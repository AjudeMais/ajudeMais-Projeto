/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de estados para dashboards.
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular
        .module('amRoute')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('home.dashboard', {
            url: "/dashboard",
            template: '<ui-view />'
        })
            .state('home.dashboard.admin', {
                url: "/admin",
                templateUrl: "app/components/dashboard/admin/dashboard.admin.html",
                controller: "DashboardAdminController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Dashboard'
                }
            })
            .state('home.dashboard.instituicao', {
                url: "/instituicao",
                templateUrl: "app/components/dashboard/instituicao/dashboard.instituicao.html",
                controller: "DashboardInstController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Dashboard'
                }
            })
    }
})();