/**
 * @ngdoc Run
 * @name DTDefaultOptions
 * 
 * @description configurações de tradução para módulos de datatables
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function() {
   angular.module('amApp').run(['DTDefaultOptions', function(DTDefaultOptions) {
            var language = {
                "sEmptyTable": "Nenhum dado encontrado",
                "sInfo": "De _START_ até _END_ de _TOTAL_ total",
                "sInfoEmpty": "de 0 até 0 de 0 total",
                "sInfoFiltered": "(filtreret ud af _MAX_ rækker ialt)",
                "sInfoPostFix": "",
                "sInfoThousands": ",",
                "sLengthMenu": "Mostrando _MENU_ itens",
                "sLoadingRecords": "Carregando dados...",
                "sProcessing": "Processando...",
                "sSearch": "Buscar:",
                "sZeroRecords": "Nenhum item encontrado no filtro",
                "oPaginate": {
                    "sFirst": "Primeiro",
                    "sLast": "Último",
                    "sNext": "Próximo",
                    "sPrevious": "Anterior"
                },
                "oAria": {
                    "sSortAscending": ": Ordenar ascendente",
                    "sSortDescending": ": Ordenar descrescente"
                }
            }

            DTDefaultOptions.setLanguage(language);
        }
    ]);

})();