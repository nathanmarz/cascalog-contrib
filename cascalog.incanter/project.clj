(def shared
  '[[cascalog "1.8.5"]
    [incanter/incanter-charts "1.3.0-SNAPSHOT"]])

(defproject cascalog-incanter "0.1.1-SNAPSHOT"
  :description "Incanter integration for Cascalog."
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]
                     [midje-cascalog "0.3.1"]]
  :dependencies ~(conj shared '[org.clojure/clojure "1.3.0"])
  :multi-deps {"1.2" ~(conj shared '[org.clojure/clojure "1.2.1"])
               "1.4" ~(conj shared '[org.clojure/clojure "1.4.0-alpha3"])})
