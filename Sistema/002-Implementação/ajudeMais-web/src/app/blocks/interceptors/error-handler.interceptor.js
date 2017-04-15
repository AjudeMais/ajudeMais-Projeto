/**
 * @ngdoc Service
 * @name errorResolverInterceptor
 * 
 * @description Service para interceptação de erros do servidor
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */

(function(){
  angular.module('amApp').factory("errorResolverInterceptor", function($q, $location) {
	  
    var errorResolverInterceptor = {
        responseError: function(response) {
            if (response.status >= 500) {
              $location.path('home/500');
            }
            return $q.reject(response);
        }
    };
    
    return errorResolverInterceptor;
  });

})();
