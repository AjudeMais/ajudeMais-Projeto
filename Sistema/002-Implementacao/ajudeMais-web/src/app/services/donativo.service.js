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
        .factory("donativoService", donativoService);

    donativoService.$inject = ['$http', 'Api'];

    function donativoService($http, Api) {

        var service = {
            getByInstituicao: _getByInstituicao,
            filterDonativoByEstadoAfterAceito: _filterDonativoByEstadoAfterAceito
        };
        return service;

        /**
         *
         * @private
         */
        function _getByInstituicao() {
            return $http.get(Api + "/donativo/filter/instituicao");
        };


        /**
         *
         * @private
         */
        function _filterDonativoByEstadoAfterAceito(id) {
            return $http.get(Api + "/donativo/filter/campanha/estado/"+id);
        };
    }
})();
