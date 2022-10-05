(ns hotwire-clj.web.views.messages
  (:require [hotwire-clj.web.views.layout :as layout]
            [ring.util.response :as r]
            [ring.util.anti-forgery :as af]
            [hiccup.form :refer [form-to]]))

(defn- message-deleted-notification []
  [:div.notification.is-info.is-light
   "Message Deleted!"])

(defn- message-display
  [{:keys [id message]}]
  [:turbo-frame.box {:id (str "message_" id)} ;:data-turbo-action "advance"}

   [:p.message-paragraph message]
   [:a.button.is-primary {:href (str "/messages/" id "/edit")} "Edit"]
   " "
   [:form.is-inline-block {:method "POST" :action (str "/messages/" id) :data-turbo-confirm "Are you sure you want to delete this message?"}
    [:input {:type "hidden" :name "_method" :value "DELETE"}]
    (af/anti-forgery-field)
    [:button.button.is-danger {:type "submit"} "Delete"]]])

(defn- new-message-button
  []
  [:turbo-frame {:id "new_message"}
   [:div.level
    [:a.button.is-primary {:href "/messages/new"} "New Message"]]])

(defn index
  [{:keys [flash] :as req} messages]
  (println "req: " flash)
  (layout/wrap
    req
    {:title "Messages"}

    [:div.content
     [:div.block
      [:h1.title "Message Index"]
      [:p.subtitle "Home to all the messages"]]

     (when flash
       [:div.block
        [:div.notification.is-success
         flash]])

     [:div.block
      (new-message-button)]

     [:div.block
      [:turbo-frame {:id "messages"}
       (for [{:keys [is-deleted] :as message} messages]
         (if (= is-deleted 1)
           (message-deleted-notification)
           (message-display message)))]]]))

(defn show
  [req message]
  (layout/wrap
    req
    {:title "Message"}

    [:block
     [:h1.title "Message"]]
    (message-display message)))

(defn- message-form
  [{:keys [id message]}]
  (let [is-new? (nil? id)]
    (form-to [(if is-new? :post :put) (str "/messages" (when (not is-new?) (str "/" id)))]
             (af/anti-forgery-field)
             [:div.field
              [:label.label "Message Content"]
              [:div.control
               [:textarea.textarea {:name "message"} message]]]
             [:br]
             [:div.buttons
              [:a.button.is-link.is-light {:href "/messages"} "Cancel"]
              [:button.button.is-primary {:type "submit"} (if is-new? "Create" "Update")]])))

(defn edit
  [req {:keys [id] :as message} & {:keys [error?]}]
  (layout/wrap
    req
    {:title "Edit Message"}

    [:block
     [:h1.title "Edit Message"]]

    [:turbo-frame {:id (str "message_" id)}
     (when error?
       [:div.notification.is-danger.is-light
        "Unable to perform update!"])

     (message-form message)]))

(defn new
  [req & {:keys [message error?]}]
  (layout/wrap
    req
    {:title "New Message"}

    [:turbo-frame {:id "new_message"}
     (when error?
       [:div.notification.is-danger.is-light
        "Unable to create new message"])
     (message-form message)]))

(defn created
  [req message]
  (as-> (layout/wrap req {}
                     [:turbo-stream {:action "replace" :target "new_message"}
                      [:template
                       (new-message-button)]]
                     [:turbo-stream {:action "prepend" :target "messages"}
                      [:template
                       (message-display message)]]) response
         (r/response response)
         (r/header response "Content-Type" "text/vnd.turbo-stream.html")))

(defn deleted
  [req id]
  (as-> (layout/wrap req {} [:turbo-stream {:action "replace" :target (str "message_" id)}
                             [:template
                              (message-deleted-notification)]]) response
        (r/response response)
        (r/header response "Content-Type" "text/vnd.turbo-stream.html")))