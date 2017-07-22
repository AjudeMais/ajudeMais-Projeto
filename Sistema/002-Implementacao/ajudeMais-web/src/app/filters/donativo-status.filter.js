/**
 * @ngdoc filter
 * @name status
 *
 * @description Filtro para determinação de classe css a ser utilizada
 * de acordo com o estado da doação.
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module("amApp")
        .filter('status', function () {
            return function (input) {
                input = input || '';
                var out = 'label label-success';

                switch (input) {
                    case 'DISPONIBILIZADO':
                        out = 'label label-default';
                        break;
                    case 'ACEITO':
                        out = 'label label-info';
                        break;
                    case 'CANCELADO' || 'CANCELADO_POR_MENSAGEIRO':
                        out = 'label label-danger';
                        break;
                    case 'RECOLHIDO':
                        out = 'label label-primary';
                        break;

                    case 'NAO_ACEITO' :
                        out = 'label label-state-not-accepted';
                        break;
                }

                return out;
            };
        });
})();