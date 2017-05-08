(function () {
    angular.module('amApp')
        .config(['$localStorageProvider',
            function ($localStorageProvider) {
                $localStorageProvider.setKeyPrefix('_am-');
            }])
        .config(['$sessionStorageProvider',
            function ($sessionStorageProvider) {
                $sessionStorageProvider.setKeyPrefix('_am-');
            }])

})();