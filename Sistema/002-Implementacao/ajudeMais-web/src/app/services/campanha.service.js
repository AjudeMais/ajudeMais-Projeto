/**
 * @ngdoc Service
 * @name campanhaService
 *
 * @description requisições à endpoints de campanhas de doação
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("campanhaService", campanhaService);

    campanhaService.$inject = ['$http', 'Api'];

    function campanhaService($http, Api) {

        var service = {
            save: _save,
            update: _update,
            getCampanhasByInstituicao: _getCampanhasByInstituicao,
            getCountCampanhasByInstituicao: _getCountCampanhasByInstituicao,
            getCampanhasAtivasByInstituicao: _getCampanhasAtivasByInstituicao
        };
        return service;

        /**
         *
         * @param campanha
         * @private
         */
        function _save(campanha) {
            return $http.post(Api + "/campanha", campanha);
        }

        /**
         *
         * @param campanha
         * @private
         */
        function _update(campanha) {
            return $http.put(Api + "/campanha", campanha);
        }

        /**
         *
         * @private
         */
        function _getCampanhasByInstituicao() {
            return $http.get(Api + "/campanha/filter/instituicao");
        };

        /**
         *
         * @param idInstituicao
         * @private
         */
        function _getCountCampanhasByInstituicao(idInstituicao) {
            return $http.get(Api + "/campanha/count/" + idInstituicao);
        }

        /**
         *
         * @param idInstituicao
         * @private
         */
        function _getCampanhasAtivasByInstituicao(idInstituicao) {
            return $http.get(Api + "/campanha/filter/instituicao/" + idInstituicao);
        }
    }
})();
