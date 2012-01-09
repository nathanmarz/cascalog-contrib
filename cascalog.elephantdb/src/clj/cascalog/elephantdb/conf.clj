(ns cascalog.elephantdb.conf
  (:import [elephantdb.cascading ElephantBaseTap$Args]
           [java.util ArrayList HashMap]))

(defn mk-hash-map [m]
  (HashMap. (or m {})))

(defn- mk-list [l]
  (when l (ArrayList. l)))

(def default-args
  {:persistence-options {}
   :indexer      nil
   :version      nil
   :tmp-dirs     nil
   :timeout-ms   nil})

(defn convert-clj-args
  [{:keys [persistence-options tmp-dirs indexer timeout-ms version]}]
  (let [ret (ElephantBaseTap$Args.)]
    (set! (.persistenceOptions ret) (mk-hash-map persistence-options))
    (set! (.tmpDirs ret)            (mk-list tmp-dirs))
    (when indexer
      (set! (.indexer ret) indexer))
    (when timeout-ms
      (set! (.timeoutMs ret) timeout-ms))
    (set! (.version ret) version)
    ret))


