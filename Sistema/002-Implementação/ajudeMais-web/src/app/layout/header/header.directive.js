/**
 * @ngdoc Directive
 * @name header
 * 
 * @description diretiva para header do layout principal.
 * 
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function() {
	angular.module('layout').directive('header', function() {
		return {
			templateUrl : 'app/layout/header/header.directive.html',
			restrict : 'E',
			replace : true,
		}
	});
})();