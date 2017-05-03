var gulp = require('gulp');
var browserSync = require('browser-sync');
var runSequence = require('run-sequence');
var concat = require('gulp-concat');
var wrap = require('gulp-wrap');
var uglify = require('gulp-uglify');
var htmlmin = require('gulp-htmlmin');
var cleanCSS = require('gulp-clean-css');
var ngAnnotate = require('gulp-ng-annotate');
var templateCache = require('gulp-angular-templatecache');
var del = require('del');
var path = require('path');
var inject = require('gulp-inject');
var rename = require('gulp-rename');
var ngConstant = require('gulp-ng-constant');

var config = require('./config');

gulp.task('clean', function () {
    return del(config.paths.dist + '/**/*', {
        dot: true
    });
});

gulp.task('copy-fonts', function () {
    return gulp.src(config.paths.fonts)
        .pipe(gulp.dest(config.paths.dist + '/content/fonts'));
});

gulp.task('copy', ['clean', 'copy-fonts'], function () {
    return gulp.src(config.paths.static, {
        base: config.paths.src
    })
        .pipe(gulp.dest(config.paths.dist));
});

gulp.task('templates', function () {
    return gulp.src(config.paths.templates)
        .pipe(htmlmin({
            collapseWhitespace: true
        }))
        .pipe(templateCache({
            module: 'templates',
            root: 'app',
            standalone: true,
            moduleSystem: 'IIFE'
        }))
        .pipe(gulp.dest(config.paths.tmp));
});

gulp.task('vendors', ['templates'], function () {
    return gulp.src(config.paths.vendors)
        .pipe(concat('plugins.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest(config.paths.dist + '/app/js/'));
});

gulp.task('scripts', ['vendors'], function () {
    return gulp.src(config.paths.scripts)
        .pipe(wrap('(function(angular){\n\'use strict\';\n<%= contents %>})(window.angular);'))
        .pipe(concat('scripts.min.js'))
        .pipe(ngAnnotate())
        .pipe(uglify())
        .pipe(gulp.dest(config.paths.dist + '/app/js/'));
});

gulp.task('styles', function () {
    return gulp.src(config.paths.css)
        .pipe(cleanCSS())
        .pipe(concat('main.min.css'))
        .pipe(gulp.dest(config.paths.dist + '/content/css/'));
});

gulp.task('inject', function () {
    var sources = gulp.src([
        config.paths.dist + '/app/**/*.js',
        config.paths.dist + '/content/**/*.css'], {read: false});

    return gulp.src('./src/index.html')
        .pipe(inject(sources, {ignorePath: 'dist'}))
        .pipe(gulp.dest('./dist'));
});

gulp.task('ngconstant:dev', function () {
    return ngConstant({
        name: 'amApp',
        constants: {
            Api: config.base.apiDev
        },
        template: config.base.constantTemplate,
        stream: true
    })
        .pipe(rename('app.constants.js'))
        .pipe(gulp.dest(config.paths.src + '/app/constants'));
});

gulp.task('ngconstant:prod', function () {
    return ngConstant({
        name: 'amApp',
        constants: {
            Api: config.base.apiProd
        },
        template: config.base.constantTemplate,
        stream: true
    })
        .pipe(rename('app.constants.js'))
        .pipe(gulp.dest(config.paths.src + '/app/constants'));
});

gulp.task('browser-sync', function () {
    browserSync({
        open: true,
        port: config.base.port,
        files: [config.paths.src + '/**'],
        server: {
            baseDir: config.paths.src
        }
    });
});

gulp.task('reload-css', function () {
    gulp.src(config.paths.css)
        .pipe(browserSync.reload({
            stream: true
        }));
});

gulp.task('watch', function () {
    gulp.watch(config.paths.css, ['reload-css']);
    gulp.watch(config.paths.src + '/app');
});

gulp.task('install', function (cb) {
    return runSequence('browser-sync', 'watch', ['ngconstant:dev'], cb)
});

gulp.task('default', ['install']);

gulp.task('start', ['install']);

gulp.task('build', function (cb) {
    return runSequence('clean', 'copy', 'styles', 'ngconstant:prod', 'scripts', ['inject'], cb)
});