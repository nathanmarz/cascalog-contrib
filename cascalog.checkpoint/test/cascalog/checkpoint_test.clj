(ns cascalog.checkpoint-test
  (:use cascalog.checkpoint))

(def sprint identity)

(defn run-test! []
  (workflow ["/tmp/lalala"]
            aaa ([] (sprint "aaa")
                   (sprint "aaa")
                   (sprint "aaa")
                   (sprint "aaa"))
            bbb ([:deps nil]
                   (sprint "bbb"))
            ccc ([:tmp-dirs ccc-path]
                   (sprint "ccc"))
            ddd ([:deps [bbb ccc] :tmp-dirs ddd-path]
                  (sprint "ddd")
            eee ([:deps :all]
                  (sprint "eee")))))
