/**
 * @ngdoc Service
 * @name authenticationService
 * 
 * @description Services para controle de autenticação e autorização
 * 
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function() {
	angular.module('amApp').factory('authenticationService', function($http, $rootScope) {
		var resultado = {};

		resultado.doLogin = function(username, password, callback, callbackError) {
			/*TODO: implementar...*/
		};

		resultado.doLogout = function(callback) {
			/*TODO: implementar...*/
		};

		resultado.usuarioLogado = function(callback) {
			/*TODO: implementar...*/			
			callback(false);
		}

		/**
		 * 
		 */
		resultado.autorizado = function(authorizedRoles) {
			if (!angular.isArray(authorizedRoles)) {
				if (authorizedRoles == '*') {
					return true;
				}
				authorizedRoles = [ authorizedRoles ];
			}
			var isAuthorized = false;
			angular.forEach(authorizedRoles, function(authorizedRole) {
				var authorized = (!!$rootScope.sessionUser && $rootScope.sessionUser.conta.papeis.indexOf(authorizedRole) !== -1);
				if (authorized || authorizedRole == '*') {
					isAuthorized = true;
				}
			});
			return isAuthorized;
		}

		return resultado;
	});

})();