(defproject cascalog-elephantdb "0.3.0"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-options {:debug "true" :fork "true"}
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.9.0-wip"]
                 [elephantdb/elephantdb-cascading "0.3.0"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.3.0"]]
  :multi-deps {"1.2" [[org.clojure/clojure "1.2.1"]
                      [cascalog "1.9.0-wip"]
                      [elephantdb/elephantdb-cascading "0.3.0"]]
               "1.4" [[org.clojure/clojure "1.4.0-alpha3"]
                      [cascalog "1.9.0-wip"]
                      [elephantdb/elephantdb-cascading "0.3.0"]]})
