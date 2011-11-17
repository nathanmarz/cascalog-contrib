(ns cascalog.math.stats
  (:use cascalog.api)
  (:require [cascalog.ops :as c]))

(def variance
  "Predicate macro that calculates the variance of the supplied input
   var."
  (<- [!val :> !var]
      (* !val !val :> !squared)
      (c/sum !squared :> !square-sum)
      (c/count !count)
      (c/avg !val :> !mean)
      (* !mean !mean :> !mean-squared)
      (div !square-sum !count :> !i)
      (- !i !mean-squared :> !var)))
