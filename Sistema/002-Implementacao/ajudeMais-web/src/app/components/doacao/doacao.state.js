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

        $stateProvider.state('home.doacao', {
            url: "/doacao",
            template: '<ui-view />'
        })
            .state('home.doacao.list', {
                url: "/list",
                templateUrl: "app/components/doacao/doacoes.html",
                controller: "DoacaoController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Doações'
                }
            })
            .state('home.doacao.detail', {
                url: "/detail/:doacaoDetail",
                templateUrl: "app/components/doacao/doacao.detail.html",
                controller: "DoacaoDetailController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Detalhes de Doação'
                }
            })
    }
})();