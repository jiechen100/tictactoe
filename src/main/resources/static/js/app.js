var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/board',{
            templateUrl: '/views/board.html',
            controller: 'boardController'
        })
        .when('/score',{
            templateUrl: '/views/score.html',
            controller: 'scoreController'
        })
        .otherwise(
            { redirectTo: '/'}
        );
});

