(ns achilles.core
  (:require [clojure.string :as str]))


(defn obj-array [cls obj-seq]
  (let [n (count obj-seq)
        a (make-array cls n)]
    (loop [i 0]
      (if (== i n)
        a
        (do
          (aset a i (nth obj-seq i))
          (recur (inc i)))))))


(defn get-trial-constructor [trial-name]
  (let [cls (Class/forName (str "achilles.trials." trial-name))
        array-one-long-arg (obj-array Class [Long/TYPE])]
    (. cls (getConstructor array-one-long-arg))))


(def prog-name "lein run")


;; While it might be nice to implement code that lets us show all
;; available classes in the achilles.trials package dynamically at run
;; time, I tried to implement some of the ideas shown in this
;; StackOverflow page, but could not get it to work:

;; http://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection

;; Let the user provide the full class name if they wish on the
;; command line, but if we show them a list of classes available, only
;; show the ones included here.  They can look at the source code if
;; they want to know all of them.

(def trial-abbrevs
  [ 
   [ "IncPrimitiveInt" "ipi" ]
   [ "IncPrimitiveLong" "ipl" ]
   [ "IncPrimitiveDouble" "ipd" ]
   [ "IncNewBoxedDouble" "inbd" ]
   [ "IncNewBoxedDouble_PrngUpdate2PrimitiveInt" "inbd-pu2pi" ]
   [ "IncNewBoxedDouble_PrngUpdate4PrimitiveInt" "inbd-pu4pi" ]
   [ "IncNewBoxedDouble_PrngUpdate8PrimitiveIn" "inbd-pu8pi" ]
   [ "IncNewBoxedDouble_PrngUpdate16PrimitiveInt" "inbd-pu16pi" ]
  ])


(defn make-abbrev-map [abbrevs]
  (into {} (mapcat (fn [[full-name & abbrevs]]
                     (map (fn [a] [a full-name])
                          abbrevs))
                   abbrevs)))


(defn usage [prog-name exit-code]
  (printf
"usage: %s type job-size num-threads
    type is a name for the kind of test to run.  Names and abbreviations below.
    all other arguments must be integers >= 1
    job-size is the total number of steps that will be performed by all threads
    num-threads must be >= 1, and is the number of threads to run in parallel.

    Type names and abbreviations are:
%s"
    prog-name
    (->> trial-abbrevs
         (map #(format "%-20s %s\n" (first %) (str/join " " (rest %))))
         (str/join "")))
  (flush)
  (System/exit exit-code))


(defn -main [& args]
  (when-not (= 3 (count args))
    (printf "Expected 3 args but found %d" (count args))
    (usage prog-name 1))
  (let [trial-abbrev-map (make-abbrev-map trial-abbrevs)
        [trial-name job-size-str num-threads-str] args
        job-size (bigint job-size-str)
        num-threads (bigint num-threads-str)]
    (when-not (>= job-size 1)
      (printf "job-size must be at least 1 (found %d)\n" job-size)
      (usage prog-name 1))
    (when-not (>= num-threads 1)
      (printf "num-threads must be at least 1 (found %d)\n" num-threads)
      (usage prog-name 1))
    (let [tname (get trial-abbrev-map trial-name trial-name)
          constr (try
                   (get-trial-constructor tname)
                   (catch ClassNotFoundException e
                     (printf "No class '%s' found.\n\n" tname)
                     (usage prog-name 1)))
          ;;job-size-per-thread (quot job-size num-threads)
          job-size-per-thread job-size
          ]
      (achilles.ParallelTest/doTest tname constr num-threads
                                    job-size-per-thread))))
