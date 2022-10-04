(ns hotwire-clj.web.views.layout
  (:require [hiccup
             [core :refer [html]]
             [page :refer [html5 include-css include-js]]]
            [ring.middleware.anti-forgery :as af]
            [hotwire-clj.web.turbo :as turbo]))

(defmulti wrap (fn [req {:keys [layout-name]} & body-content]
                 (if (turbo/turbo-request? req)
                   :partial
                   layout-name)))

(defmethod wrap :default
  [req {:keys [title script-section] :or {title "Demo Site"}} & body-content]
  (html5 [:head
          [:title title]
          [:meta {:name "csrf-token"
                  :content af/*anti-forgery-token*}]
          (include-css "/dist/index.bundle.css")
          (include-js "/dist/index.bundle.js")
          [:body
           [:nav.navbar {:role "navigation" :aria-label "main navigation"}
            [:div.navbar-brand
             [:a.navbar-item {:href "/"}
              [:span.brand "Hotwire Demo"]
              #_[:img {:src "https://bulma.io/images/bulma-logo.png" :width "112" :height "28"}]]
             [:a.navbar-burger {:role "button" :aria-label "menu" :aria-expanded "false" :data-target "navbarBasicExample"}
              [:span {:aria-hidden "true"}]
              [:span {:aria-hidden "true"}]
              [:span {:aria-hidden "true"}]]]
            [:div#navbarBasicExample.navbar-menu
             [:div.navbar-start
              [:a.navbar-item {:href "/messages"} "Messages"]
              [:a.navbar-item {:href "/about"} "About"]]]]

           [:div.container.is-desktop
            body-content]

           (when script-section
             script-section)]]))

(defmethod wrap :partial
  [_ _ & body-content]
  (html
    body-content))


