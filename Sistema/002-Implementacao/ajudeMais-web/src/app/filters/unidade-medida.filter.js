/**
 * @ngdoc filter
 * @name status
 *
 * @description Filtro para mudança
 * de acordo com o estado da doação.
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
(function () {
    angular.module("amApp")
        .filter('capitalize', function () {
            return function (input) {
                return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
            };
        });
})();