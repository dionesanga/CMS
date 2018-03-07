var ticketApp = angular.module("ticketApp", ["ngRoute"]);

ticketApp.config(function($routeProvider) {		
	$routeProvider
	
	.when('/', {
		templateUrl : 'ITickets.html',
		controller  : 'mainController'
	})
	.when('/abrir-ticket', {
		templateUrl : 'ticket/IAbrirTicket.html',
		controller  : 'abrirController'
	})
	.when('/fechar-ticket', {
		templateUrl : 'ticket/IFecharTicket.html',
		controller  : 'fecharController'
	})
	.when('/consultar-ticket', {
		templateUrl : 'ticket/IConsultarTicket.html',
		controller  : 'consultarController'
	});
	
});

ticketApp.controller('mainController', function($scope, $http){
	var self 				= this;
	
	$scope.vShowMessage		= false;
	$scope.vMessage			= 'Teste';
});

ticketApp.controller('abrirController', function($scope, $http){
	var self 				= this;
	
	$('.input-group.date').datepicker({
	    format: "dd/mm/yyyy",
	    todayBtn: "linked",
	    autoclose: true,
	    todayHighlight: true
	    });
	
	$scope.vShowMessage		= false;
	$scope.vMessage			= 'Teste';
});