/**
 * 
 */
app.factory('metrics', metrics);

function metrics($http,globalVars){
	
	var service = {};
	service.apiRoot = globalVars.apiUrl + 'api/metric/';
	
	service.get = function (businessObject,frequency,id,callback){

		var callurl = this.apiRoot + businessObject + '.' + frequency + '.' + id;
		
		var httpMethod = {
				method: 'GET',
				url: callurl
		};

		$http(httpMethod).then(callback);
	}

	console.log("created metrics service");
	
	return service;
}