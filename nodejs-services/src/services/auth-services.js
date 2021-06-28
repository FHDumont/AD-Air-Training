
var http = require('http');
var path = require('path');
var MongoClient = require('mongodb').MongoClient;
var lodash = require('lodash');
var HttpDispatcher = require('httpdispatcher');
var dispatcher = new HttpDispatcher();
var d = require('domain').create()

function handleRequest(request, response) {
    try {
        console.log(request.url);
        dispatcher.dispatch(request, response);
    }
    catch (err) {
        console.log(err);
    }
}

dispatcher.onGet("/", function (request, response) {
    response.writeHead(200, {
        'Content-Type': 'text/html'
    });
    response.end('<h1>Hey, this is the homepage of your server</h1>');
});

dispatcher.onGet("/auth-services/authenticate", function (request, response) {

    var delayMinMS = 50;
    var delayMaxMS = 100;

    var mongoDBURL = "mongodb://mongo-sessions/sessions";

    var url = request.url;

    mongoQuery(mongoDBURL, "sessions", delayMinMS, delayMaxMS, function (err) {

        if (err) {
            response.writeHead(500, {
                'Content-Type': 'text/plain'
            });
            response.end(err.errno);
        }
        else {
            response.statusCode = 200;
            response.end('Authenticate');
        }
    });
});

var makeWebRequest = function (hostName, port, path, waitMS, parentCallback) {

    d.on('error', function (err) {
        parentCallback(err);
    })

    d.run(function () {

        if (waitMS > 0) {
            var waitTill = new Date(new Date().getTime() + waitMS);
            while (waitTill > new Date()) {
                const doSomethingInJavaScript = 1 + 2 + 3;
            }
        }

        var options = {
            host: hostName,
            path: path,
            port: port
        };

        var callback = function (response) {
            var str = ''
            response.on('data', function (chunk) {
                str += chunk;
            });
            response.on('error', function (err) {
                console.log(err)
                parentCallback(err);
            });
            response.on('end', function () {
                console.log("response.on(end): " + str);
                parentCallback(null);
            });
        }

        var req = http.request(options, callback).end();
    });
}

dispatcher.onError(function (request, response) {
    response.writeHead(404);
    response.end("Error, the URL doesn't exist");
});

var db;

var initMongoDB = function (mongoDBURL) {

    console.log("initMongoDB");
    MongoClient.connect(mongoDBURL, function (err, database) {
        if (err) throw err;
        db = database;
    });

}

var mongoQuery = function (mongoDBURL, dbName, delayMinMS, delayMaxMS, parentCallback) {

    if (db == null) {
        initMongoDB(mongoDBURL);
    }
    else {

        var query = {
            "firstName": "James"
        };

        db.collection(dbName).find(query).toArray(function (err, result) {

            var delayMS = lodash.random(delayMinMS, delayMaxMS);
            const end = Date.now() + delayMS;
            while (Date.now() < end) {
                const doSomethingHeavyInJavaScript = 1 + 2 + 3;
            }

            if (err) {
                console.log("Error calling: " + mongoDBURL + ", ");
                console.log(err);
            }

            console.log("Found " + result.length + " records");
            parentCallback(err, result);
        });
    }
}

var startServer = function () {

    var server = http.createServer(handleRequest);

    server.listen(8080, (err) => {
        if (err) {
            return console.log('error: ' + err)
        }
        console.log('process.pid: ' + process.pid);
        console.log(`server is listening on 8080`)
    })
}

exports.main = function () {
    startServer();
}