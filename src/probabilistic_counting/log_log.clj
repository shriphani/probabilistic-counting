(ns probabilistic-counting.log-log
  "The LogLog algorithnm"
  (:use [incanter.core])
  (:import [org.apache.mahout.math MurmurHash]
           [org.apache.commons.lang3 SerializationUtils]))

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
  [xs k]
  (let [m       (int (Math/pow 2 k))
        buckets (make-array Integer/TYPE m)]
    (do
      (doseq [x xs]
        (let [h   (int (MurmurHash/hash (SerializationUtils/serialize x) 1991))
              idx (bit-and h (- m 1))
              val (max
                   (aget buckets idx)
                   (rho h))]
          (aset buckets idx val)))
      (* (Math/pow 2 (/ (apply + buckets) m))
         m
         0.79402))))
