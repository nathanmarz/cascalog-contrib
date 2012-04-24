(defproject cascalog-elasticsearch "0.1.0"
  :description "Elasticsearch integration for Cascading and Cascalog."
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :jvm-opts ["-Xmx768m" "-server"]
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.8.5"]]
  :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"]
                     [org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]
                     [midje-cascalog "0.3.1"]]
  :multi-deps {"1.2" [[org.clojure/clojure "1.2.1"]
                      [cascalog "1.8.5"]]
               "1.4" [[org.clojure/clojure "1.4.0-alpha2"]
                      [cascalog "1.8.5"]]})

