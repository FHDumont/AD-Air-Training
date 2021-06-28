<!DOCTYPE HTML>
<html>
  <head>
    	<title>SYJ Airlines</title>
		<meta name="viewport" content="width=device-width, maximum-scale=1, user-scalable=no" />
		<meta name="mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<link rel="stylesheet" href="/css/bootstrap.css">
		<link rel="stylesheet" href="/css/main.css">
		<script type="text/javascript">
			
			function makeAjaxRequest(urlPath, anchorID) {
				var ajaxRequest = new XMLHttpRequest();
				ajaxRequest.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200) {
						document.getElementById(anchorID).innerHTML = "Clicked";
						setTimeout(function() {
							document.getElementById(anchorID).innerHTML = "Click Me";
						}, 3500);
					}
				};
				ajaxRequest.open("GET", urlPath, true);
				ajaxRequest.send();
			}

		</script>
  </head>
  <body bgcolor="#010001">
    <div id="header">
        <table width="100%" style="margin-top: -5px">
            <tr>
                <td>
                    <div onclick="location.href='/'">
                        <img width="220px" src="/images/logo.png"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainContainer">
		<div class="container">
			<form action="/" method="post">
				<ul class="list-group">
					<li class="list-group-item">
						<div class="row" onclick="makeAjaxRequest('/login', 'loginAnchor');">
							<div class="col-md-8">
								<h5>
									Login Page
								</h5>
							</div>
							<div class="col-md-4">
								<a id="loginAnchor" class="btn btn-primary pull-right execute">Click Me</a>
							</div>
						</div>
					</li>
				</ul>
				<ul class="list-group">
					<li class="list-group-item">
						<div class="row" onclick="makeAjaxRequest('/web-api/flights/searchForFlights', 'searchForFlightsAnchor');">
							<div class="col-md-8">
								<h5>
									Search For Flights
								</h5>
							</div>
							<div class="col-md-4">
								<a id="searchForFlightsAnchor" class="btn btn-primary pull-right execute">Click Me</a>
							</div>
						</div>
					</li>
				</ul>
				<ul class="list-group">
					<li class="list-group-item">
						<div class="row" onclick="makeAjaxRequest('/web-api/flights/getPromotions', 'getPromotionsAnchor');">
							<div class="col-md-8">
								<h5>
									Get Promotions
								</h5>
							</div>
							<div class="col-md-4">
								<a id="getPromotionsAnchor" class="btn btn-primary pull-right execute">Click Me</a>
							</div>
						</div>
					</li>
				</ul>
				<ul class="list-group">
					<li class="list-group-item">
						<div class="row" onclick="makeAjaxRequest('/web-api/flights/getAirportDetails', 'getAirportDetailsAnchor');">
							<div class="col-md-8">
								<h5>
									Get Airport Details
								</h5>
							</div>
							<div class="col-md-4">
								<a id="getAirportDetailsAnchor" class="btn btn-primary pull-right execute">Click Me</a>
							</div>
						</div>
					</li>
				</ul>
				<ul class="list-group">
					<li class="list-group-item">
						<div class="row" onclick="makeAjaxRequest('/web-api/flights/getFlightDetails', 'getFlightDetailsAnchor');">
							<div class="col-md-8">
								<h5>
									Get Flight Details
								</h5>
							</div>
							<div class="col-md-4">
								<a id="getFlightDetailsAnchor" class="btn btn-primary pull-right execute">Click Me</a>
							</div>
						</div>
					</li>
				</ul><ul class="list-group">
					<li class="list-group-item">
						<div class="row" onclick="makeAjaxRequest('/web-api/flights/chooseSeat', 'chooseSeatAnchor');">
							<div class="col-md-8">
								<h5>
									Choose Seat
								</h5>
							</div>
							<div class="col-md-4">
								<a id="chooseSeatAnchor" class="btn btn-primary pull-right execute">Click Me</a>
							</div>
						</div>
					</li>
				</ul>

			</form>
		</div>
	</div>
  </body>
</html>