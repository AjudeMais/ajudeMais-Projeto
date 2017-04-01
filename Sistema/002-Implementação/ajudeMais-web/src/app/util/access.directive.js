/**
 * @ngdoc Directive
 * @name access/access-view
 * 
 * @description directivas para para visualização de compomentes por papel de acesso.
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function() {
	
	/**
	 * Disabilita compoente
	 */
	angular.module("hyperactive.angular.utils").directive('access',
			function(authenticationService) {
				return {
					restrict : 'A',
					link : function($scope, element, attrs) {
						var roles = attrs.access.split(',');
						if (roles.length > 0) {
							if (!authenticationService.autorizado(roles)) {
								element.addClass("disabled");	
								element.attr("disabled", "disabled");
							}
						}
					}
				};
			});
	
	/**
	 * Esconde componente.
	 */
	angular.module("hyperactive.angular.utils").directive('accessView',
			function(authenticationService) {
				return {
					restrict : 'A',
					link : function($scope, element, attrs) {
						var roles = attrs.accessView.split(',');
						if (roles.length > 0) {
							if (!authenticationService.autorizado(roles)) {
								element.hide();
							}
						}
					}
				};
			});
})();
