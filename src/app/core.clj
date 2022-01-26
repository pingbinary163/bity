(ns app.core
  (:require [io.pedestal.http :as http]
            [clojure.tools.logging :as log]
            [mount.core :as mount]
            [app.infra.config :refer [config-state]]
            [app.infra.routes :refer [routes]])
  (:gen-class))

;; main
(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (log/warn "Restarting...")
  (mount/start)
  (.addShutdownHook
    (Runtime/getRuntime)
    (Thread. mount/stop))
  (http/start (http/create-server (-> {:env                   :prod
                                       ::http/type            :jetty
                                       ::http/host            "0.0.0.0"
                                       ::http/routes          routes
                                       ::http/allowed-origins ["*"]
                                       ::http/join?           false
                                       ::http/port            (get-in config-state [:http-server :port])}
                                      http/default-interceptors
                                      (update ::http/interceptors into [http/json-body])))))