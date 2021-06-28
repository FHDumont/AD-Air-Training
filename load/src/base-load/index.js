var infoGen = require('./infoGen.js');
var lodash = require("lodash");
var needle = require('needle');
var eumUtilities = require("eum-utilities");

var appBaseUrl = "http://web-api:8080";
var appInitialized = false;

var getPagesForSession = function(sessionGuid) {

    var flightInfo = infoGen.getFlightInfo();
    var customerInfo = {firstName: flightInfo.firstName, lastName: flightInfo.lastName, customerClass: flightInfo.customerClass};
    var searchCity = lodash.sample([flightInfo.startCity, flightInfo.endCity]);

    var pageList = [];
    pageList.push({page: "/login/" + flightInfo.userId, method: "post", drop: 5, headers: {"sessionId": sessionGuid, "Content-Type": "application/json"}, postData: customerInfo});
    pageList.push({page: "/web-api/flights/searchForFlights", method: "get", drop: 20, headers: {"sessionId": sessionGuid}});
    pageList.push({page: "/web-api/flights/getPromotions", method: "get", drop: 30, headers: {"sessionId": sessionGuid}});
    pageList.push({page: "/web-api/flights/getAirportDetails?airportCode=" + searchCity, method: "get", drop: 10, headers: {"sessionId": sessionGuid}});
    pageList.push({page: "/web-api/flights/getFlightDetails", method: "get", drop: 15, headers: {"sessionId": sessionGuid}});
    pageList.push({page: "/web-api/flights/chooseSeat", method: "get", drop: 10, headers: {"sessionId": sessionGuid}});
    pageList.push({page: "/web-api/flights/bookFlight", method: "post", drop: 20, headers: {"sessionId": sessionGuid, "Content-Type": "application/json"}, postData: flightInfo});
    pageList.push({page: "/web-api/flights/checkout", method: "post", drop: 0, headers: {"sessionId": sessionGuid, "Content-Type": "application/json"}, postData: flightInfo, addFrontEndError: true});    
    return pageList;
}

var processNextPage = function(session) {

    if (session.pageList.length == 0) {
        console.log("session.pageList.length == 0: " + session.sessionGuid);
    } 
    else {
        var nextPage = session.pageList.shift();

        if (nextPage.drop && (lodash.random(1,100) < nextPage.drop)) {

            console.log("Dropping session at page " + nextPage.page);
        
        } 
        else {

            var options = {
                headers: nextPage.headers
            };

            if (nextPage.addFrontEndError && nextPage.addFrontEndError == true && nextPage.postData && nextPage.postData.customerClass == "Diamond" && session.browser.agent.indexOf("Chrome") < 0) {

                console.log("Adding error for page: " + nextPage.page);
                setTimeout(function() {
                    processNextPage(session);
                }, lodash.random(2000, 8000));

            }
            else {

                var start = new Date();
                needle.request(nextPage.method, appBaseUrl + nextPage.page, nextPage.postData, options, function(err, result) {
    
                    var statusCode = "blank";
    
                    if (result && result.statusCode) {
                        statusCode = result.statusCode;
                    }
    
                    if (!err && statusCode == 200) {
                        
                        var timeMS = new Date() - start
                        console.log("Page successfully returned from " + appBaseUrl + nextPage.page + ", statusCode: " + statusCode + ", execution time: %dms", timeMS);
    
                        if (appInitialized == false) {
                            console.log("appInitialized = true");
                            appInitialized = true;
                        }
    
                        if (appInitialized == true) {
    
                            setTimeout(function() {
                                processNextPage(session);
                            }, lodash.random(2000, 8000));
                        }
    
                    }
                    else {
    
                        var timeMS = new Date() - start
    
                        if (err) {
                            console.log("Error requesting application page: " + nextPage.method + ": " + appBaseUrl + nextPage.page + ", " + err + ", execution time: %dms", timeMS);
                        }
                        else {
                            console.log("Error requesting application page: " + nextPage.method + ": " + appBaseUrl + nextPage.page + ", " + statusCode + ", execution time: %dms", timeMS);
                        }
                    }
                }); 
            }
        }
    }
}

var generateSessions = function() {

    var waitTime = 7500;
    var sessionCount = 6;
    
    for (var i = 0; i < sessionCount; i++) {
        var session = eumUtilities.getBrowserSessionData();
        session.pageList = getPagesForSession(session.sessionGuid);
        processNextPage(session);
    }

    setTimeout(function() {generateSessions();}, waitTime);
}

exports.main = function() {
    generateSessions();
}
