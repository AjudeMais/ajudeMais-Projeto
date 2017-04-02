(function() {
angular.module('layout').directive('headerNotification',
	function() {
		return {
			templateUrl : 'app/layout/header/header-notification.directive.html',
			restrict : 'E',
			replace : true,
			controller: function($rootScope, $state, authenticationService) {
				var vm = this;
	
				/**
				 * 
				 */
				vm.logout = function() {
					/*TODO:implementar...*/
				}							
				
			},
			controllerAs: 'headerNotificationCtrl'
		}
	});
})();
