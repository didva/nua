angular.module('bcApp', ['ui.bootstrap', 'ngRoute', 'pascalprecht.translate', 'ngCookies', 'ngSanitize', 'ab-base64']);
angular.module('bcApp').config(['$routeProvider', '$translateProvider', function($routeProvider, $translateProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/main.html',
            controller: 'BcMainController'
        })
        .when('/catalogue', {
            templateUrl: 'views/catalogue.html',
            controller: 'BcCatalogueController',
            reloadOnSearch: false
        })
        .when('/profile', {
            templateUrl: 'views/profile.html',
            controller: 'BcProfileController'
        })
        .when('/catalogue/:searchText', {
            templateUrl: 'views/catalogue.html',
            controller: 'BcCatalogueController',
            reloadOnSearch: false
        })
        .when('/users', {
            templateUrl: 'views/users.html',
            controller: 'BcUsersController'
        })
        .when('/book/:bookId', {
            templateUrl: 'views/book.html',
            controller: 'BcBookController'
        })
        .when('/booklet/:bookId', {
            templateUrl: 'views/booklet.html',
            controller: 'BcBookletController'
        })
        .otherwise({redirectTo: '/'});

    $translateProvider.translations('en', {
        MAIN_NAME: 'LIBRARY OF RARE PRINTED BOOK RESOURCES',
        MAIN_FOND: 'From the collection of rare printed books and manuscripts of the scientific library of the University.',
        MAIN_RECTOR: 'Established on the initiative of Rector of Yaroslav Mudriy National Law University, Vasyl Yakovych Tatsiy.',
        MAIN_COUNT: 'At present the library of rare printed resources contains:',
        MAIN_BOOKS: 'Books',
        MAIN_PAGES: 'Pages'
    })
    .translations('ru', {
        MAIN_NAME: 'БИБЛИОТЕКА ЭЛЕКТРОННЫХ КОПИЙ РАРИТЕТНЫХ ИЗДАНИЙ',
        MAIN_FOND: 'Из фонда редких книг и рукописей научной библиотеки университета.',
        MAIN_RECTOR: 'Создается по инициативе ректора Национального юридического университета имени Ярослава Мудрого, Тация Василия Яковича.',
        MAIN_COUNT: 'Сейчас библиотека электронных копий раритетных изданий насчитывает:',
        MAIN_BOOKS: 'Книг',
        MAIN_PAGES: 'Страниц'
    })
    .translations('ua', {
        MAIN_NAME: 'БІБЛІОТЕКА ЕЛЕКТРОННИХ КОПІЙ РАРИТЕТНИХ ВИДАНЬ',
        MAIN_FOND: 'Із фонду рідкісних книг та рукописів наукової бібліотеки університету.',
        MAIN_RECTOR: 'Створюється за ініціативою ректора Національного юридичного університету імені Ярослава Мудрого, Тація Василя Яковича.',
        MAIN_COUNT: 'Наразі бібліотека електронних копій раритетних видань налічує:',
        MAIN_BOOKS: 'Книг',
        MAIN_PAGES: 'Сторінок'
    });    
    $translateProvider.preferredLanguage('ua');
    $translateProvider.useSanitizeValueStrategy('escape');
}]);
angular.module('bcApp').run(function run( $translate, $cookies, $http){
    var lang = $cookies.get('language');
    if(!lang) {
        lang = 'ua';
    }
    $translate.use(lang);
    var token = $cookies.get('token');
    if(token) {
        $http.defaults.headers.common['Authorization'] = 'Bearer ' + token;
    }
});
angular.module('bcApp').constant('appUrl', 'http://localhost:8080/bookschecker-reborn/');