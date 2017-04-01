/**
 * @ngdoc config
 * @name cfpLoadingBarProvider
 * 
 * @description configuração de posicionamento e formato de loading.
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function() {
	angular.module('amApp').config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
	    cfpLoadingBarProvider.parentSelector = '#loading-bar-container';
	    cfpLoadingBarProvider.spinnerTemplate = false;
	}])
})();