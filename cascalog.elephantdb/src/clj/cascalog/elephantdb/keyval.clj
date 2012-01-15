(ns cascalog.elephantdb.keyval
  (:require [cascalog.workflow :as w]
            [cascalog.elephantdb.core :as core]
            [elephantdb.common.config :as c])
  (:import [elephantdb.index Indexer]
           [cascalog.elephantdb KeyValIndexer]))

(defmulti kv-indexer
  "Accepts a var OR a vector of a var and arguments. If this occurs,
  the var will be applied to the other arguments before returning a
  function. For example, given:

  (defn make-adder [x]
      (fn [y] (+ x y)))

  Either of these are valid:

  (kv-indexer [#'make-adder 1])
  (kv-indexer #'inc)

  The supplied function will receive

     [KeyValPersistence, key, value]

  as arguments."
  type)

(defmethod kv-indexer nil
  [_] nil)

(defmethod kv-indexer Indexer
  [indexer] indexer)

(defmethod kv-indexer :default
  [spec]
  (KeyValIndexer. (w/fn-spec spec)))

(defn convert-keyval-args [conf-map]
  (convert-args
   (merge-with #(or %1 %2)
               {:gateway (KeyValGateway.)}
               conf-map)))

(defn keyval-tap
  "Returns a tap that can be used to source and sink key-value pairs
  to ElephantDB."
  [root-path & {:keys [indexer] :as args}]
  (let [args (merge-with #(or %1 %2)
                         {:gateway (KeyValGateway.)}
                         args
                         {:indexer (kv-indexer indexer)})]
    (apply core/elephant-tap
           root-path
           (apply concat args))))
