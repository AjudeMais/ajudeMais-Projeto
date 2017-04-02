var gulp = require('gulp');

var browserSync = require('browser-sync');
var runSequence = require('run-sequence');
var concat = require('gulp-concat');
var wrap = require('gulp-wrap');
var uglify = require('gulp-uglify');
var htmlmin = require('gulp-htmlmin');
var gulpif = require('gulp-if');
var cleanCSS = require('gulp-clean-css');
var ngAnnotate = require('gulp-ng-annotate');
var templateCache = require('gulp-angular-templatecache');
var del = require('del');
var path = require('path');

var config = require('./config');

gulp.task('clean', function() {
    return del(config.paths.dist + '/**/*', {
        dot: true
    });
});

gulp.task('copy', ['clean'], function() {
    return gulp.src(config.paths.static, {
            base: config.paths.src
        })
        .pipe(gulp.dest(config.paths.dist));
});

gulp.task('templates', function() {
    return gulp.src(config.paths.src + '/app/components/**/*.html')
        .pipe(htmlmin({
            collapseWhitespace: true
        }))
        .pipe(templateCache({
            module: 'templates',
            root: 'app',
            standalone: true,
            moduleSystem: 'IIFE'
        }))
        .pipe(gulp.dest('./'));
});

gulp.task('plugins', ['templates'], function() {
    return gulp.src(config.paths.vendors)
        .pipe(concat('vendors.js'))
        .pipe(uglify())
        .pipe(gulp.dest(config.paths.dist + '/app/js/'));
});

gulp.task('styles', function() {
    return gulp.src(config.paths.css)
        .pipe(cleanCSS())
        .pipe(concat('main.min.css'))
        .pipe(gulp.dest(config.paths.dist + '/content/css/'));
});

gulp.task('scripts', ['plugins'], function() {
    return gulp.src([
            config.paths.src + '/app/**/*.js',
            './templates.js'
        ])
        .pipe(wrap('(function(angular){\n\'use strict\';\n<%= contents %>})(window.angular);'))
        .pipe(concat('scripts.js'))
        .pipe(ngAnnotate())
        .pipe(uglify())
        .pipe(gulp.dest(config.paths.dist + '/app/js/'));
});


gulp.task('browser-sync', function() {

    browserSync({
        open: true,
        port: config.base.port,
        files: [config.paths.src + '/**'],
        server: {
            baseDir: config.paths.src
        }
    });
});

gulp.task('reload-css', function() {
    gulp.src(config.paths.css)
        .pipe(browserSync.reload({
            stream: true
        }));
});

gulp.task('watch', function() {
    gulp.watch(config.paths.css, ['reload-css']);
    gulp.watch(config.paths.src + '/app');
});

gulp.task('default', ['browser-sync', 'watch']);

gulp.task('start', ['browser-sync', 'watch']);

gulp.task('build', function(cb) {
    return runSequence('clean', ['copy', 'styles', 'scripts'], cb)
});
