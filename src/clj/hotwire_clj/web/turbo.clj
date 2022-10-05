(ns hotwire-clj.web.turbo
  (:require [clojure.string :as str]))

(def ^:const content-type "text/vnd.turbo-stream.html")

(defn turbo-frame-request?
  [{:keys [headers] :as _req}]
  (contains? headers "turbo-frame"))

(defn turbo-stream-request?
  [{:keys [headers] :as _req}]
  (let [request? (-> (get headers "accept")
                     (str/includes? content-type))]
    (println "turbo request? " request?)
    request?))
