/**
 *
 */
app.factory('globalVars', globalVars);

function globalVars(){

	var service = {};

	service.appTitle = 'Trade Orders SLA monitoring';
	service.businessObject = 'TradeProcessingDuration';
	service.selectCountColumn = 'timestmp';
	service.updateIntervalMs = 1500;
	service.apiUrl= "svc/";
	//service.apiUrl= "http://localhost:8012/svc/";
	console.log("created globalVars");

	return service;

}
