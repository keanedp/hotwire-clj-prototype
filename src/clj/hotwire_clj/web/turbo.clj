(ns hotwire-clj.web.turbo
  (:require [clojure.string :as str]))

(def ^:const content-type "text/vnd.turbo-stream.html")

(defn turbo-request?
  [{:keys [headers]}]
  (let [request? (-> (get headers "accept")
                     (str/includes? content-type))]
    (println "turbo request? " request?)
    request?))
