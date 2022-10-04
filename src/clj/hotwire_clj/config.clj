(ns hotwire-clj.config
  (:require [environ.core :refer [env]]
            [clojure.string :as str]))

(defn- config->bool
  [value & {:keys [default-value] :or {default-value false}}]
  (cond
    (boolean? value) value
    (string? value) (= "true" (str/lower-case value))
    :else default-value))

(def is-dev?
  (-> :is-dev
      env
      config->bool))

(def db-classname (:db-classname env))

(def db-subprotocol (:db-subprotocol env))

(def db-subname (:db-subname env))
