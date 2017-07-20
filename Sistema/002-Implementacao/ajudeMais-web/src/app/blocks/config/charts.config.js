/**
 * @ngdoc config
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .config(['ChartJsProvider', function (ChartJsProvider) {

            ChartJsProvider.setOptions({
                chartColors: [ '#b13227', '#00ADF9', '#1eb131', '#FDB45C', '#7916bf', '#949FB1', '#4D5360'],
                responsive: true,
                legend: {
                    display: true
                }
            });

            ChartJsProvider.setOptions('line', {
                showLines: true
            });
        }])
})();