/**
 * @ngdoc Controller
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .config(function (toastrConfig) {
            angular.extend(toastrConfig, {
                timeOut: 3000,
                progressBar: false
            });
        });
})();