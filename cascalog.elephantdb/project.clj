(def shared
  '[[cascalog "1.8.5"]
    [hadoop-util "0.2.7"]
    [elephantdb/elephantdb-cascading "0.2.0"]])

(defproject cascalog-elephantdb "0.2.0"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-options {:debug "true" :fork "true"}
  :dependencies ~(conj shared '[org.clojure/clojure "1.3.0"])
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.3.0"]]
  :multi-deps {"1.2" ~(conj shared '[org.clojure/clojure "1.2.1"])
               "1.4" ~(conj shared '[org.clojure/clojure "1.4.0-alpha3"])})
