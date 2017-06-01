/**
 * 
 */
var app = angular.module('app',['ngRoute']);

app.config(function($routeProvider,$locationProvider) {
	$routeProvider.
	when('/', {
		templateUrl: 'views/rawData.html',
		controller: 'metricsController as metricsController'
	}).
	otherwise({
		redirectTo: '/'
	});
});
