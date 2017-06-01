/**
 * 
 */
app.factory('serverTime', serverTime).run(serverTime);

function serverTime($http,$rootScope,$interval,globalVars){

	var service = {};
	//init var at start of program
	$rootScope.currentServerTime = "19700101000000.000";

	service.getTimeFromServer = function (callback){

		var callurl = globalVars.apiUrl + 'api/serverTime';

		var httpMethod = {
				method: 'GET',
				url: callurl
		};

		$http(httpMethod).then(callback);
	};

	service.updateTime = function() {
		service.getTimeFromServer(function(data) {
			$rootScope.currentServerTime = data.data;
		}.bind(service));
		
	};
	//first call
	service.updateTime();

	//set update interval;
	service.intervalPromise = $interval(function() {
		this.updateTime();
	}.bind(service), 1000);
	
	console.log('created serverTime service');


	return service;
};

