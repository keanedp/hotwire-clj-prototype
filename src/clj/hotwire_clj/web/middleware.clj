(ns hotwire-clj.web.middleware
  (:require [ring.middleware
             [reload :as reload]
             [defaults :as defaults]
             [stacktrace :as stacktrace]
             [gzip :as gzip]
             [flash :as flash]]
            [muuntaja.middleware :as muuntaja-middleware]
            [taoensso.timbre :as log]
            [hotwire-clj.config :as config]
            [hotwire-clj.web.handlers.errors :as errors]))

(defn wrap-error
  [handler]
  (fn [req]
    (let [is-dev? config/is-dev?]
      (try
        (handler req)
        (catch Throwable t
          (log/error "Caught exception: " (.getMessage t))
          (if is-dev?
            (throw t)
            (errors/error-500 req)))))))

(defn wrap-request
  [handler]
  (let [is-dev? true]
    (-> handler
        (wrap-error)
        (stacktrace/wrap-stacktrace)
        (defaults/wrap-defaults defaults/site-defaults)
        (muuntaja-middleware/wrap-format)
        (gzip/wrap-gzip)
        (flash/wrap-flash)
        (cond-> is-dev? (reload/wrap-reload)))))
