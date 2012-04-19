(ns mastermind.core
  (:use [clojure.contrib.combinatorics :only (selections)])
  )

(def colors [:red :green :blue :yellow :orange :purple])

(def all-possible (selections colors 4))

(defn correct-place [fact guess]
  (if (empty? fact) 0
    (+ (if (= (first fact) (first guess)) 1 0) (correct-place (rest fact) (rest guess)))
  )
  )

(defn correct-color [fact guess]
  (let [map-fact (frequencies fact) 
        map-guess (frequencies guess)
        union-keys (fn [master slave] (apply dissoc master (keys (apply dissoc master (keys slave)))))
        ]
  (reduce + (vals (merge-with min (union-keys map-fact map-guess) (union-keys map-guess map-fact))))
  )
  )

(defn guess-feedback [fact guess]
  (let [cp (correct-place fact guess) cc (correct-color fact guess)]
    {:place cp :color (- cc cp)}
    )
  )
  
(defn possible-solution? [guess feedbacks]
  (if (empty? feedbacks) true
    (and (= (guess-feedback (:guess (first feedbacks)) guess) (:feedback (first feedbacks))) (possible-solution? guess (rest feedbacks))) 
  )
  )

(defn all-possible-solutions [feedbacks]
  (filter #(possible-solution? % feedbacks) all-possible)
  )

