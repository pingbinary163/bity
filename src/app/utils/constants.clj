(ns app.utils.constants
  (:require [buddy.core.hash :as hash]))

(def jwt-secret (hash/sha256 "1234567890123456"))
