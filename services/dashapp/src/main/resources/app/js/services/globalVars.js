/**
 * 
 */
app.factory('globalVars', globalVars);

function globalVars(){
	
	var service = {};
	
	service.appTitle = 'Trade Orders SLA monitoring';
	service.businessObject = 'TradeProcessingDuration';
	service.updateIntervalMs = 1500;
	service.apiUrl= "";
	console.log("created globalVars");
	
	return service;
	
}