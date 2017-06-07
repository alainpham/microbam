/**
 * 
 */
var app = angular.module('app',['ngRoute']);

app.config(function($routeProvider,$locationProvider) {
	$locationProvider.hashPrefix('');
	$routeProvider.
	when('/', {
		templateUrl: 'views/rawData.html',
		controller: 'metricsController as metricsController'
	}).
	otherwise({
		redirectTo: '/'
	});
});
