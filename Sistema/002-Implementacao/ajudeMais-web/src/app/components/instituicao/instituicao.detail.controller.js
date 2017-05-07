(function () {
    angular.module("amApp")
        .controller("InstituicaoDetailController", InstituicaoDetailController);

    InstituicaoDetailController.$inject = ['instituicaoService', '$stateParams', '$state'];

    function InstituicaoDetailController(instituicaoService, $stateParams,$state) {
        var vm = this;
        vm.instituicao = {};
        var instituicaoDetailparam = JSON.parse($stateParams.instituicaoDetail);
        if (instituicaoDetailparam) {
            vm.instituicao = instituicaoDetailparam;
            console.log(vm.instituicao);

        }


        /**
         *
         */
        vm.voltar = function () {
            $state.go('home.instituicao');
        };
    }



})();