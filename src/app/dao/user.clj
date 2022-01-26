(ns app.dao.user
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [app.infra.db :refer [db-state]]))

;;
(defn get-by-user-name [user-name]
  (with-open [conn (jdbc/get-connection db-state)]
    (jdbc/execute-one! conn
                       ["SELECT user_id, user_name, user_title, password FROM t_user WHERE user_name=? LIMIT 1" user-name]
                       {:builder-fn rs/as-unqualified-kebab-maps})))
