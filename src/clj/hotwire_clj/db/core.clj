(ns hotwire-clj.db.core
  (:require [toucan.db :as db]
            [toucan.models :as models]
            [hotwire-clj.config :as config]
            [clojure.java.io :as io]))

(defn setup-db []
  (db/set-default-db-connection!
    {:classname   config/db-classname
     :subprotocol config/db-subprotocol
     :subname     (str (io/resource config/db-subname))})

  (models/set-root-namespace! 'hotwire-clj.db.models)
  (db/set-default-automatically-convert-dashes-and-underscores! true))
