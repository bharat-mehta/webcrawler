# webcrawler

This application is developed to crawl any website. 

The application creates fixed thread pool to read the given URL.

### How to build and run ?

* Install Java 8
* Run `mvn clean install ' . This will run the tests and create executable jar
* To use the application , run `java -jar  webcrawler-0.1-SNAPSHOT.jar http://wiprodigital.com 100 50000` where `100` denotes the maxDepth and `50000` denotes max links. 
* To use the application the user must specify a URL in command line arguments


### Report

After scanning/reading a webpage, application creates a page.json file like :
```json
{
    "url": "http://wiprodigital.com/?s\u003d\u0026post_type[]\u003dnews",
    "depth": 2,
    "referrer": "http://wiprodigital.com",
    "statusCode": 200,
    "milliSeconds": 2068,
    "media": {
        "img": [
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/images/logo.png"
        ],
        "script": [
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/scripts/jquery.slicknav.min.js",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/scripts/wiprodigital.js",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/plugins/ajax-load-more/core/dist/js/ajax-load-more.min.js?ver\u003d3.1.2",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/scripts/wdblog.js",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-includes/js/jquery/jquery.js?ver\u003d1.12.4",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/scripts/jquery-1.12.0.min.js",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-includes/js/jquery/jquery-migrate.min.js?ver\u003d1.4.1",
            "http://js.hsforms.net/forms/v2.js",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/scripts/bootstrap.min.js",
            "http://js.hs-scripts.com/621527.js",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/scripts/modernizr.min.js",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-includes/js/wp-embed.min.js?ver\u003d4.6.4"
        ]
    },
    "imports": {
        "stylesheet": [
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/plugins/masterslider/public/assets/css/masterslider.main.css?ver\u003d2.29.0",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/uploads/masterslider/custom.css?ver\u003d9.5",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/style.css",
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/plugins/ajax-load-more/core/dist/css/ajax-load-more.min.css?ver\u003d4.6.4"
        ],
        "apple-touch-icon-precomposed": [
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/uploads/2016/08/Fav_icon_144x144.png"
        ],
        "EditURI": [
            "http://wiprodigital.com/xmlrpc.php?rsd"
        ],
        "wlwmanifest": [
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-includes/wlwmanifest.xml"
        ],
        "dns-prefetch": [
            "http://s.w.org"
        ],
        "icon": [
            "http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/uploads/2016/08/Fav_icon_144x144.png"
        ],
        "pingback": [
            "http://wiprodigital.com/xmlrpc.php"
        ],
        "publisher": [
            "https://plus.google.com/+Wiprodigital"
        ],
        "alternate": [
            "http://wiprodigital.com/search/feed/rss2/"
        ],
        "https://api.w.org/": [
            "http://wiprodigital.com/wp-json/"
        ]
    },
    "hyperLinks": [
        "http://wiprodigital.com",
        "http://wiprodigital.com/who-we-are",
        "http://wiprodigital.com/who-we-are/#wdteam-vid",
        "http://wiprodigital.com/who-we-are#wdteam_meetus",
        "http://wiprodigital.com/who-we-are#wdteam_leaders",
        "http://wiprodigital.com/what-we-do",
        "http://wiprodigital.com/what-we-do#work-three-circles-row",
        "http://wiprodigital.com/what-we-do#wdwork_cases",
        "http://wiprodigital.com/what-we-do#wdwork_partners",
        "http://wiprodigital.com/what-we-think",
        "http://wiprodigital.com/?s\u003d\u0026post_type[]\u003dpost",
        "http://wiprodigital.com/?s\u003d\u0026post_type[]\u003dcases",
        "http://wiprodigital.com/?s\u003d\u0026post_type[]\u003devents",
        "http://wiprodigital.com/?s\u003d\u0026post_type[]\u003dnews",
        "http://wiprodigital.com/join-our-team",
        "http://wiprodigital.com/join-our-team#wdwork-vid",
        "http://wiprodigital.com/join-our-team#wdcareers_team",
        "http://wiprodigital.com/join-our-team#wdcareers_jobs",
        "http://wiprodigital.com/get-in-touch",
        "http://wiprodigital.com/get-in-touch#wddi-locations",
        "http://wiprodigital.com/get-in-touch#wddi-contact",
        "https://www.facebook.com/WiproDigital/",
        "https://twitter.com/wiprodigital",
        "https://www.linkedin.com/company/wipro-digital",
        "http://wiprodigital.com/privacy-policy"
    ]
}
```

### Note

I have used just 5 threads in the threadpool to save from   `Too many requests` error
