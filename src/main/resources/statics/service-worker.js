importScripts("js/pwa/precache-manifest.js?v=20190121");


importScripts('js/pwa/workbox-v3.6.3/workbox-sw.js');
workbox.setConfig({
    modulePathPrefix: 'js/pwa/workbox-v3.6.3/',
    debug: false
});
/**
 * @file service-worker.js with workbox api
 * @desc [example](https://workbox-samples.glitch.me/examples/workbox-sw/)
 * @author gao(someone@somecompany.com)
 */

/* globals workbox */
workbox.core.setCacheNameDetails({
    prefix: 'gakken-cache',
    suffix: 'v1',
    precache: 'install-time',
    runtime: 'run-time',
    googleAnalytics: 'ga'
});
workbox.skipWaiting();
workbox.clientsClaim();

workbox.precaching.precacheAndRoute(self.__precacheManifest || []);

/**
 * example runningCache with api
 */
// workbox.routing.registerRoute(/^https:\/\/lavas\.baidu\.com\/some\/api/,
//     workbox.strategies.networkFirst());
//workbox.routing.registerRoute(new RegExp('/img/*.*'), workbox.strategies.staleWhileRevalidate());


/**
 * example runningCache with resources from CDN
 * including maxAge, maxEntries
 * cacheableResponse is important for CDN
 */
// workbox.routing.registerRoute(/^https:\/\/cdn\.baidu\.com/i,
//     workbox.strategies.cacheFirst({
//         cacheName: 'lavas-cache-images',
//         plugins: [
//             new workbox.expiration.Plugin({
//                 maxEntries: 100,
//                 maxAgeSeconds: 7 * 24 * 60 * 60
//             }),
//             new workbox.cacheableResponse.Plugin({
//                 statuses: [0, 200]
//             })
//         ]
//     })
// );
//workbox.routing.registerNavigationRoute('/index.html');
