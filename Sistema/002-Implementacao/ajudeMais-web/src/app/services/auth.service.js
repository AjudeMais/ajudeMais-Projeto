/**
 * @ngdoc Service
 * @name authenticationService
 *
 * @description Services para controle de autenticação e autorização
 *
 * @author <a href="https://franckaj.github.io/">Franck Aragão</a>
 */
(function () {
    angular.module('amApp')
        .factory('authenticationService', authenticationService);

    authenticationService.$inject = ['$http', 'Api', '$sessionStorage', '$localStorage'];

    function authenticationService($http, Api, $sessionStorage, $localStorage) {

        var service = {
            doLogin: _doLogin,
            doLogout: _doLogout,
            getUserLogged: _getUserLogged,
            logged: _logged,
            isAuthorized: _isAuthorized,
            storageToken: _storageToken
        };
        return service;

        /**
         *
         * @param account
         * @param callback
         * @param callbackError
         * @private
         */
        function _doLogin(account, callback, callbackError) {
            $http.post(Api + "/auth/login", account).then(function (response) {
                callback(response.data);
            }, function (response) {
                callbackError(response.data);
            });
        };

        /**
         *
         * @param callback
         * @private
         */
        function _doLogout(callback) {
            delete $localStorage.at;
            delete $sessionStorage.at;
            $sessionStorage.sessionUser = undefined;
            callback();
        }

        /**
         *
         * @param callback
         * @private
         */
        function _getUserLogged(callback) {
            $http.get(Api + "/auth/user").then(function (response) {
                $sessionStorage.sessionUser = response.data;
                callback(response.data);
            });
        };

        /**
         *
         * @param callback
         * @private
         */
        function _logged(callback) {
            if ($sessionStorage.at === undefined) {
                callback(false);
            } else if ($sessionStorage.sessionUser === undefined) {
                $http.get(Api + "/auth/user").then(function (response) {
                    $sessionStorage.sessionUser = response.data;
                    callback(true);
                }, function (response) {
                    callback(false);
                });
                callback(true);
            }
        }

        /**
         *
         * @param authorizedRoles
         * @returns {boolean}
         * @private
         */
        function _isAuthorized(authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!$sessionStorage.sessionUser && $sessionStorage.sessionUser.grupos.indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }
            });
            return isAuthorized;
        }

        /**
         *
         * @param jwt
         * @private
         */
        function _storageToken(jwt) {
            $sessionStorage.at = jwt;
        }
    };
})();