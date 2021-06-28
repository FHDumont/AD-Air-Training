var lodash = require("lodash");
var uuidV4 = require("uuid/v4");

var maleFirstNames = ["Rob","Eric","Jeff","Alex","Tom","James","Steve","Matt","Ron","Andy","Tom"];
var femaleFirstNames = ["Gabriella","Lori","Michelle","Jill","Michelle","Leslie","Anne"];
var lastNames = ["Bolton","Querales","Johanson","Morgan","Rabaut","Fedotyev","Swanson","Dwyer","Knope","Haverford","Perkins"];

var customerClasses = ["Silver", "Silver", "Silver", "Platinum", "Platinum", "Gold", "Gold", "Platinum", "Platinum", "Diamond"];

var flights = [{"start": "ATL", "end": "LAX", "price1": 2780, "price2": 1326, "price3": 822},
				{"start": "ATL", "end": "DFW", "price1": 2540, "price2": 918, "price3": 569},
				{"start": "ATL", "end": "DEN", "price1": 2600, "price2": 1020, "price3": 632},
				{"start": "ATL", "end": "SFO", "price1": 2840, "price2": 1428, "price3": 885},
				{"start": "ATL", "end": "LAS", "price1": 2780, "price2": 1326, "price3": 822},
				{"start": "ATL", "end": "SEA", "price1": 2900, "price2": 1530, "price3": 948},
				{"start": "ATL", "end": "MIA", "price1": 2660, "price2": 1122, "price3": 695},
				{"start": "ATL", "end": "BOS", "price1": 2600, "price2": 1020, "price3": 632},
				{"start": "ATL", "end": "LGA", "price1": 2540, "price2": 918, "price3": 569},
				{"start": "ATL", "end": "IAD", "price1": 2479, "price2": 816, "price3": 505},
				{"start": "MCI", "end": "LAX", "price1": 2600, "price2": 1020, "price3": 632},
				{"start": "MCI", "end": "DFW", "price1": 2419, "price2": 714, "price3": 442},
				{"start": "MCI", "end": "DEN", "price1": 2360, "price2": 612, "price3": 379},
				{"start": "MCI", "end": "SFO", "price1": 2660, "price2": 1122, "price3": 695},
				{"start": "MCI", "end": "LAS", "price1": 2570, "price2": 969, "price3": 600},
				{"start": "MCI", "end": "SEA", "price1": 2479, "price2": 816, "price3": 505},
				{"start": "MCI", "end": "MIA", "price1": 2720, "price2": 1224, "price3": 758},
				{"start": "MCI", "end": "BOS", "price1": 2690, "price2": 1173, "price3": 727},
				{"start": "MCI", "end": "LGA", "price1": 2660, "price2": 1122, "price3": 695},
				{"start": "MCI", "end": "IAD", "price1": 2720, "price2": 1224, "price3": 758},
				{"start": "PDX", "end": "LAX", "price1": 2540, "price2": 918, "price3": 569},
				{"start": "PDX", "end": "DFW", "price1": 2660, "price2": 1122, "price3": 695},
				{"start": "PDX", "end": "DEN", "price1": 2540, "price2": 918, "price3": 569},
				{"start": "PDX", "end": "SFO", "price1": 2479, "price2": 816, "price3": 505},
				{"start": "PDX", "end": "LAS", "price1": 2540, "price2": 918, "price3": 569},
				{"start": "PDX", "end": "SEA", "price1": 2360, "price2": 612, "price3": 379},
				{"start": "PDX", "end": "MIA", "price1": 3200, "price2": 2040, "price3": 1264},
				{"start": "PDX", "end": "BOS", "price1": 2720, "price2": 1224, "price3": 758},
				{"start": "PDX", "end": "LGA", "price1": 2780, "price2": 1326, "price3": 822},
				{"start": "PDX", "end": "IAD", "price1": 2810, "price2": 1377, "price3": 853}];

var genders = ["M", "F"];

exports.getFlightInfo = function() {

	var gender = lodash.sample(genders);
	var firstName;

	if (gender == "M") {
		firstName = lodash.sample(maleFirstNames);
	}
	else {
		firstName = lodash.sample(femaleFirstNames);
	}

	var lastName = lodash.sample(lastNames);

	var selectedFlight = lodash.sample(flights);

	var ticketClass;
	var ticketPrice;
	var rand = lodash.random(0, 100);

	if (rand < 20) {
		ticketClass = "FirstClass";
		ticketPrice = selectedFlight.price1;
	}
	else if (rand < 50) {
		ticketClass = "BusinessClass";
		ticketPrice = selectedFlight.price2;
	}
	else {
		ticketClass = "EconomyClass";
		ticketPrice = selectedFlight.price3;
	}

	return {
		userId: lodash.random(10000, 99999),
		ticketId: uuidV4(),
		firstName: firstName,
		lastName: lastName,
		startCity: selectedFlight.start,
		endCity: selectedFlight.end,
		flightCities: selectedFlight.start + "-" + selectedFlight.end,
		customerClass: lodash.sample(customerClasses),
		ticketPrice: ticketPrice,
		ticketClass: ticketClass,
		promotionName: "June2019"
  	}
}
