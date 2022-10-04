(ns hotwire-clj.web.server
  (:require [org.httpkit.server :as server]
            [hotwire-clj.web.handler :as handler]
            [taoensso.timbre :as log])
  (:gen-class))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (log/info "Shutting down web server")
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)
    (log/info "Completed shutting down web server")))

(defn start-server [port]
  (reset! server (server/run-server handler/app {:port port}))
  (println (str "Running webserver at http:/127.0.0.1:" port "/")))
