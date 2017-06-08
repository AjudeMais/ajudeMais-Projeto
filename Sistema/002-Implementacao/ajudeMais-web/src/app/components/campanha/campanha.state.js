/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de estados para campanhas de doação
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular
        .module('amRoute')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('home.campanha', {
            url: "/campanha",
            template: '<ui-view />'
        })
            .state('home.campanha.list', {
                url: "/list",
                templateUrl: "app/components/campanha/campanhas.html",
                controller: "CampanhaController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Campanhas de Doação'
                }
            })
            .state('home.campanha.edit', {
                url: "/edit",
                templateUrl: "app/components/campanha/campanha.edit.html",
                controller: "CampanhaEditController",
                controllerAs: 'vm',
                params: {
                    campanhaEdit: null
                },
                data: {
                    pageTitle: 'Edição de Campanha'
                }
            })
            .state('home.campanha.detail', {
                url: "/detail/:campanhaDetail",
                templateUrl: "app/components/campanha/campanha.detail.html",
                controller: "CampanhaDetailController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Detalhes de Campanha'
                }
            })
    }
})();