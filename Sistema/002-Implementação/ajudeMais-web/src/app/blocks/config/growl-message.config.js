/**
 * @ngdoc Config
 * 
 * @description Configurações do modulo de meensagens 'Growl'
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function() {
   angular.module('amApp').config(function(growlProvider) {
       growlProvider.globalTimeToLive(3000);
       growlProvider.globalDisableCloseButton(true);
       growlProvider.globalPosition('top-center');
       growlProvider.globalDisableCountDown(true);
       
   });

})();