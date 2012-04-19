(ns mastermind.core)

(def colors [:red :green :blue :yellow :orange :purple])



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
  

