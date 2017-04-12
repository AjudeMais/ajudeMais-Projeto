/**
 * @ngdoc controller
 * @name DoadorController
 * 
 * @description Controller para consulta de doadores.
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function() {
	var app = angular.module('amApp');

	app.controller('DoadorController', function(doadorService, $uibModal, DTOptionsBuilder, DTColumnDefBuilder) {
		var vm = this;
		vm.doadores = [];

		vm.dtOptions = DTOptionsBuilder.newOptions()
		.withPaginationType('simple_numbers').withDisplayLength(10).withBootstrap();
		
		vm.dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0).notSortable() ];
		
		vm.buscarDoadores = function() {
            doadorService.getDoadores(function(resultado) {
				vm.doadores = resultado;
			});
		}

		vm.buscarDoadores();

		/**
		 * 
		 */
		function abrirModal(doador) {
			return $uibModal.open({
				templateUrl : 'app/components/private/doador/doador.edit.html',
				controller : 'PacienteModalController',
				controllerAs : 'vm',
				backdrop : 'static',
				resolve : {
					doador : function() {
						return doador;
					}
				}
			});
		}

		/**
		 * 
		 */
		vm.adicionarDoador = function() {
			var modal = abrirModal({});

			modal.result.then(function(doador) {
				vm.doadores.push(doador);
			});
		}

		/**
		 * 
		 */
		vm.editarDoador = function(doador) {
			abrirModal(doador);
		}
	});
})();
