/**
 * @ngdoc Controller
 * @name Login Controller
 * 
 * @description Controller para pagina de login
 * 
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function() {

	angular.module('amApp').controller('LoginController', function($state) {
		var vm = this;
		vm.usuario = {};

		/**
		 * 
		 */
		vm.doLogin = function() {
			/*TODO: implementar...*/
			$state.go("home");
		};
	});

})();
