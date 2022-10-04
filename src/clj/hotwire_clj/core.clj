(ns hotwire-clj.core
  (:require [hotwire-clj.web.server :as server]
            [hotwire-clj.db.core :as db]
            [clojure.java.io :as io])
  (:gen-class))

(defn -main
  [& args]
  (db/setup-db)
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
   (server/start-server port)))
