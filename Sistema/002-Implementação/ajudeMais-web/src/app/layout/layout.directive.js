(function() {
	angular.module('layout', ['angular-growl', 'angular-loading-bar']).directive('layout', function() {
		return {
			templateUrl : 'app/layout/layout.directive.html',
			restrict : 'E',
			replace : true,
		}
	});
})();