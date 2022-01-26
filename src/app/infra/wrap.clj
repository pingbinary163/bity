(ns app.infra.wrap
  (:require [app.utils.jwt :as jwt]
            [clojure.data.json :as json]
            [app.utils.constants :refer [jwt-secret]]
            [app.handlers.helpers :as helpers]
            [app.handlers.helpers :refer [content-json]]))

;; global exception handler
(defn wrap-exception [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        (.printStackTrace e)
        {:status  500
         :headers content-json
         :body    (json/write-str
                    {:code "0"
                     :info (.getMessage e)})}))))

;; jwt authorization
;; https://github.com/ovotech/ring-jwt/blob/master/src/ring/middleware/jwt.clj
(defn- read-token-from-header
  "Finds the token by searching the specified HTTP header (case-insensitive) for a bearer token."
  [header-name]
  (fn [{:keys [headers]}]
    (some->> headers
             (filter #(.equalsIgnoreCase header-name (key %)))
             (first)
             (val)
             (re-find #"(?i)^Bearer (.+)$")
             (last))))

(defn wrap-jwt
  "Middleware that decodes a JWT token, verifies against the signature and then
  adds the decoded claims to the incoming request under :claims.
  If the JWT token exists but cannot be decoded then the token is considered tampered with and
  a 401 response is produced.
  If the JWT token does not exist, an empty :claims map is added to the incoming request."
  [handler]
  (fn [req]
    (try
      (let [token ((read-token-from-header "Authorization") req)
            user-id (jwt/decode token)]
        (if (empty? user-id)
          (helpers/unauthorized-handler)
          (->> (assoc req :claims {:user-id user-id})
               (handler))))
      (catch Exception e
        (helpers/unauthorized-handler)))))