"use strict";
angular.module('sportsClub').config(function($stateProvider, $urlRouterProvider){	
	$stateProvider
    .state('home', {
        url: '/home',
        templateUrl: 'app/components/home/home.html',
        controller: "homeCtrl"
    });
	$stateProvider
    .state('admin', {
        url: '/admin',
        templateUrl: 'app/components/adminBoard/adminBoard.html',
        controller: "adminBoardCtrl"
    });
    $stateProvider
        .state('manager', {
            url: '/manager',
            templateUrl: 'app/components/managerBoard/managerBoard.html',
            controller: "managerBoardCtrl",
            params: {
                managerId: null
            }
        });
	$urlRouterProvider.otherwise("/home");
});