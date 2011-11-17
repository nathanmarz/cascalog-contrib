(ns cascalog.incanter
  (:use cascalog.api)
  (:require [incanter.charts :as charts]
            [incanter.core :as incanter]))

(defn bar?-
  [subquery & opts]
  (let [data (incanter/matrix (first (??- subquery)))]
    (incanter/view (apply charts/bar-chart*
                          (map first data)
                          (map second data)
                          opts))))
