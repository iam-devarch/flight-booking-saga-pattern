# Distributed transaction using Saga design pattern with orchestrator way
(**Note:** As this project focuses more on demo or usage of design pattern there are no tests for this)  

**Scope for enhancement** : This project currently saves only current state of all the transactions and hence cannot audit or trace back history of events. 
We can enhance it to use **Event Sourcing pattern** to capture each db save as one event which will make it auditable and history can be traced back.  

## Prerequisites / Tech stack
* Apache Kafka 
* Java 17+
* WebFlux
* H2 DB
* Postman or SoapUI (For ease of API calls)


## Functionality
This application performs flight booking operation as a distributed transaction.  
So failure in any one task will revert back and cancel the whole booking.  
When **/booking/confirm** service is triggered the transaction starts.  
It mainly comprises two major below tasks which have **commit** and **rollback** operations completed by service calls **/seats/reserve** and **/payment/debit**.  

* Payment task
* Seat reservation task

If there's failure in any one of them for any reason (for e.g. insufficient balance or unavailability of seats), the whole transaction will be cancelled.  
The cancellation is completed by calling the **/seats/revert** and **/payment/credit** services for each of above transaction tasks. The flow diagram for this is shown below.

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

