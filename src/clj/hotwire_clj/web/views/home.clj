(ns hotwire-clj.web.views.home
  (:require [hotwire-clj.web.views.layout :as layout]))

(defn index
  [req]
  (layout/wrap
    req
    {:title          "Welcome"
     :script-section [:script {:type "application/javascript"} "console.log('hey from script section!');"]}

    [:div.content
     [:h1.title "Welcome to the demo site!!!"]
     [:p.subtitle "Some text goes here"]

     [:a {:href "/messages"} "View Messages"]]))

(defn about
  [req]
  (layout/wrap
    req
    {:title "About"}

    [:div.content
     [:h1.title "About"]
     [:p.subtitle "A Hotwire and Clojure demo site"]]))