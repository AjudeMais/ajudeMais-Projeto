/**
 * @ngdoc Service
 * @name mensageiroAssociadoService
 *
 * @description service referente aos serviços de associação de mensageiro com instuição.
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("mensageiroService", mensageiroService);

    mensageiroService.$inject = ['$http', 'Api'];

    function mensageiroService($http, Api) {

        var service = {
            findByContaEmail: _findByContaEmail
        };
        return service;

        /**
         * Busca mensageiros por conta, filtrando por email.
         * @param callback
         * @private
         */
        function _findByContaEmail(email) {
            return $http.get(Api + '/mensageiro/filter/contaEmail', {params: {"email": email}});
        };
    };
})();
