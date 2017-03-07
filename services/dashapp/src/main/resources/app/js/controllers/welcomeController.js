/**
 * 
 */

app.controller('welcomeController',welcomeController);

function welcomeController($scope,globalVars){
	$scope.user = 'MasterOfTheUniverse';
	$scope.appTitle = globalVars.appTitle;
};