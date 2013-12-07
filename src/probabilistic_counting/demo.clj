(ns probabilistic-counting.demo
  "A demo of the implemented algorithms"
  (:use [probabilistic-counting.log-log]))

(defn demo-log-log []
  (map
   (fn [_] (log-log (map #(mod % 12) (range 100000)) 2))
   (range 10)))
