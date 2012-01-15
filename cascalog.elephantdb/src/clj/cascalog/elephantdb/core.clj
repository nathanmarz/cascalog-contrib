(ns cascalog.elephantdb.core
  (:use cascalog.api
        [cascalog.elephantdb impl conf])
  (:require [cascalog.workflow :as w]
            [elephantdb.common.config :as c])
  (:import [elephantdb.index Indexer]
           [cascalog.elephantdb ClojureIndexer]
           [elephantdb Utils]
           [elephantdb.cascading ElephantDBTap]
           [org.apache.hadoop.conf Configuration]))

(defmulti mk-indexer
  "Accepts a var OR a vector of a var and arguments. If this occurs,
  the var will be applied to the other arguments before returning a
  function. For example, given:

  (defn make-adder [x]
      (fn [y] (+ x y)))

  Either of these are valid:

  (mk-indexer [#'make-adder 1])
  (mk-indexer #'inc)

  The supplied function will receive

     [KeyValPersistence, Document]

  as arguments."
  type)

(defmethod mk-indexer nil
  [_] nil)

(defmethod mk-indexer Indexer
  [indexer] indexer)

(defmethod mk-indexer :default
  [spec]
  (ClojureIndexer. (w/fn-spec spec)))

(defn elephant-tap
  [root-path & {:keys [spec indexer] :as args}]
  (let [args (->  args
                  (merge {:indexer (mk-indexer indexer)})
                  (convert-keyval-args))
        spec (when spec
               (c/convert-clj-domain-spec spec))
        tap  (ElephantDBTap. root-path spec args)]
    (cascalog-tap tap
                  (fn [pairs]
                    [tap (elephant<- tap pairs)]))))

(defn reshard!
  [source-dir target-dir numshards]
  (let [fs (Utils/getFS source-dir (Configuration.))
        spec (c/read-domain-spec fs source-dir)
        new-spec (assoc spec :num-shards numshards)]
    (?- (elephant-tap target-dir :domain-spec new-spec)
        (elephant-tap source-dir))))
