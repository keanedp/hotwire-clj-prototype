(ns hotwire-clj.web.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [GET POST PUT DELETE defroutes]]
            [hotwire-clj.web.middleware :as middleware]
            [hotwire-clj.web.handlers
             [home :as home]
             [messages :as messages]
             [errors :as errors]]))

(defroutes app-routes
           (GET "/messages" [] messages/index)
           (GET "/messages/:id/edit" [id :as req] (messages/edit-message req id))
           (GET "/messages/new" [] messages/new-message)
           (POST "/messages" [] messages/create-message)
           (PUT "/messages/:id" [id :as req] (messages/update-message req id))
           (DELETE "/messages/:id" [id :as req] (messages/delete-message req id))

           (GET "/about" [] home/about)
           (GET "/" [] home/index)
           (route/not-found errors/error-404))

(def app (middleware/wrap-request #'app-routes))
