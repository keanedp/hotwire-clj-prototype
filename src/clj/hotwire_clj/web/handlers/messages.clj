(ns hotwire-clj.web.handlers.messages
  (:require [hotwire-clj.web.views.messages :as message-views]
            [hotwire-clj.db.models.message :refer [Message]]
            [toucan.db :as db]
            [taoensso.timbre :as log]
            [hotwire-clj.web.turbo :as turbo]
            [ring.util.response :as r]))

(defn index
  [req]
  (let [messages (db/select Message {:order-by [[:id :desc]]})]
    (message-views/index req messages)))

(defn edit-message
  [req id]
  (let [message (Message id)]
    (message-views/edit req message)))

(defn update-message
  [{:keys [params] :as req} id]
  (let [{:keys [message]} params
        db-message (Message id)
        updated-message (-> db-message
                            (assoc :message message))
        success? (try
                   (db/update! Message id updated-message)
                   (catch Exception e
                     (log/error "Exception when updating message: " (.getMessage e))
                     false))]
    (if success?
      (if (turbo/turbo-stream-request? req)
        (message-views/show req (if success? updated-message db-message))
        (-> (r/redirect "/messages" 303)
            (assoc :flash "Updated message!")))
      (message-views/edit req {:id id :message message} :error? true))))

(defn new-message
  [req]
  (message-views/new req))

(defn create-message
  [{:keys [params] :as req}]
  (let [{:keys [message]} params
        created-message (->> {:message message}
                             (db/insert! Message))]
    (if created-message
      (if (turbo/turbo-stream-request? req)
        (message-views/created req created-message)
        (-> (r/redirect "/messages" 303)
            (assoc :flash "Created message!")))
      (message-views/new req {:message message} :error? true))))


(defn delete-message
  [req id]
  (println "content types: " (get-in req [:headers "accept"]))
  (let [deleted? (db/update! Message id :is-deleted 1)]
    (if (turbo/turbo-stream-request? req)
      (message-views/deleted req id)
      (-> (r/redirect "/messages" 303)
          (assoc :flash "Deleted message!")))))
