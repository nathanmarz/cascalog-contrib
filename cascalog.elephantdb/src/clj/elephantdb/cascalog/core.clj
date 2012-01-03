(ns elephantdb.cascalog.core
  (:use cascalog.api
        [elephantdb.cascalog impl conf])
  (:require [cascalog.workflow :as w]
            [elephantdb.common.config :as c])
  (:import [elephantdb Utils]
           [elephantdb.cascalog ClojureIndexer]
           [elephantdb.cascading ElephantDBTap]
           [org.apache.hadoop.conf Configuration]))

(defn mk-updater
  "Accepts a var OR a vector of a var and arguments. If this occurs,
  the var will be applied to the other arguments before returning a
  function. For example, given:

  (defn make-adder [x]
      (fn [y] (+ x y)))

  Either of these are valid:

  (mk-updater [#'make-adder 1])
  (mk-updater #'inc)"
  [updater-spec]
  (ClojureIndexer. (w/fn-spec updater-spec)))

(defn elephant-tap
  "Returns a tap that can be used to source and sink key-value pairs
  to ElephantDB."
  [root & {:keys [args domain-spec]}]
  (let [args    (convert-clj-args (merge default-args args))
        spec    (when domain-spec
                  (c/convert-clj-domain-spec domain-spec))
        edb-tap (ElephantDBTap. root spec args)]
    (cascalog-tap edb-tap
                  (fn [pairs]
                    [edb-tap (elephant<- edb-tap pairs)]))))

(defn reshard!
  [source-dir target-dir numshards]
  (let [fs (Utils/getFS source-dir (Configuration.))
        spec (c/read-domain-spec fs source-dir)
        new-spec (assoc spec :num-shards numshards)]
    (?- (elephant-tap target-dir :domain-spec new-spec)
        (elephant-tap source-dir))))
