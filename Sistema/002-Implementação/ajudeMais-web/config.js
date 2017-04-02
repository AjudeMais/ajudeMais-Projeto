'use strict';

exports.base = {
    port: 8000,
    apiUri: 'http://localhost:8080',
};
exports.paths = {
    dist: 'dist',
    src: 'src',
    tmp: 'tmp',
    static: ['src/index.html'],
    vendors: ['src/vendors/**/*.js'],
    css: ['src/content/css/*.css'],
}
