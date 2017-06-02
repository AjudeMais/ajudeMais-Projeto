/**
 * @ngdoc Service
 * @name campanhaService
 *
 * @description requisições à endpoints de doação
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("doacaoService", doacaoService);

    doacaoService.$inject = ['$http', 'Api'];

    function doacaoService($http, Api) {

        var service = {
            getByInstituicao: _getByInstituicao
        };
        return service;

        /**
         *
         * @private
         */
        function _getByInstituicao() {
            return $http.get(Api + "/doacao/filter/instituicao");
        };
    }
})();
