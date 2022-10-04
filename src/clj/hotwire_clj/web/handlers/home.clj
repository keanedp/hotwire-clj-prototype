(ns hotwire-clj.web.handlers.home
  (:require [hotwire-clj.web.views.home :as home-views]
            [hotwire-clj.db.models.message :refer [Message]]))

(defn index
  [req]
  (home-views/index req))

(defn about
  [req]
  (home-views/about req))
