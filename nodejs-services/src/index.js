
var authServices = require("./services/auth-services.js");
var flightServices = require("./services/flight-services.js");
var offerServices = require("./services/offer-services.js");
var searchServices = require("./services/search-services.js");
var os = require("os");
var hostname = os.hostname();

var main = function () {

    if (hostname == "auth-services") {
        authServices.main();
    }
    else if (hostname == "flight-services") {
        flightServices.main();
    }
    else if (hostname == "offer-services") {
        offerServices.main();
    }
    else if (hostname == "search-services") {
        searchServices.main();
    }
}

main();
