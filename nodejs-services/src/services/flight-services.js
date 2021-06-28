
var http = require('http');
var path = require('path');
var lodash = require('lodash');
var HttpDispatcher = require('httpdispatcher');
var dispatcher = new HttpDispatcher();
var needle = require('needle');

function handleRequest(request, response) {
    try {
        console.log(request.url);
        dispatcher.dispatch(request, response);
    }
    catch (err) {
        console.log(err);
    }
}

dispatcher.onGet(/\//, function(request, response) {

    var url = request.url;
    var endpoint = url.split("/")[2];

    makeWebRequest("search-services", "8080", "/search-services/" + endpoint, lodash.random(0, 75), function(err) {
        
        var url = "http://search-services:8080/search-services/" + endpoint;

        if (err) {
            console.log('error: ' + url);
            response.writeHead(500, {
                'Content-Type': 'text/plain'
            });
            response.end(err.errno);
        }
        else {
            makeWebRequest("offer-services", "8080", "/offer-services/" + endpoint, lodash.random(0, 75), function(err2) {
                
                var url2 = "http://offer-services:8080/offer-services/" + endpoint;

                if (err2) {
                    console.log('error: ' + url2);
                    response.writeHead(500, {
                        'Content-Type': 'text/plain'
                    });
                    response.end(err2.errno);
                }
                else {
                    console.log('finishing: ' + url2);
                    response.writeHead(200, {
                        'Content-Type': 'text/plain'
                    });
                    response.end(url2);
                }
            });
        }
    });
});

var makeWebRequest = function(hostName, port, path, waitMS, parentCallback) {

    var options = {};
    var start = new Date();
    var url = "http://" + hostName + ":" + port + path;

    console.log("Calling: " + url);

    needle.get( url, function(err, result) {

        const end = Date.now() + waitMS;
        while (Date.now() < end) {
            const doSomethingHeavyInJavaScript = 1 + 2 + 3;
        }

        var statusCode = "blank";

        if (result && result.statusCode) {
            statusCode = result.statusCode;
        }

        if (!err && statusCode == 200) {
            var timeMS = new Date() - start
            console.log("Page successfully returned from " + url + ", statusCode: " + statusCode + ", execution time: %dms", timeMS);
            parentCallback(null);
        }
        else {
            var timeMS = new Date() - start

            if (err) {
                console.log("Error requesting application page: " + url + ", " + err + ", execution time: %dms", timeMS);
                parentCallback(err)
            }
            else {
                console.log("Error requesting application page: " + url + ", " + statusCode + ", execution time: %dms", timeMS);
                parentCallback("Error requesting application page: " + url + ", " + statusCode);
            }
        }
    }); 
}

dispatcher.onError(function(request, response) {
    response.writeHead(404);
    response.end("Error, the URL doesn't exist");
});

var startServer = function() {

    var server = http.createServer(handleRequest);

    server.listen(8080, (err) => {
        if (err) {
            return console.log('error: ' + err)
        }
        console.log('process.pid: ' + process.pid);
        console.log(`server is listening on 8080`)
    })
}

exports.main = function() {
    startServer();
}