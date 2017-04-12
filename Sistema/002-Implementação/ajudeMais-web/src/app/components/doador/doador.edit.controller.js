/**
 * @ngdoc controller
 * @name PacienteModalController
 * 
 * @description Controller para edição de um paciente.
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */

(function() {
	angular.module("amApp").controller("PacienteModalController", function(doadorService, $uibModalInstance, growl, doador) {
		
		var vm = this;
		vm.doador = {};
		vm.editar = true;
		vm.loading = false;
		angular.copy(doador, vm.doador);

		isEdicao();

		/**
	      * @name v.salvar
	      * @desc edição e cadastro de um doador
		 */
		vm.salvar = function() {
			vm.loading = true;

			if (!vm.editar) {
				doadorService.salvar(vm.doador, function(response) {
					growl.success("<b>Doador</b> criado com sucesso");
					angular.copy(response, doador);
					vm.loading = false;
					$uibModalInstance.close(doador);
				});
			} else {
				doadorService.alterar(vm.doador, function(response) {
					growl.success("<b>Doador</b> alterado com sucesso");
					
					angular.copy(response, doador);
					vm.loading = false;
					$uibModalInstance.close(doador);
				});
			}			
		}

		/**
		 * 
		 */
		vm.cancelar = function() {
			$uibModalInstance.dismiss('cancel');
		}
		
	      /**
	      * @name isEdicao
	      * @desc verifica se um doador de paramentro é um cadastro ou edição
	      * @returns {boolean}
	      */
		function isEdicao() {
			if (vm.doador.id == undefined) {
				vm.editar = false;
				vm.titulo = "Novo Doador";
			} else {
				vm.editar = true;
				vm.titulo = "Alterar Doador";
			}
			return vm.editar;
		}
	});
})();
