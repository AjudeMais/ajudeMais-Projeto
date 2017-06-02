/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de estados para acompanhamento de doações.
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular
        .module('amRoute')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('home.donativo', {
            url: "/donativo",
            template: '<ui-view />'
        })
            .state('home.donativo.list', {
                url: "/list",
                templateUrl: "app/components/donativo/donativos.html",
                controller: "DonativoController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Doações'
                }
            })
            .state('home.donativo.detail', {
                url: "/detail/:donativoDetail",
                templateUrl: "app/components/donativo/donativo.detail.html",
                controller: "DonativoDetailController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Detalhes de Doação'
                }
            })
    }
})();