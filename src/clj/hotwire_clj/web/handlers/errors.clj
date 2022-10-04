(ns hotwire-clj.web.handlers.errors
  (:require [hotwire-clj.web.views.errors :as errors]))

(defn error-500
  [req]
  (errors/page-500 req))

(defn error-404
  [req]
  (errors/page-404 req))
