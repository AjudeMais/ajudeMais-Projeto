/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de rotas usando para app usando ui-router
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    var app = angular.module('amRoute', ['ui.router']);

    app.config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/login");
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'app/layouts/layout.html',
            data: {
                pageTitle: 'Home'
            }
        })
    });
})();