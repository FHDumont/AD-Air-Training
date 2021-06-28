
var http = require('http');
var path = require('path');
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

dispatcher.onGet(/\//, function(request, response) {

    var url = request.url;
    var endpoint = url.split("/")[2];

    if (endpoint == "searchForFlights") {
        mySqlConnect("Select count(*) as resultCount from flight;");
    }
    else if (endpoint == "getAirportDetails") {
        mySqlConnect("Select count(*) as resultCount from airport;");
    }
    else if (endpoint == "getFlightDetails") {
        mySqlConnect("Select count(*) as resultCount from flight;");
    }
    else if (endpoint == "chooseSeat") {
        mySqlConnect("Select count(*) as resultCount from seatmap;");
    }
    else if (endpoint == "getPromotions") {
        mySqlConnect("Select count(*) as resultCount from promotion;");
    }

    response.writeHead(200, {
        'Content-Type': 'text/html'
    });
    response.end('<h1>Hey, this is the homepage of your server</h1>');
});

dispatcher.onError(function(request, response) {
    response.writeHead(404);
    response.end("Error, the URL doesn't exist");
});

var mysql = require('mysql');
var connection;

function startConnection() {
    console.error('CONNECTING');
    connection = mysql.createConnection({
      host     : 'mysql-promotions',
      user     : 'promotions',
      password : 'promotions',
      database : 'Promotions'
    });
    connection.connect(function(err) {
        if (err) {
            console.error('CONNECT FAILED', err.code);
            startConnection();
        }
        else
            console.error('CONNECTED');
    });
    connection.on('error', function(err) {
        if (err.fatal)
            startConnection();
    });
}

var mySqlConnect = function(query) {

    connection.query(query, function (error, results, fields) {
    
    if (error) {
        console.log('error: ' + error);
        throw error;
    } 
        console.log('The resultCount is: ' + results[0].resultCount);
    });

    // connection.end();
}

var startServer = function() {

    var server = http.createServer(handleRequest);

    server.listen(8080, (err) => {
        if (err) {
            return console.log('error: ' + err)
        }
        console.log('process.pid: ' + process.pid);
        console.log(`server is listening on 8080`);
        startConnection();
    })
}

exports.main = function() {
    startServer();
}