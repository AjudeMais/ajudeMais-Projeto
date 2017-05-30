/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de estados para rotas de erros
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular
        .module('amRoute')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('home.403', {
            url: '/403',
            templateUrl: 'app/components/erros/403/403.html',
            data: {
                pageTitle: 'Acesso Negado'
            }
        })
            .state('500', {
                url: '/500',
                templateUrl: 'app/components/erros/500/500.html',
                data: {
                    pageTitle: 'Erro no Servidor'
                }
            })
            .state('refused', {
                url: '/refused',
                templateUrl: 'app/components/erros/refused/refused.html',
                data: {
                    pageTitle: 'Erro na conexão com o servidor'
                }
            })
    }
})();