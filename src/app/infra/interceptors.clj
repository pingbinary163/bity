(ns app.infra.interceptors
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [clojure.string :as s]
            [app.utils.jwt :as jwt]))

;; Defines "/" and "/about" routes with their associated :get handlers.
;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
(def common-interceptors [(body-params/body-params)])

(def auth-interceptor
  {:name ::auth-interceptor
   :enter (fn [context]
            (let [authorization (-> context :request :headers :authorization)
                  authorization-split (s/split authorization #"\s+")
                  bearer? (= "Bearer" (authorization-split first))
                  token (authorization-split second)]
            (if (or (empty? token) (= "" jwt/decode token))
              (assoc context :response {:status 401}))))})
