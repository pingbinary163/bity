(ns app.utils.jwt
  (:require [buddy.sign.jwt :as jwt])
  (:require [java-time :as t])
  (:require [app.utils.constants :refer [jwt-secret]]))

;; :exp in seconds
(defn encode [user-id]
  (jwt/sign {:userId (str user-id) :exp (.getEpochSecond (t/plus (t/instant) (t/minutes 30)))}
            jwt-secret))

(defn decode [payload]
  (try
     (:userId (jwt/unsign payload jwt-secret))
     (catch Exception e
       "")))
