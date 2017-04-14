/**
 * @ngdoc Route
 * @name amRoute
 * 
 * @description mapeamento de rotas usando ui-router
 * 
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function() {
	var app = angular.module('amRoute', [ 'ui.router' ]);

	app.config(function($stateProvider, $urlRouterProvider) {

		$urlRouterProvider.otherwise("/login");

		$stateProvider.state('login', {
			url : '/login',
			templateUrl : 'app/components/auth/login.html',
			controller : 'LoginController',
			controllerAs : 'vm',
			data : {
				pageTitle : 'Login'
			}
		})

        $stateProvider.state('403', {
            url : '/403',
            templateUrl : 'app/components/auth/403/403.html',
            data : {
                pageTitle : 'Acesso Negado'
            }
        })

		.state('home', {
			url : '/home',
			template : '<layout></layout>',
			data : {
				pageTitle : 'Home'
			}
		})
		
		.state('home.doador', {
			url : "/doador",
			templateUrl : "app/components/doador/daodores.html",
            controller : "DoadorController",
            controllerAs : 'vm',
            data : {
                pageTitle : 'Doadores'
            }
        });
	});
})();
