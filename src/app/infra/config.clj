(ns app.infra.config
  (:require [clojure.java.io :as io]
            [mount.core :as mount]
            [aero.core :as aero]))

(defn- read-config []
  ; java -Dconfig=./config.edn -Dlogback.configurationFile=./logback.xml -jar xxx.jar
  (let [custom-config (System/getProperty "config")]
    (aero/read-config
      (if (clojure.string/blank? custom-config) (io/resource "config.edn") custom-config))))

(mount/defstate config-state
                :start (read-config))
