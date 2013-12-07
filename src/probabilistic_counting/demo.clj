(ns probabilistic-counting.demo
  "A demo of the implemented algorithms"
  (:use [probabilistic-counting.log-log]))

(defn demo-log-log []
  (log-log
   (map #(mod % 1000000) (range 10000000)) 10))
