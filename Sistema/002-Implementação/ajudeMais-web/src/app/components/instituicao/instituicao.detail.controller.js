(function () {
    angular.module("amApp")
        .controller("InstituicaoDetailController", InstituicaoDetailController);

    InstituicaoDetailController.$inject = ['instituicaoService', '$stateParams'];

    function InstituicaoDetailController(instituicaoService, $stateParams) {
        var vm = this;
        vm.instituicao = {};
        var instituicaoDetailparam = JSON.parse($stateParams.instituicaoDetail);

        if (instituicaoDetailparam) {
            vm.instituicao = instituicaoDetailparam;
        }
    }
})();