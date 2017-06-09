/**
 *
 */
app.factory('indicators', indicators);

function indicators($http,globalVars){

	var service = {};
	service.apiRoot = globalVars.apiUrl + 'api/indicatorrecords/';

	service.get = function (businessObject,frequencyGroupValue,callback){

		var callurl = this.apiRoot + businessObject ;

		var httpMethod = {
				method: 'GET',
				url: callurl,
				headers: {
					'frequencyGroupValue': frequencyGroupValue
				}
		};

		$http(httpMethod).then(callback);
	}

	console.log("created indicators service");

	return service;
}
