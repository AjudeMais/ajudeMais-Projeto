/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de estados para instituição
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular
        .module('amRoute')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('home.instituicao', {
            url: "/instituicao",
            template: '<ui-view />'
        })
            .state('home.instituicao.list', {
                url: "/list",
                templateUrl: "app/components/instituicao/instituicoes.html",
                controller: "InstituicaoController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Instituições'
                }
            })
            .state('home.instituicao.edit', {
                url: "/edit",
                templateUrl: "app/components/instituicao/instituicao.edit.html",
                controller: "InstituicaoEditController",
                controllerAs: 'vm',
                params: {
                    instituicaoEdit: null
                },
                data: {
                    pageTitle: 'Edição de Instituição'
                }
            })
            .state('home.instituicao.detail', {
                url: "/detail/:instituicaoDetail",
                templateUrl: "app/components/instituicao/instituicao.detail.html",
                controller: "InstituicaoDetailController",
                controllerAs: 'vm',
                data: {
                    pageTitle: 'Detalhes de Instituição'
                }
            })
    }
})();