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

        $stateProvider.state('home.403', {
            url : '/403',
            templateUrl : 'app/components/auth/403/403.html',
            data : {
                pageTitle : 'Acesso Negado'
            }
        })

        $stateProvider.state('home.500', {
            url : '/500',
            templateUrl : 'app/components/auth/500/500.html',
            data : {
                pageTitle : 'Erro no Servidor'
            }
        })

		.state('home', {
			url : '/home',
			templateUrl : 'app/layouts/layout.html',
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
