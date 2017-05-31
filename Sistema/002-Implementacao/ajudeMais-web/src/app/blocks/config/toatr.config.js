(function () {
    angular.module('amApp')
        .config(function (toastrConfig) {
            angular.extend(toastrConfig, {
                timeOut: 2000,
                progressBar: false
            });
        });
})();