(def shared
  '[[cascalog "1.9.0-wip10"]
    [yieldbot/elephantdb-cascading "0.3.2"]])

(defproject yieldbot/cascalog-elephantdb "0.3.2"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-options {:debug "true" :fork "true"}
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.4.0"]
                     [hadoop-util "0.2.8"]]
  :dependencies ~(conj shared '[org.clojure/clojure "1.3.0"])
  :multi-deps {"1.2" ~(conj shared '[org.clojure/clojure "1.2.1"])
               "1.4" ~(conj shared '[org.clojure/clojure "1.4.0-alpha3"])})
