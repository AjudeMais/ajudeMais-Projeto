/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de estados para categoria
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular
        .module('amRoute')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('home.categoria', {
            url: "/categoria",
            templateUrl: "app/components/categoria/categorias.html",
            controller: "CategoriaController",
            controllerAs: 'vm',
            data: {
                pageTitle: 'Itens Doáveis'
            }
        })
    }
})();