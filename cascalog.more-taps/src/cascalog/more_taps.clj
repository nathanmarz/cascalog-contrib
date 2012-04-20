(ns cascalog.more-taps
  (:use cascalog.api)
  (:require [cascalog.tap :as tap]
            [cascalog.vars :as v]
            [cascalog.workflow :as w])
  (:import [cascading.scheme TextDelimited]
           [cascading.tuple Fields]))

(defn delimited
  [field-seq delim & {:keys [classes skip-header?]}]
  (let [skip-header? (boolean skip-header?)
        field-seq    (w/fields field-seq)
        field-seq    (if (and classes (not (.isDefined field-seq)))
                       (w/fields (v/gen-nullable-vars (count classes)))
                       field-seq)]
    (if classes
      (TextDelimited. field-seq skip-header? delim (into-array classes))
      (TextDelimited. field-seq skip-header? delim))))

(defn hfs-delimited
  "Creates a tap on HDFS using Cascading's TextDelimited
   scheme. Different filesystems can be selected by using different
   prefixes for `path`.

  Supports keyword option for `:outfields`, `:classes` and
  `:skip-header?`. See `cascalog.tap/hfs-tap` for more keyword
  arguments.

   See http://www.cascading.org/javadoc/cascading/tap/Hfs.html and
   http://www.cascading.org/javadoc/cascading/scheme/TextDelimited.html"
  [path & opts]
  (let [{:keys [outfields delimiter]} (apply array-map opts)
        scheme (apply delimited
                      (or outfields Fields/ALL)
                      (or delimiter "\t")
                      opts)]
    (apply tap/hfs-tap scheme path opts)))

(defn lfs-delimited
  "Creates a tap on the local filesystem using Cascading's
   TextDelimited scheme. Different filesystems can be selected by
   using different prefixes for `path`.

  Supports keyword option for `:outfields`, `:classes` and
  `:skip-header?`. See `cascalog.tap/hfs-tap` for more keyword
  arguments.

   See http://www.cascading.org/javadoc/cascading/tap/Hfs.html and
   http://www.cascading.org/javadoc/cascading/scheme/TextDelimited.html"
  [path & opts]
  (let [{:keys [outfields delimiter]} (apply array-map opts)
        scheme (apply delimited
                      (or outfields Fields/ALL)
                      (or delimiter "\t")
                      opts)]
    (apply tap/lfs-tap scheme path opts)))
