(ns probabilistic-counting.demo
  "A demo of the implemented algorithms"
  (:use [probabilistic-counting.log-log]))

(defn demo-log-log []
  (map
   (fn [_] (/ 100000
             (log-log (map rand (range 100000)) 10)))
   (range 10)))
