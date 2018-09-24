# Webscraping from GetYourGuide site
import sys
import time

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException

class TourPlan:
	pass

# Specifying incognito mode as you launch your browser[OPTIONAL]
option = webdriver.ChromeOptions()
option.add_argument('--headless')
option.add_argument('--no-sandbox')
option.add_argument('--disable-dev-shm-usage')

# Create new Instance of Chrome in incognito mode
browser = webdriver.Chrome(executable_path='chromedriver', chrome_options=option)
detail = webdriver.Chrome(executable_path='chromedriver', chrome_options=option)

browser.get("https://www.getyourguide.com/s/?q=" + sys.argv[1] + "&customerSearch=1&searchSource=2&p=1")

timeout = 20
try:
    WebDriverWait(browser, timeout).until(EC.visibility_of_element_located((By.XPATH, "//div[@class='activities-show-more load-more']")))
except TimeoutException:
    print("Timed out!")

try:
	element = browser.find_element_by_xpath("//div[@class='activities-show-more load-more']")
	process = True

	while process:

		browser.execute_script("arguments[0].click();", element)
		browser.execute_script("arguments[0].scrollIntoView();", element)
		
		time.sleep(0.6)
		
		element = browser.find_element_by_xpath("//div[@class='activities-show-more load-more']")
		child = browser.find_element_by_xpath("//span[@class='btn btn-cta btn-small']")
		
		if child.get_attribute("style") == 'display: none;':
		
			process = False
			
			# find_elements_by_xpath - Returns an array of selenium objects.
			articles_element = browser.find_elements_by_xpath("//a[@class='activity-card-link']")
			
			step = 1
			for article in articles_element:
				
				# Go to detail page
				detail.get("" + article.get_attribute("href"))
				
				about_list = detail.find_elements_by_xpath("//div[contains(@class, 'keydetails-benefit key-details-item')]//div[contains(@class, 'label')]")
				
				tour = TourPlan()
				tour.title = detail.find_element_by_xpath("//h1[@class='activity-title']").text
				tour.overview = detail.find_element_by_xpath("//section[@class='overview long-txt']//div[@class='content']").text
				tour.price = detail.find_element_by_xpath("//p[contains(@class, 'price')]//strong[@class='price-actual']").text
				tour.image = detail.find_element_by_xpath("//div[@class='activity-header-image-contain']").get_attribute("data-background")
			
				jsonStr = '{"title":"' + tour.title + '", "overview":"' + tour.overview + '", "price":"' + tour.price + '", "image":"' + tour.image + '", "about":['
				
				for i in range(1, len(about_list)):
					if i > 1:
						jsonStr = jsonStr + ','
					jsonStr = jsonStr + '"' + about_list[i].text + '"'
				jsonStr = jsonStr + ']}'
				print(jsonStr)
				
				time.sleep(10)
except TimeoutException:
	fail = True

browser.quit()
detail.quit()