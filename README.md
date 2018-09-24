# TourBitz Scraping

## Tecnologías

Para la solución de este ejercicio se usaron las siguientes tecnologías:

* Selenium
* ChromeDriver
* Python3
* Java
* SpringBoot
* GCP - Compute Engine

## Solución

Se creó una API rest que contiene dos endpoints:

* [POST] /tour-api/rest/v1.0/{tour}/tours/{search-key}

Este endpoint se conecta a la página web asociada al {tour}. Para efectos de este ejercicio el tour a utilizar es getyourguide, el cual va a extraer información de la página https://www.getyourguide.com/.

{search-key} Es el tour a buscar dentro del {tour} seleccionado. Por ejemplo, para buscar planes en Colombia, el endpoint a invocar sería:

/tour-api/rest/v1.0/getyourguide/tours/colombia

Los tours extraídos de la página se almacenan en el servidor donde está alojada la aplicación. Y se dejan disponibles para posteriores consultas.

Nota. La extracción puede tardarse un poco (depende de la cantidad de resultados encontrados), y como estrategia para evitar el bloqueo de la IP por múltiples peticiones desde el mismo origen, hay un delay de 6 segundos entre la consulta de detalle de cada uno de los tours a consultar.

* [GET] /tour-api/rest/v1.0/tours/{search-key}

Mediante este endpoint es posible consultar los tours extraídos mediante el endpoint anterior.
