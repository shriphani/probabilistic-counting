(ns probabilistic-counting.log-log
  "The LogLog algorithnm")

(defn rho
  "Number of leading zeros in the bit-representation
   Args:
    y : the number itself
    size : optional : the number of bytes used to represent
           the number. Default: 4 bytes/32 bits"
  ([y] (rho y 32))
  
  ([y size]
     (int
      (- size
         (Math/ceil
          (/ (Math/log (+ y 1))
             (Math/log 2)))))))

(defn log-log
  [M num-buckets]
  (let [buckets (map short (range num-buckets))]
    buckets))
