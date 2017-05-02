(function () {
	angular.module('layout').directive('widget', widget);
	
	function widget() {
	    var directive = {
	        transclude: true,
	        template: '<div class="widget" ng-transclude></div>',
	        restrict: 'EA'
	    };
	    return directive;
	
	    function link(scope, element, attrs) {
	
	    }
	};
})();