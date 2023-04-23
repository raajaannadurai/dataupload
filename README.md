# Microservice to upload data into h2 database.

Endpoints
---------
POST : http://localhost:9090/api/v1/medical-records [Data upload]

GET : http://localhost:9090/api/v1/medical-records [Fetch all data]

GET: http://localhost:9090/api/v1/medical-records/{code} [Get data by code]

DELETE : http://localhost:9090/api/v1/medical-records [Delete all data]

H2 Console
----------
http://localhost:9090/h2-console

jdbc url : jdbc:h2:mem:test_db
