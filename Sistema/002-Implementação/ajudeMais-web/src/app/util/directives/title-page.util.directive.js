/**
 * @ngdoc Directive
 * @name updateTitle
 * 
 * @description directiva atualiza titulo/header de acordo com a rota
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function() {
	angular.module("ajudeMais.utils").directive('updateTitle',function($rootScope, $timeout) {
		return {
		      link: function(scope, element) {

		        var listener = function(event, toState) {

		          var title = 'Ajude Mais';
		          if (toState.data && toState.data.pageTitle) title = toState.data.pageTitle;

		          $rootScope.pageTitle =  title;
		          
		        };

		        $rootScope.$on('$stateChangeSuccess', listener);
		      }
		    };

	});
})();