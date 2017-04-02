
/**
 * @ngdoc Service
 * @name doadorService
 * 
 * @description requisições à endpoints de doador
 * 
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function(){
  angular.module("amApp").factory("doadorService", function($http, amValue){

    /**
	 * 
	 */
	var _getDoadores = function(callback) {
		$http.get(amValue.apiUri+"/doador").then(function(response) {
			callback(response.data);
		});
	};

	/**
	 * 
	 */
    var _salvar = function(doador, callback) {
		$http.post(amValue.apiUri+"/doador", doador).then(function(response) {
			callback(response.data);
		});
	};

    /**
	 * Altera um doador na API
	 */
    var _alterar = function(doador, callback) {
      $http.put(amValue.apiUri+"/doador", doador).then(function(response){
        callback(response.data);
      });
    };
    
    /**
	 * 
	 */
    var _remover = function(doador, callback) {
        $http.delete("doador/" + doador.id).then(function(response){
          callback(response.data);
        });
      };

    return {
    	getDoadores: _getDoadores,
    	salvar: _salvar,
    	alterar: _alterar,
    	remover: _remover
    };
    
  });
  
})();
