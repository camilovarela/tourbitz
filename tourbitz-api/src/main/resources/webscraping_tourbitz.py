from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import time

# Specifying incognito mode as you launch your browser[OPTIONAL]
option = webdriver.ChromeOptions()
option.add_argument("--incognito")

# Create new Instance of Chrome in incognito mode
browser = webdriver.Chrome(executable_path='chromedriver', chrome_options=option)

# Go to desired website
browser.get("https://www.getyourguide.com/s/?q=mexico&customerSearch=1&searchSource=2&p=1")

# Wait 20 seconds for page to load
timeout = 20
try:
    WebDriverWait(browser, timeout).until(EC.visibility_of_element_located((By.XPATH, "//div[@class='activities-show-more load-more']")))
except TimeoutException:
    print("Timed out waiting for page to load")
    browser.quit()

element = browser.find_element_by_xpath("//div[@class='activities-show-more load-more']")
process = True
while process:

	browser.execute_script("arguments[0].click();", element)
	browser.execute_script("arguments[0].scrollIntoView();", element)
	
	time.sleep(0.5)
	
	element = browser.find_element_by_xpath("//div[@class='activities-show-more load-more']")
	child = browser.find_element_by_xpath("//span[@class='btn btn-cta btn-small']")
	
	if child.get_attribute("style") == 'display: none;':
		process = False
		
		# find_elements_by_xpath - Returns an array of selenium objects.
		titles_element = browser.find_elements_by_xpath("//h3[@class='activity-card-title']")
		
		# List Comprehension to get the actual repo titles and not the selenium objects.
		print(len(titles_element))

		titles = [x.text for x in titles_element]

		# print response in terminal
		print('TITLES:')
		print(titles, '\n')
