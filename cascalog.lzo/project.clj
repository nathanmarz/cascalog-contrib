(defproject cascalog-lzo "0.1.0-wip"
  :description "Lzo compression taps for Cascalog."
  :dependencies [[cascalog "1.9.0-wip"]
                 [elephant-bird "2.1.3-SNAPSHOT"]
                 [hadoop-lzo "0.4.14"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.4.0"]])


