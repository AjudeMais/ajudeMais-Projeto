/**
 * @ngdoc Service
 * @name errorResolverInterceptor
 * 
 * @description Service para interceptação de erros do servidor
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */

(function(){
  angular.module('amApp').factory("accessDeniedInterceptor", function($q, $location) {
	  
	    var accessDeniedInterceptor = {
	            responseError: function(response) {
	                if (response.status === 403) {
	                	$location.path("/login");
	                }
	                return $q.reject(response);
	            }
	        };
	        return accessDeniedInterceptor;
  });

})();
