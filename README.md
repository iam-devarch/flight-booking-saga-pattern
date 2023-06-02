# Saga design pattern demo with orchestrator way
(**Note:** As this project focuses more on demo or usage of design pattern there are no tests for this)
## Prerequisites
* Apache Kafka 
* Java 17+
* Postman or SoapUI (For ease of API calls)

## Flow Diagram for Flight booking transaction
![](/images/saga-orchestrator.png)

## Steps to run in local
* Use docker-compose.yml file to pull kafka images and run kafka container instances  on your local
* Once zookeeper and kafka containers are up and running, run all four spring boot components
  * BookingServiceApplication.java
  * OrchestratorApplication.java
  * SeatReservationServiceApplication.java
  * PaymentServiceApplication.java
* After that you can call services as shown in below snapshots from postman or soapUI
  * Call /booking/confirm service as below
  ![](/images/booking-confirm-service.png)

  * Call /booking/confirm service as below
  ![](/images/booking-showAll-service.png)

