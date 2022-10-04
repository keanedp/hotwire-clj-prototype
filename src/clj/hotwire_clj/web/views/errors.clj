(ns hotwire-clj.web.views.errors
  (:require [ring.util.response :as r]
            [hotwire-clj.web.views.layout :as layout]))

(defn page-500
  [req]
  (-> (layout/wrap req {:title "Oops"} [:h1 {:style "color:red"} "An error occurred!"])
      r/response
      (r/status 500)
      (r/content-type "text/html")))

(defn page-404
  [req]
  (-> (layout/wrap req {:title "Page not found"} [:h1 {:style "color:red"} "Error, page not found!!"])
      r/response
      (r/status 404)
      (r/content-type "text/html")))
