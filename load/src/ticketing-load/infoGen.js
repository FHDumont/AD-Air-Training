var lodash = require("lodash");
var uuidV4 = require("uuid/v4");

var maleFirstNames = ["Rob","Eric","Jeff","Alex","Tom","James","Steve","Matt","Ron","Andy","Tom"];
var femaleFirstNames = ["Gabriella","Lori","Michelle","Jill","Michelle","Leslie","Anne"];
var lastNames = ["Bolton","Querales","Johanson","Morgan","Rabaut","Fedotyev","Swanson","Dwyer","Knope","Haverford","Perkins"];

var geoList = ["US Southeast", "US Northeast", "US West", "US Central", "EMEA", "APAC", "LATAM"];

var airlines = [{"name": "American Airlines", "prefix": "AA"}, {"name": "Air Canada", "prefix": "AC"}, {"name": "Alaska Airlines", "prefix": "AS"}, 
				{"name": "JetBlue Airways", "prefix": "B6"}, {"name": "British Airways", "prefix": "BA"}, {"name": "Delta Airlines", "prefix": "DL"}, 
				{"name": "Spirit Airlines", "prefix": "NK"}, {"name": "Lufthansa", "prefix": "LH"}];

var genders = ["M", "F"];

exports.getTicketInfo = function() {

	var gender = lodash.sample(genders);
	var firstName;

	if (gender == "M") {
		firstName = lodash.sample(maleFirstNames);
	}
	else {
		firstName = lodash.sample(femaleFirstNames);
	}

	var lastName = lodash.sample(lastNames);
	var selectedAirline = lodash.sample(airlines);
	var selectedGeo = lodash.sample(geoList);

	// Delays are in seconds
	var step2Delay = lodash.random(60, 120);
	var step3Delay = lodash.random(20, 30);
	var step4Delay = lodash.random(120, 180);
	var chanceSeatTaken = lodash.random(0, 6);

	var bookingFee = lodash.random(200, 300);
	var bookingFailed = false;

	if (chanceSeatTaken >= lodash.random(0, 100)) {
		bookingFailed = true;
	}

	return {
		sessionId: uuidV4(),
		ticketId: uuidV4(),
		firstName: firstName,
		lastName: lastName,
		airlineName: selectedAirline.name,
		airlineCode: selectedAirline.prefix,
		flightNumber: selectedAirline.prefix + "-" + lodash.random(100, 999),
		geo: selectedGeo,
		chanceSeatTaken: chanceSeatTaken,
		step2Delay: step2Delay,
		step3Delay: step3Delay,
		step4Delay: step4Delay,
		bookingFailed: bookingFailed,
		bookingFee: bookingFee
  	}
}




