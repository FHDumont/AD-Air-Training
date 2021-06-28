var baseLoad = require("./base-load/index.js");
var ticketingLoad = require("./ticketing-load/index.js");

var enableBaseLoad = process.env.ENABLE_BASE_LOAD || "0";
var enableTicketingLoad = process.env.ENABLE_TICKETING_LOAD || "0";

var main = function() {
    
    if (enableBaseLoad == "1") {
        baseLoad.main()
    }
    else if (enableTicketingLoad == "1") {
        ticketingLoad.main()
    }
}

main();
