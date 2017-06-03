/**
 * @ngdoc Controller
 * @name DonativoDetailController
 *
 * @description Controller para página de visualização de detalhes de um danativo.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .controller("DonativoDetailController", DonativoDetailController);

    DonativoDetailController.$inject = ['$stateParams', '$state'];

    function DonativoDetailController($stateParams, $state) {
        var vm = this;
        vm.donativo = {};
        var donativoDetailparam = JSON.parse($stateParams.donativoDetail);

        if (donativoDetailparam) {
            vm.donativo = donativoDetailparam;
        }

        /**
         *
         */
        vm.previus = function () {
            $state.go('home.donativo.list');
        };
    }
})();