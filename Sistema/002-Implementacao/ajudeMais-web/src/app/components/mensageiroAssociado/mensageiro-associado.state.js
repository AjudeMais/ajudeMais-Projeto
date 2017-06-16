/**
 * @ngdoc Route
 * @name amRoute
 *
 * @description mapeamento de estados para mensageiro associado.
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */

(function () {
    angular
        .module('amRoute')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('home.mensageirosAss', {
            url: "/mensageirosAssociados",
            templateUrl: "app/components/mensageiroAssociado/mensageiros-associados.html",
            controller: "MensageiroAssociadoController",
            controllerAs: 'vm',
            data: {
                pageTitle: 'Mensageiros Associados'
            }
        })
    }
})();