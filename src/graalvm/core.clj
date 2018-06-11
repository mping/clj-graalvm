(ns graalvm.core
  (:use org.httpkit.server)
  (:require [org.httpkit.server :as http]
            [ring.middleware.defaults
              :refer [wrap-defaults
                      api-defaults]]
            [bidi.ring :refer [make-handler]])
  (:import [org.httpkit.server AsyncChannel])
  (:gen-class))

;; We want to be sure none of our calls relies
;; on reflection. Graal does not support them.
(set! *warn-on-reflection* 1)

;; This is where we store our data.
(def ^String tmpdir
  (System/getProperty "java.io.tmpdir"))

;; That's how we find a file given a key.
;; Keys must match the given pattern.
(defn file [^String key]
  {:pre [(re-matches #"^[A-Za-z-]+$" key)]}
  (java.io.File. tmpdir key))

;; Here we handle GET requests. We just
;; read from a file.
(defn get-handler
  [{:keys [params]}]
  {:body (slurp (file (params :key)))})

;; This is our PUT request handler. Given
;; a key and a value we write to a file.
(defn put-handler
  [{:keys [params]}]
  (let [val (params :val)]
    (spit (file (params :key)) val)
    {:body val, :status 201}))

;; Here's the routing tree of our application.
;; We pick the handler depending on the HTTP
;; verb. On top of that we add an extra middle-
;; ware to parse data sent in requests.
(def handler
  (-> ["/dict/" {[:key] {:get get-handler
                         :put put-handler}}]
      (make-handler)
      (wrap-defaults api-defaults)))

;; Finally, we've got all we need to expose
;; our handler over HTTP.
(defn -main []
  (http/run-server handler
                   {:port 8080})
  (println "🔥 http://localhost:8080"))