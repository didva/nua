var gulp    = require('gulp'),
    connect = require('gulp-connect'),
    connectLivereload = require('connect-livereload'),
    concat = require('gulp-concat'),
    spritesmith = require('gulp.spritesmith'),
    sass = require('gulp-sass');

var componentVendor = './src/components/**/';

var path = {
    'indexHtml': './src/*.html',
    'components': {
        'html' :  componentVendor + '*.html',
        'js'   :  componentVendor + '*.js',
        'css'  :  componentVendor + '*.css',
        'main_css' : componentVendor + '*_main.css',
        'midia_css' : componentVendor + '*_media.css',
        'main_sass' :  componentVendor + '*_main.scss',
        'media_sass' :  componentVendor + '*_media.scss'
    }
};

var destVendor = './build/';

var compiledDist = {
    'css' : destVendor +'styles'
};

/**
 * init gulp server
 * */
gulp.task('connect', function () {
    connect.server({
        root: '',
        port: 8020,
        livereload: true
    });
});

/**
 * livereload index*/
gulp.task('indexHtml', function(){
    gulp.src(path.indexHtml)
        .pipe(connect.reload())
});

/**
 * livereload components
 * */
/*livereload componentsHtml*/
gulp.task('componentsHtml', function(){
    gulp.src(path.components.html)
        .pipe(connect.reload())
});
/*livereload componentsJs*/
gulp.task('componentsJs', function(){
    gulp.src(path.components.js)
        .pipe(connect.reload())
});
/*livereload componentsCss*/
gulp.task('componentsCss', function(){
    gulp.src(path.components.main_css)
        .pipe(concat('main.css'))
        .pipe(connect.reload())
        .pipe(gulp.dest(compiledDist.css))

});
/*livereload componentsCss_media*/
gulp.task('componentsCss_media', function(){
    gulp.src(path.components.midia_css)
        .pipe(concat('media.css'))
        .pipe(connect.reload())
        .pipe(gulp.dest(compiledDist.css))

});
/*livereload componentsScss*/
gulp.task('componentsScss_main', function(){
    return gulp.src(path.components.main_sass)
        .on('error', function(err){ console.log(err.message); })
        .pipe(sass())
        .on('error', function(err){ console.log(err.message); })
        .pipe(concat('mainSass.css'))
        .pipe(gulp.dest(compiledDist.css))
        .pipe(connect.reload())
});

/*livereload componentsScss_media*/
gulp.task('componentsScss_media', function(){
    return gulp.src(path.components.media_sass)
        .on('error', function(err){ console.log(err.message); })
        .pipe(sass())
        .on('error', function(err){ console.log(err.message); })
        .pipe(concat('mediaSass.css'))
        .pipe(gulp.dest(compiledDist.css))
        .pipe(connect.reload())
});

/**
 * live reload*/
gulp.task('watch', ['componentsJs',
    'componentsHtml',
    'componentsCss',
    'componentsCss_media',
    'componentsScss_main',
    'componentsScss_media' ], function () {
    gulp.watch(path.indexHtml,       ['componentsHtml']);

    gulp.watch(path.components.html, ['indexHtml']);

    gulp.watch(path.components.js,   ['componentsJs']);

    /* gulp.watch(path.components.css,  ['componentsCss']);
     gulp.watch(path.components.midia_css,  ['componentsCss_media']);*/

    gulp.watch(path.components.main_sass,  ['componentsScss_main']);
    gulp.watch(path.components.media_sass,  ['componentsScss_media']);
});


/*sprite*/
gulp.task('sprite', function() {
    var spriteData =
        gulp.src('./src/img/*.*') // путь, откуда берем картинки для спрайта
            .pipe(spritesmith({
                imgName: 'sprite.png',
                cssName: 'sprite.scss',
                cssFormat: 'scss',
                algorithm: 'binary-tree',
                /* cssTemplate: 'stylus.template.mustache',*/
                cssVarMap: function(sprite) {
                    sprite.name = 's' + sprite.name
                }
            }));

    spriteData.img.pipe(gulp.dest('./build/images/')); // путь, куда сохраняем картинку
    spriteData.css.pipe(gulp.dest('./build/styles/')); // путь, куда сохраняем стили
});

gulp.task('default',['connect', 'watch']);