/**
 * @ngdoc Service
 * @name errorResolverInterceptor
 * 
 * @description Service para interceptação de erros do servidor
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */

(function(){
  angular.module('amApp').factory("errorResolverInterceptor", function($q, growl) {
	  
    var errorResolverInterceptor = {
        responseError: function(response) {
            if (response.status >= 500) {
              growl.error("Erro na comunicação com servidor. Tente novamente mais tarde");
            }
            return $q.reject(response);
        }
    };
    
    return errorResolverInterceptor;
  });

})();
