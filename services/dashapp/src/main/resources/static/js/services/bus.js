app.factory('bus', bus);

function bus($rootScope,globalVars){

	var service = {};

  service.message = '';


  service.publishFrequencyGroupValue = function(msg) {
    this.message = msg;
    $rootScope.$broadcast('frequencyGroupValue');
  };


  console.log("created bus service");

	return service;
}
