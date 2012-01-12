(defproject cascalog-{{name}} "0.1.0"
  :source-path "src/clj"
  :description "TODO: What do I do?"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.8.5"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]
                     [midje-cascalog "0.3.1"]] ;; for testing: http://goo.gl/EXyEV
  ;; multi-deps allows you to test against multiple releases of
  ;; clojure. run "lein multi deps" and "lein multi midje" to run the
  ;; full test suite, or "lein multi midje --with 1.2" to target a
  ;; specific bundle.
  :multi-deps {"1.2" [[org.clojure/clojure "1.2.1"]
                      [cascalog "1.8.5"]]
               "1.4" [[org.clojure/clojure "1.4.0-alpha3"]
                      [cascalog "1.8.5"]]})
