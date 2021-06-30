#!/bin/bash

WEB_API_HOST=web-api
WEB_API_PORT=8080

if [ x"$WEB_API_HOST" != "x" ]; then
    WEB_API_HOST=$WEB_API_HOST
fi

if [ x"$WEB_API_PORT" != "x" ]; then
    WEB_API_PORT=$WEB_API_PORT
fi

while true
do

	USER_ID=$RANDOM

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://$WEB_API_HOST:$WEB_API_PORT/login/$USER_ID >> /dev/null
	sleep 1
	
	nohup curl http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/searchForFlights >> /dev/null
	sleep 1

	nohup curl http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/getPromotions >> /dev/null
	sleep 1
	
	nohup curl http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/getAirportDetails >> /dev/null
	sleep 1
	
	nohup curl http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/getFlightDetails >> /dev/null
	sleep 1

	nohup curl http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/chooseSeat >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/bookFlight >> /dev/null
	sleep 1
	
	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/reserveFlight >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/managerApproval >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/paymentIssued >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/ticketIssued >> /dev/null
	sleep 1

	nohup curl -d '{"firstName":"James", "lastName":"Bond", "customerClass":"Diamond"}' -H "Content-Type: application/json" -X POST http://$WEB_API_HOST:$WEB_API_PORT/web-api/flights/checkout >> /dev/null
	sleep 1

done