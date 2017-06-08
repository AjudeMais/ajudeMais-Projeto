(function () {
    angular.module("amApp")
        .controller("InstituicaoDetailController", InstituicaoDetailController);

    InstituicaoDetailController.$inject = ['$stateParams', '$state'];

    function InstituicaoDetailController($stateParams, $state) {
        var vm = this;
        vm.instituicao = {};
        var instituicaoDetailparam = JSON.parse($stateParams.instituicaoDetail);

        if (instituicaoDetailparam) {
            vm.instituicao = instituicaoDetailparam;
        }

        /**
         *
         */
        vm.voltar = function () {
            $state.go('home.instituicao.list');
        };
    }


})();