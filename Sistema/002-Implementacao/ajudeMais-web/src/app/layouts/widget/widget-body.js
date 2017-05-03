(function() {
angular.module('layout').directive('widgetBody', widgetBody);

	function widgetBody() {
	    var directive = {
	        requires: '^widget',
	        scope: {
	            loading: '=?',
	            classes: '@?'
	        },
	        transclude: true,
	        template: '<div class="widget-body" ng-class="classes"><rd-loading ng-show="loading"></rd-loading><div ng-hide="loading" class="widget-content" ng-transclude></div></div>',
	        restrict: 'E'
	    };
	    return directive;
	};
})();
