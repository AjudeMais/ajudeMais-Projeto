(function() {
	angular.module('layout', ['angular-growl', 'angular-loading-bar']).directive('layout', function() {
		return {
			templateUrl : 'app/components/layout/wrapper/layout.directive.html',
			restrict : 'E',
			replace : true,
		}
	});
})();