(ns app.handlers.about
  (:require [io.pedestal.http.route :as route]
            [ring.util.response :as ring-resp]
            [app.infra.interceptors :refer [common-interceptors]]))

(defn about-page
  [req]
  (ring-resp/response (format "Clojure %s - served from %s !"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn home-page
  [req]
  (ring-resp/response "Hello World"))

(defn json
  [req]
  {:status 200
   :body {:type "Office" :address {:street "Ocean Drive" :zip 90210 :city "Beverly Hills" }}})