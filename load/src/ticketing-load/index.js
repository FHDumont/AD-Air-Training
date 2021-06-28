var infoGen = require('./infoGen.js');
var lodash = require("lodash");
var http = require("http");
var needle = require("needle");

var hostName = "web-api";
var hostPort = "8080";
var mongoDBURL = "mongodb://mongo-tickets:27017/tickets";
var MongoClient = require('mongodb').MongoClient;
var dbName = "tickets";
var initalWaitMS = 5000;
var waitMS = 2000;

var reserveFlight = function() {

    console.log("Starting reserveFlight");
    var ticketInfo = infoGen.getTicketInfo();

    var options = {host: hostName, path: '/web-api/flights/reserveFlight', port: '8080', method: 'POST', headers: {'sessionId': ticketInfo.sessionId, 'Content-Type': 'application/json','Content-Length': Buffer.byteLength(JSON.stringify(ticketInfo))}};
    var newReq = http.request(options, function(res) {
        console.log("statusCode: ", res.statusCode);
        res.on('data', function(d) {
            // console.log(d);
        });
    });

    newReq.write(JSON.stringify(ticketInfo));
    newReq.end();

    console.log("reserveFlight complete");
} 

var managerApproval = function() {

    console.log("Starting managerApproval");

    var filter = {"managerApprovalDate": {"$exists": false}, "nextStepAfter": {"$lt": new Date(Date.now())}};

    mongoDBGet(mongoDBURL, dbName, filter, function(err, selectedRecord) {
        
        if (selectedRecord) {

            var nextStepDelay = selectedRecord.step3Delay; // delay is in seconds
            var ticketId = selectedRecord.ticketId;
            var nextStepAfter = Date.now() + (nextStepDelay * 1000);
            var updateFields = {"managerApprovalDate": new Date(Date.now()), "nextStepAfter": new Date(nextStepAfter)};
            setMilestoneDate("managerApprovalDate", updateFields, ticketId, nextStepDelay);

            var url = hostName + ':8080/web-api/flights/managerApproval';
            var options = {
                method: 'POST',
                json: true,
                headers: {'sessionId': selectedRecord.sessionId}
            };

            needle.post(url, selectedRecord, options, function(err, resp) {
                console.log("managerApproval complete");
            });
        } 
        else {
            console.log("managerApproval: selectedRecord == null");
        }  
    });
} 

var paymentIssued = function() {

    console.log("Starting paymentIssued");

    var filter = {"paymentIssuedDate": {"$exists": false}, "managerApprovalDate": {"$exists": true}, "nextStepAfter": {"$lt": new Date(Date.now())}};

    mongoDBGet(mongoDBURL, dbName, filter, function(err, selectedRecord) {
        
        if (selectedRecord) {

            var nextStepDelay = selectedRecord.step4Delay; // delay is in seconds
            var ticketId = selectedRecord.ticketId;
            var nextStepAfter = Date.now() + (nextStepDelay * 1000);
            var updateFields = {"paymentIssuedDate": new Date(Date.now()), "nextStepAfter": new Date(nextStepAfter)};
            setMilestoneDate("paymentIssuedDate", updateFields, ticketId, nextStepDelay);

            var url = hostName + ':8080/web-api/flights/paymentIssued';
            var options = {
                method: 'POST',
                json: true,
                headers: {'sessionId': selectedRecord.sessionId}
            };

            needle.post(url, selectedRecord, options, function(err, resp) {
                console.log("paymentIssued complete");
            });
        } 
    });
} 

