(ns mastermind.core
  (:use [clojure.contrib.combinatorics :only (selections)]
        ;[clojure.data :only [diff]]
        [clojure.set :only [intersection]]))

(def colors [:red :green :blue :yellow :orange :purple])

(def all-possible (selections colors 4))

(defn correct-place
  "Counts the number of position matches."
  [fact guess]
  (count (filter (partial apply =) (partition 2 (interleave fact guess))))
  ;(count (remove nil? ((diff fact guess) 2)))
  )

(defn correct-color
  "Counts the number of colour matches."
  [fact guess]
  (let [fact (frequencies fact)
        guess (frequencies guess)
        common-keys (intersection (set (keys fact)) (set (keys guess)))
        merged (merge-with min fact guess)
        common (select-keys merged common-keys)]
    (reduce + (vals common))))

(defn guess-feedback [fact guess]
  (let [cp (correct-place fact guess) cc (correct-color fact guess)]
    {:place cp :color (- cc cp)}))
  
(defn possible-solution? [feedbacks guess]
  (every? #(= (guess-feedback (:guess %) guess) (:feedback %)) feedbacks))

(defn all-possible-solutions [feedbacks]
  (filter (partial possible-solution? feedbacks) all-possible))

(defn find-solution [fact feedbacks]
  (if (= fact (:guess (last feedbacks))) feedbacks
      (let [next-try (first (all-possible-solutions feedbacks))]
        (if (nil? next-try) feedbacks
            (recur fact (conj feedbacks
                              {:guess next-try
                               :feedback (guess-feedback fact next-try)}))))))

  
