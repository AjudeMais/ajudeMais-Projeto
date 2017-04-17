'use strict';

exports.base = {
    port: 8000,
    apiDev: 'http://localhost:8080',
    apiProd: 'http://TODO',
    constantTemplate: '(function () {\n' +
    '    angular.module(\'<%- moduleName %>\')\n' +
    '<% constants.forEach(function(constant) { %>        .constant(\'<%- constant.name %>\', <%= constant.value %>)\n<% }) %>;\n' +
    '})();\n'
};

exports.paths = {
    dist: 'dist',
    src: 'src',
    tmp: 'tmp',
    static: [
        'src/content/img/**/*',
        'src/content/fonts/**/*'
    ],
    templates: [
        'src/app/**/**/*.html'
    ],
    vendors: [
        'src/vendors/jquery/dist/jquery.min.js',
        'src/vendors/iCheck/icheck.js',
        'src/angular-route/angular-route.js',
        'src/vendors/datatables.net/js/jquery.dataTables.js',
        'src/vendors/angular/angular.js',
        'src/vendors/angular-animate/angular-animate.js',
        'src/vendors/angular-ui-router/release/angular-ui-router.js',
        'src/vendors/ngstorage/ngStorage.js',
        'src/vendors/angular-datatables/dist/angular-datatables.js',
        'src/vendors/angular-datatables/dist/plugins/bootstrap/angular-datatables.bootstrap.js',
        'src/vendors/angular-bootstrap/ui-bootstrap-tpls.js',
        'src/vendors/angular-cookies/angular-cookies.js',
        'src/vendors/angular-growl-v2/build/angular-growl.js',
        'src/vendors/angular-loading-bar/build/loading-bar.js'
    ], scripts: [
        'src/app/*.js',
        'src/app/constants/*.js',
        'src/app/util/*.js',
        'src/app/services/**/*.js',
        'src/app/util/directives/*.js',
        'src/app/blocks/**/*.js',
        'src/app/layouts/*.js',
        'src/app/layouts/header/*.js',
        'src/app/layouts/notification/*.js',
        'src/app/layouts/sidebar/directive/*.js',
        'src/app/layouts/sidebar/*.js',
        'src/app/layouts/widget/*.js',
        'src/app/components/**/*.js',
        'tmp/*.js'
    ],
    css: [
        'src/vendors/bootstrap/dist/css/bootstrap.min.css',
        'src/content/css/main.css',
        'src/vendors/iCheck/skins/square/_all.css',
        'src/vendors/font-awesome/css/font-awesome.css',
        'src/vendors/angular-growl-v2/build/angular-growl.min.css',
        'src/vendors/angular-datatables/dist/css/angular-datatables.css',
        'src/vendors/angular-datatables/dist/plugins/bootstrap/datatables.bootstrap.min.css',
        'src/vendors/angular-loading-bar/build/loading-bar.css'
    ],
    fonts: [
        'src/vendors/font-awesome/fonts/fontawesome-webfont.*',
        'src/vendors/bootstrap/fonts/glyphicons-halflings-regular.*'
    ],

}
