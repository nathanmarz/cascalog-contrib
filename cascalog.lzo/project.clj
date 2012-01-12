(def shared
  '[[cascalog "1.9.0-wip"]
    [elephant-bird "2.1.3-SNAPSHOT"]
    [hadoop-lzo "0.4.14"]])

(defproject cascalog-lzo "0.1.0-wip"
  :description "Lzo compression taps for Cascalog."
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]
                     [midje-cascalog "0.3.1"]]
  :dependencies ~(conj shared '[org.clojure/clojure "1.3.0"])
  :multi-deps {"1.2" ~(conj shared '[org.clojure/clojure "1.2.1"])
               "1.4" ~(conj shared '[org.clojure/clojure "1.4.0-alpha3"])})
