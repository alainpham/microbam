/**
 *
 */
app.factory('projections', projections);

function projections($http,globalVars){

	var service = {};
	service.apiRoot = globalVars.apiUrl + 'api/indicatorrecords/projection/';

	service.get = function (businessObject,frequencyGroupValue,select,groupBy,callback){

		var callurl = this.apiRoot + businessObject ;

		var httpMethod = {
				method: 'GET',
				url: callurl,
				headers: {
					'select': select,
					'groupBy': groupBy,
					'frequencyGroupValue': frequencyGroupValue
				}
		};

		$http(httpMethod).then(callback);
	}

	console.log("created projections service");

	return service;
}
