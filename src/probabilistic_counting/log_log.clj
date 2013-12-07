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

(defn alpha
  [m]
  (if (< 64 m)
    0.79402
    (* 2 (Math/pow (* (gamma (- (/ 1 m)))
                      (/ (- 1 (Math/pow 2 (/ 1 m)))
                         (Math/log 2)))
                   (- m)))))

(defn log-log
  [xs k]
  (let [m       (int (Math/pow 2 k))
        buckets (make-array Integer/TYPE m)]

    (do
      (map #(aset buckets % 0) (range m))
      (doseq [x xs]
        (let [h   (int (MurmurHash/hash (SerializationUtils/serialize x) 1991))
              idx (bit-and h (- m 1))
              val (max
                   (aget buckets idx)
                   (rho h))]
          (aset buckets idx val)))
      (* (Math/pow 2 (/ (apply + buckets) m))
         m
         (alpha m)))))
