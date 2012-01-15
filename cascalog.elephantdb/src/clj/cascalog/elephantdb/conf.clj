(ns cascalog.elephantdb.conf
  (:import [elephantdb.cascading ElephantBaseTap$Args]
           [elephantdb.cascading KeyValGateway]
           [java.util ArrayList HashMap]))

(defn- mk-list [l]
  (when l (ArrayList. l)))

(defn convert-args
  [{:keys [incremental tmp-dirs indexer timeout-ms version gateway]
    :or {incremental true}}]
  (let [ret  (ElephantBaseTap$Args.)]
    (set! (.tmpDirs ret) (mk-list tmp-dirs))
    (when gateway
      (set! (.gateway ret) gateway))
    (when indexer
      (set! (.indexer ret) indexer))
    (when timeout-ms
      (set! (.timeoutMs ret) timeout-ms))
    (set! (.version ret) version)
    ret))
