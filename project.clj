(defproject bity "0.0.1"
  :description "Business Intelligence application written in Clojure, much inspired by Metabase."
  :url "http://pingbinary.com/open-source/bity"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}
  :repositories {"central" {:url "https://mirrors.huaweicloud.com/repository/maven/"}
                 "clojars" {:url "https://mirrors.tuna.tsinghua.edu.cn/clojars/"}
                 "project" {:url "file:repo" :username "" :password ""}}
  :min-lein-version "2.9.6"
  :dependencies [; core
                 [org.clojure/clojure "1.10.3"]             ; clojure
                 [org.clojure/core.async "1.5.648"]         ; async
                 [org.clojure/core.cache "1.0.225"]         ; cache
                 [org.clojure/data.json "2.4.0"]            ; json
                 [org.clojure/core.match "1.0.0"]           ; pattern match
                 [org.clojure/tools.logging "1.2.4"]        ; log
                 [org.clojure/spec.alpha "0.3.218"]         ; spec
                 ; http
                 [io.pedestal/pedestal.service "0.5.10"]    ; pedestal
                 [io.pedestal/pedestal.jetty "0.5.10"]      ; pedestal http adapter
                 [clj-http "3.12.3"]                        ; http client wrapper
                 ; app core
                 [aero "1.1.6"]                             ; config
                 [ch.qos.logback/logback-classic "1.2.10"]  ; log
                 [mount "0.1.16"]                           ; state management
                 [com.github.seancorfield/next.jdbc "1.2.761"] ; jdbc
                 [com.github.seancorfield/honeysql "2.2.858"]  ; orm
                 [hikari-cp "2.13.0"]                       ; db connection pool
                 [mysql/mysql-connector-java "8.0.28"]      ; adapter-mysql
                 [org.postgresql/postgresql "42.3.1"]       ; adapter-postgresql
                 [local/oracle "21.3.0"]                    ; adapter-oracle
                 [local/mssql "9.4.1"]                      ; adapter-mssql
                 ; app util
                 [buddy/buddy-core "1.10.413"]              ; encrypt & decrypt
                 [buddy/buddy-sign "3.4.1"]                 ; jwt
                 [clojure.java-time "0.3.3"]                ; java8-time wrapper
                 [jarohen/chime "0.3.3"]]                   ; cron job
  :main ^:skip-aot app.core
  :target-path "target/%s"
  :profiles {:dev     {:source-paths ["dev"]
                       :aliases      {"run-dev" ["trampoline" "run" "-m" "app.dev.user/run-dev"]}
                       :dependencies [[io.pedestal/pedestal.service-tools "0.5.10"]
                                      [org.clojure/tools.namespace "1.2.0"]]}
             :uberjar {:aot [bity.core]
                       :omit-source true
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true" "-Djava.awt.headless=true"]}})
