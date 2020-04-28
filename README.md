
#Quick thoughts regarding my implementation:

- I choosed mongoDB for storage since there is no complex object graph needs to be persisted as tables with relations 
and queried based on alot of criterias and joins.
and also of course because of the need to store every calculated price to the database so I imagine in case of huge number of users
there is a need to high throughput performance which noSQL databases is good at it, for example user can have a GUI with a slider for 
for the coverage so he could requests the backend to calculate the price a lot of times until he found his proper price. 

- Followed TDD approach in my implementation.

- used mongock for database migration 

- used spring boot actuator metrics with prometheus for monitoring the application

- dockerized the application so it's easy to be deployed else where (of course there is still more tweeks can be done)

#End Points :
- GET v1/prices
- GET v1/modules
- POST v1/modules/:id/price

# Instructions :
- After building the project and generate jar file in target, you can run the application by typing this command on the project root folder : 
    - docker-compose up -d 
 
  
then you can start call the rest endpoint :

    curl -X GET http://localhost:8080/v1/modules
    
    response:
    [
      {
          "id": "5d848f84b834740001e732b2",
          "moduleName": "Bike",
          "minCoverage": {
              "amount": 0
          },
          "maxCoverage": {
              "amount": 3000
          },
          "risk": 30
      },
      {
          "id": "5d848f84b834740001e732b4",
          "moduleName": "Jewelry",
          "minCoverage": {
              "amount": 500
          },
          "maxCoverage": {
              "amount": 10000
          },
          "risk": 5
      },
      {
          "id": "5d848f84b834740001e732b6",
          "moduleName": "Electronics",
          "minCoverage": {
              "amount": 500
          },
          "maxCoverage": {
              "amount": 6000
          },
          "risk": 35
      },
      {
          "id": "5d848f84b834740001e732b8",
          "moduleName": "Sports Equipment",
          "minCoverage": {
              "amount": 0
          },
          "maxCoverage": {
              "amount": 20000
          },
          "risk": 30
      }]
      
  

- So you can pickup any module id and then calculate price of a selected coverage 
by making this call and send amount in body payload of the request :
    - curl -X POST \
  http://localhost:8080/v1/modules/5d848f84b834740001e732b2/price \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"amount":710.0226
}'

- and you can see prices log calculations from here :
    - curl -X GET \
  http://localhost:8080/v1/prices \
  -H 'cache-control: no-cache'
