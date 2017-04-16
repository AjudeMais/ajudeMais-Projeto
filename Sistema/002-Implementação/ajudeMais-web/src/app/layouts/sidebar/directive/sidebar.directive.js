/**
 * @ngdoc Directive
 * @name menu
 * 
 * @description diretiva para menu do sidebar
 * 
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function() {
	angular.module('layout').directive('menu', function() {
		
		var ddo = {};
		ddo.restrict = 'E';
		ddo.replace = true;
		ddo.scope = {
			toggle : '&'
		}
		ddo.templateUrl = 'app/layouts/sidebar/directive/sidebar.directive.html';
	
		return ddo;
	});
})();