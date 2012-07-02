(def shared
  '[[cascalog "1.9.0"]
    [jackknife "0.1.2"]
    [hadoop-util "0.2.8"]])

(defproject cascalog-checkpoint "0.2.0"
  :description "Workflow checkpoints for the masses."
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]
                     [midje-cascalog "0.4.0"]]
  :dependencies ~(conj shared '[org.clojure/clojure "1.4.0"])
  :multi-deps {"1.2" ~(conj shared '[org.clojure/clojure "1.2.1"])
               "1.3" ~(conj shared '[org.clojure/clojure "1.3.0"])})


