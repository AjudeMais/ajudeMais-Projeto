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
            getFilterDonativoByEstadoAndInstituicao: _getFilterDonativoByEstadoAndInstituicao,
            countDonativosByInstituicao: _countDonativosByInstituicao,
            countDonativosCampanhaByInstituicao: _countDonativosCampanhaByInstituicao
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
         */
        function _countDonativosByInstituicao(id) {
            return $http.get(Api + "/donativo/count/" + id);
        }

        /**
         *
         */
        function _countDonativosCampanhaByInstituicao(id) {
            return $http.get(Api + "/donativo/campanha/count/" + id);
        }

        /**
         *
         * @private
         */
        function _getFilterDonativoByEstadoAndInstituicao(estado) {
            return $http.get(Api + "/donativo/filter/estado", {params: {"estado": estado}});
        };


    }
})();
