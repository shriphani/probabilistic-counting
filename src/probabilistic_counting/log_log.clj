(ns probabilistic-counting.log-log
  "The LogLog algorithnm"
  (:use [incanter.core]))

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
  [stream num-buckets-exponent]
  (let [buckets  (make-array Integer/TYPE (Math/pow 2 num-buckets-exponent))
        j-mask   (bit-shift-left
                  (- (int (Math/pow 2 num-buckets-exponent)) 1)
                  (- 32 num-buckets-exponent))
        val-mask (bit-xor j-mask 0xffff)
        m        (Math/pow 2 num-buckets-exponent)
        alpha    (Math/pow
                  (* (gamma (- (/ 1 m)))
                     (/ (- 1 (Math/pow 2 (/ 1 m)))
                        (Math/log 2)))
                  (- m))]
    (do
      (doseq [x stream]
        (let [index (bit-and x j-mask)
              val   (bit-and x val-mask)]
          (println :x (Integer/toBinaryString x)
                   :index (Integer/toBinaryString index)
                   :val (Integer/toBinaryString (rho val)))
          (aset buckets index (max (aget buckets index) (rho val)))))
      (*
       0.79402
       m
       (Math/pow
        2 (/ (apply + buckets)
             (count buckets)))))))
