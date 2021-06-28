#!/bin/bash

while true
do

	USER_ID=$RANDOM

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://web-api:8080/login/$USER_ID >> /dev/null
	sleep 1
	
	nohup curl http://web-api:8080/web-api/flights/searchForFlights >> /dev/null
	sleep 1

	nohup curl http://web-api:8080/web-api/flights/getPromotions >> /dev/null
	sleep 1
	
	nohup curl http://web-api:8080/web-api/flights/getAirportDetails >> /dev/null
	sleep 1
	
	nohup curl http://web-api:8080/web-api/flights/getFlightDetails >> /dev/null
	sleep 1

	nohup curl http://web-api:8080/web-api/flights/chooseSeat >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://web-api:8080/web-api/flights/bookFlight >> /dev/null
	sleep 1
	
	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://web-api:8080/web-api/flights/reserveFlight >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://web-api:8080/web-api/flights/managerApproval >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://web-api:8080/web-api/flights/paymentIssued >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://web-api:8080/web-api/flights/ticketIssued >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://web-api:8080/web-api/flights/checkout >> /dev/null
	sleep 1

done