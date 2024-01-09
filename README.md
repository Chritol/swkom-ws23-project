# swkom-ws23-project
Git Repo for the semester project (WS2023) in SWKOM Java.

A paperless based simple document management system. It is possible to upload documents and search for documents. The documents are indexed by Elasticsearch and the OCR service is used to extract text from the documents. The documents are stored in MinIO and the metadata is stored in a Postgres database. 

by 
- David Zeilinger
- Christopher Tolan

## Setup
To run the project, docker is required. Run following docker command to start the project:
```
docker-compose up
```
This will create docker container for all the services and start them. The services are available on the following ports:
- 5432: Postgres Database
- 5672: RabbitMQ
- 8080: Frontend (Paperless)
- 8088: Backend (Paperless)
- 8082: OCR Service
- 9001: MinIO
- 9200: Elasticsearch

## Access
Open in any web browser: http://localhost:8080

## Dashboards

RabbitMQ: http://localhost:15672
```
Username: paperless
Password: paperless
```
MinIO: http://localhost:9001
```
Username: paperless
Password: paperless
```
RabbitMQ: http://localhost:15672
```
Username: paperless
Password: paperless
```