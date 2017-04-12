/**
 * @ngdoc Config
 * 
 * @description Configurações para requisições Http
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function() {
	angular.module('amApp').config(httpConfig);

	httpConfig.$inject = [ '$urlRouterProvider', '$httpProvider' ];

	var regexIso8601 = /^(\d{4}|\+\d{6})(?:-(\d{2})(?:-(\d{2})(?:T(\d{2}):(\d{2}):(\d{2})\.(\d{1,})(Z|([\-+])(\d{2}):(\d{2}))?)?)?)?$/;

	/**
	 * faz com que dados que vem json do servidor e transformados em um objeto
	 * tenham os tipos de dados de data convertidos para o objeto do tipo date.
	 */
	function convertDateStringsToDates(input) {
		if (typeof input !== "object")
			return input;

		for ( var key in input) {
			if (!input.hasOwnProperty(key))
				continue;

			var value = input[key];
			var match;
			if (typeof value === "string" && (match = value.match(regexIso8601))) {
				var milliseconds = Date.parse(match[0])
				if (!isNaN(milliseconds)) {
					input[key] = new Date(milliseconds);
				}
			} else if (typeof value === "object") {
				convertDateStringsToDates(value);
			}
		}
	}

	function httpConfig($urlRouterProvider, $httpProvider) {

	    $httpProvider.defaults.transformResponse.push(function(responseData){
	        convertDateStringsToDates(responseData);

	        return responseData;
	      });

        $httpProvider.interceptors.push('authInterceptor');
		$httpProvider.interceptors.push('errorResolverInterceptor');
		$httpProvider.interceptors.push('accessDeniedInterceptor');
	}
})();
