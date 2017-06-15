/**
 * @ngdoc Controller
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .controller("CampanhaDetailController", CampanhaDetailController);

    CampanhaDetailController.$inject = ['$stateParams', '$state', 'donativoService'];

    function CampanhaDetailController($stateParams, $state, donativoService) {
        var vm = this;
        vm.campanha = {};

        var campanhaDetailparam = JSON.parse($stateParams.campanhaDetail);

        if (campanhaDetailparam) {
            vm.campanha = campanhaDetailparam;
        }

        /**
         *
         */
        vm.previus = function () {
            $state.go('home.campanha.list');
        };

    }


})();