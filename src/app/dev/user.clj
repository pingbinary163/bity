(ns app.dev.user
  (:require [io.pedestal.http :as http]
            [mount.core :as mount]
            [app.infra.routes :refer [routes]]
            [app.infra.config :refer [config-state]]
            [clojure.tools.namespace.repl :refer [refresh]]))

;; dev
(defonce dev-server (atom nil))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (mount/start)
  (.addShutdownHook
    (Runtime/getRuntime)
    (Thread. mount/stop))
  (reset! dev-server
          (-> {:env                   :dev
               ::http/routes          routes
               ::http/allowed-origins ["*"]
               ::http/type            :jetty
               ::http/port            (get-in config-state [:http-server :port])
               ::http/join?           false}
              http/create-server
              http/start))
  (println "Enter [reset-dev] to reload"))

(defn stop-dev []
  (if-not (= @dev-server nil)
    (do
      (http/stop @dev-server)
      (reset! dev-server nil))))

(defn reset-dev []
  (stop-dev)
  (refresh :after 'app.dev.user/run-dev))