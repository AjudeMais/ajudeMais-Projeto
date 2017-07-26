/**
 * @ngdoc Service
 * @name dashboardInstituicaoService
 *
 * @description .
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

(function () {
    angular.module("amApp")
        .factory("dashboardInstituicaoService", dashboardInstituicaoService);

    dashboardInstituicaoService.$inject = ['$http', 'Api'];

    function dashboardInstituicaoService($http, Api) {

        var service = {
            getCountDonativos: _getCountDonativos,
            getCountCampanhas: _getCountCampanhas,
            getCountMensageiros: _getCountMensageiros,
            getRankingMensageiro: _getRankingMensageiro,
            getDoacoesByPeriodoInstituicao: _getDoacoesByPeriodoInstituicao,
            getCountItens: _getCountItens,
            getCampanhasMetasProgres: _getCampanhasMetasProgres,
            getDonativosTimeline: _getDonativosTimeline
        };
        return service;


        /**
         *
         * @private
         */
        function _getCountDonativos() {
            return $http.get(Api + '/dashboard/instituicao/donativo/count');
        }

        /**
         *
         * @private
         */
        function _getCountCampanhas() {
            return $http.get(Api + '/dashboard/instituicao/campanha/count');
        }

        /**
         *
         * @private
         */
        function _getCountMensageiros() {
            return $http.get(Api + '/dashboard/instituicao/mensageiro/count');
        }

        /**
         *
         * @private
         */
        function _getRankingMensageiro() {
            return $http.get(Api + '/dashboard/instituicao/mensageiro/ranking');
        }

        /**
         *
         * @param nDays
         * @private
         */
        function _getDoacoesByPeriodoInstituicao(nDays, estado) {
            return $http.get(Api + '/dashboard/instituicao/donativo/periodo/instituicao', {
                params: {
                    "nDays": nDays,
                    "estado": estado
                }
            });
        }

        /**
         *
         * @private
         */
        function _getCountItens() {
            return $http.get(Api + '/dashboard/instituicao/itens/count');
        }

        /**
         *
         * @private
         */
        function _getCampanhasMetasProgres() {
            return $http.get(Api + '/dashboard/instituicao/campanha/metas')
        }

        function _getDonativosTimeline() {
            return $http.get(Api + '/dashboard/instituicao/donativo/timeline')
        }
    };
})();
