/**
 * @ngdoc Service
 * @name dashboardAdminService
 *
 * @description .
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular.module("amApp")
        .factory("dashboardAdminService", dashboardAdminService);

    dashboardAdminService.$inject = ['$http', 'Api'];

    function dashboardAdminService($http, Api) {

        var service = {
            getCountInstituicaoCaridade: _getCountInstituicaoCaridade,
            getCountDonativos: _getCountDonativos,
            getCountCampanhas: _getCountCampanhas,
            getCountMensageiros: _getCountMensageiros,
            getDoacoesByPeriodo: _getDoacoesByPeriodo
        };
        return service;


        /**
         *
         * @private
         */
        function _getCountInstituicaoCaridade() {
            return $http.get(Api + '/dashboard/admin/instituicao/count');
        };

        /**
         *
         * @private
         */
        function _getCountDonativos() {
            return $http.get(Api + '/dashboard/admin/donativo/count');
        }

        /**
         *
         * @private
         */
        function _getCountCampanhas() {
            return $http.get(Api + '/dashboard/admin/campanha/count');
        }

        /**
         *
         * @private
         */
        function _getCountMensageiros() {
            return $http.get(Api + '/dashboard/admin/mensageiro/count');
        }

        /**
         *
         * @param nDays
         * @private
         */
        function _getDoacoesByPeriodo(nDays, estado) {
            return $http.get(Api + '/dashboard/admin/donativo/periodo', {params: {"nDays": nDays, "estado": estado}});
        }

    };
})();
