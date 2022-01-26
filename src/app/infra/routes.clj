(ns app.infra.routes
  (:require [app.infra.interceptors :refer [common-interceptors, auth-interceptor]]
            [app.handlers.about :as about]))

(def routes #{["/" :get (conj common-interceptors `about/home-page)]
              ["/about" :get (conj common-interceptors `about/about-page)]
              ["/json" :get (conj common-interceptors auth-interceptor `about/json)]})
