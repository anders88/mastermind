(ns mastermind.core)

(def colors [:red :green :blue :yellow :orange :purple])



(defn correct-place [fact guess]
  (if (empty? fact) 0
    (+ (if (= (first fact) (first guess)) 1 0) (correct-place (rest fact) (rest guess)))
  )
  )
