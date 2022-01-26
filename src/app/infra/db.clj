(ns app.infra.db
  (:require [app.infra.config :refer [config-state]]
            [mount.core :as mount]
            [hikari-cp.core :as hikari]
            [next.jdbc.connection :as connection])
  (:import (com.zaxxer.hikari HikariDataSource)))

(defn db-spec []
  (let [params (get-in config-state [:database])]
    (merge {:auto-commit              true
            :connection-timeout       30000
            :validation-timeout       5000
            :idle-timeout             600000
            :max-lifetime             1800000
            :minimum-idle             10
            :maximum-pool-size        10
            :register-mbeans          false
            :re-write-batched-inserts true
            :connectionTestQuery      "SELECT 1"
            :dbtype                   (:adapter params)
            :dbname                   (:database-name params)
            :user                     (:username params)
            :password                 (:password params)
            :host                     (:server-name params)
            :port                     (:port-number params)}
           params)))

(mount/defstate db-state
                :start (connection/->pool HikariDataSource (db-spec))
                :stop (hikari/close-datasource db-state))
