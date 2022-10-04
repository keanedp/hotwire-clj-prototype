(defproject hotwire-clj "0.1.0-SNAPSHOT"
  :description "Prototype Hotwire + Clojure site"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url  ""}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [http-kit "2.3.0"]
                 [compojure "1.7.0"]
                 [ring/ring-defaults "0.3.4"]
                 [ring/ring-devel "1.9.6"]
                 [amalloy/ring-gzip-middleware "0.1.4"]
                 [metosin/muuntaja "0.6.8"]
                 [hiccup "1.0.5"]
                 [environ "1.2.0"]
                 [com.taoensso/timbre "5.2.1"]
                 [toucan "1.18.0"]
                 [org.xerial/sqlite-jdbc "3.39.3.0"]
                 [migratus/migratus "1.4.4"]]
  :plugins [[lein-environ "1.2.0"]
            [migratus-lein "0.7.3"]]
  :source-paths ["src/clj" "src/cljc"]
  :main ^:skip-aot hotwire-clj.core
  :target-path "target/%s"
  :migratus {:store         :database
             :migration-dir "migrations"
             :db            {:classname      "org.sqlite.JDBC"
                             :subprotocol    "sqlite"
                             :connection-uri "jdbc:sqlite:resources/db/app.db"}}
  :profiles {:dev     {:env {:is-dev         "true"
                             :db-classname   "org.sqlite.JDBC"
                             :db-subprotocol "sqlite"
                             ;; db resides under resources/db/app.db
                             :db-subname     "db/app.db"}}
             :uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
