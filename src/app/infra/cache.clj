(ns app.infra.cache
  (:require [clojure.core.cache :as cache]))

; 30 minutes
(def cache-atom (atom (-> {}
                          (cache/fifo-cache-factory :threshold 16)
                          (cache/ttl-cache-factory :ttl 1800000))))

(defn cache-get [k]
  (cache/lookup @cache-atom k))

(defn cache-has? [k]
  (cache/has? @cache-atom k))

(defn cache-put! [k v]
  (swap! cache-atom assoc k v))

(defn cache-del! [k]
  (swap! cache-atom cache/evict @cache-atom k))