var ticketIssued = function() {

    console.log("Starting ticketIssued");

    var filter = {"ticketIssuedDate": {"$exists": false}, "paymentIssuedDate": {"$exists": true}, "nextStepAfter": {"$lt": new Date(Date.now())}};

    mongoDBGet(mongoDBURL, dbName, filter, function(err, selectedRecord) {
        
        if (selectedRecord) {

            var nextStepDelay = -1; // delay is in seconds
            var ticketId = selectedRecord.ticketId;
            var nextStepAfter = Date.now() + (nextStepDelay * 1000);
            var updateFields = {"ticketIssuedDate": new Date(Date.now()), "nextStepAfter": new Date(nextStepAfter)};
            setMilestoneDate("ticketIssuedDate", updateFields, ticketId, nextStepDelay);

            var url = hostName + ':8080/web-api/flights/ticketIssued';
            var options = {
                method: 'POST',
                json: true,
                headers: {'sessionId': selectedRecord.sessionId}
            };

            needle.post(url, selectedRecord, options, function(err, resp) {
                console.log("ticketIssued complete");
            });
        }
    });
}

var mongoDBInsert = function(mongoDBURL, dbName, rewardInfo, nextStepDelay, parentCallback) {

    var nextStepAfter = Date.now() + (nextStepDelay * 1000);
    rewardInfo.nextStepAfter = nextStepAfter;

    MongoClient.connect(mongoDBURL, function(err, db) {

        if (err) {
            console.log("mongoDBInsert: err");
            console.log(err);
            parentCallback(err, null);
        }
        else {
            db.collection(dbName).insertOne(dbRecord, function(err1, res) {
                if (err1) {
                    console.log("Error calling: " + mongoDBURL + ", ");
                    console.log(err);
                }
                db.close();
                parentCallback(err1, res);
            });
        }
    });
}

var mongoDBGet = function(mongoDBURL, dbName, filter, parentCallback) {

    MongoClient.connect(mongoDBURL, function(err, db) {

        if (err) {
            console.log("Error calling: " + mongoDBURL + ", ");
            console.log(err);
            throw err;
        }

        db.collection(dbName).find(filter).toArray(function(err1, results) {

            var recordCount = results.length;
            var selectedIndex = lodash.random(0, (recordCount - 1));
            var selectedRecord = results[selectedIndex];

            db.close();
            if (err1) {
                console.log("Error calling: " + mongoDBURL + ", ");
                console.log(err1);
                throw err1;
            }

            parentCallback(err, selectedRecord);
            // console.log(res);
        });
    });
}

var mongoDBUpdate = function(mongoDBURL, dbName, recordId, updateFields, parentCallback) {

    MongoClient.connect(mongoDBURL, function(err, db) {

        if (err) throw err;

        db.collection(dbName).updateMany({ticketId: recordId}, {$set: updateFields}, function(err1, results) {

            db.close();
            if (err1) {
                console.log("mongoDBUpdate: Error calling: " + mongoDBURL + ", ");
                console.log(err1);
                throw err1;
            }

            parentCallback(err1, results);
            // console.log(res);
        });
    });
}

var mongoDBDelete = function(mongoDBURL, dbName, recordId, parentCallback) {

    MongoClient.connect(mongoDBURL, function(err, db) {

        if (err) throw err;

        db.collection(dbName).deleteMany({ticketId: recordId}, function(err, results) {

            db.close();
            if (err) {
                console.log("Error calling: " + mongoDBURL + ", ");
                console.log(err);
                throw err;
            }

            parentCallback(err, results);
            // console.log(res);
        });
    });
}

var setMilestoneDate = function(milestoneDateName, updateFields, ticketId, nextStepDelay) {

    if (nextStepDelay > 0) {

        mongoDBUpdate(mongoDBURL, dbName, ticketId, updateFields, function(err) {
            if (err) {
                console.log("Error calling: " + mongoDBURL + ", ");
                console.log(err);
                throw err;
            }
            console.log("setMilestoneDate complete for ticketId: " + ticketId + ", milestone: " + milestoneDateName);
        }); 
    }
    else {

        mongoDBDelete(mongoDBURL, dbName, ticketId, function(err) {
            console.log("Deleting record for ticketId: " + ticketId);
        }); 
    }
}

var generateLoad = function() {

    reserveFlight();
    managerApproval();
    paymentIssued();
    ticketIssued();
        
    setTimeout(function() {generateLoad();}, waitMS);
}

exports.main = function() {
    setTimeout(function() {generateLoad();}, initalWaitMS);
}