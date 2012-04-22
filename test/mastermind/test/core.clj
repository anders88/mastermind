(ns mastermind.test.core
  (:use [mastermind.core])
  (:use [clojure.test]))

(deftest test-correct-place
  (is (= 4 (correct-place [:red :red :red :red] [:red :red :red :red])))
  (is (= 0 (correct-place [:red :red :red :red] [:blue :blue :blue :blue])))
  (is (= 1 (correct-place [:red :red :red :red] [:blue :red :blue :blue])))
  )

(deftest test-correct-color 
  (is (= 4 (correct-color [:red :red :red :red] [:red :red :red :red])))
  (is (= 0 (correct-color [:red :red :red :red] [:blue :blue :blue :blue])))
  (is (= 2 (correct-color [:red :green :blue :blue] [:green :red :yellow :yellow])))
  )

(deftest test-guess-feedback 
  (is (= {:place 4 :color 0} (guess-feedback [:red :red :red :red] [:red :red :red :red])))
  (is (= {:place 0 :color 0} (guess-feedback [:red :red :red :red] [:blue :blue :blue :blue])))
  (is (= {:place 0 :color 2} (guess-feedback [:red :green :blue :blue] [:green :red :yellow :yellow])))
  (is (= {:place 2 :color 2} (guess-feedback [:red :green :blue :blue] [:green :red :blue :blue])))
  )

(deftest test-possible-solution
  (is (possible-solution? [] [:red :red :red :red]))
  (is (not (possible-solution? [{:guess [:red :red :red :red]
                                 :feedback {:place 0 :color 0} }]
                               [:red :green :green :green])))
  (is (possible-solution? [{:guess [:blue :red :blue :blue]
                            :feedback {:place 0 :color 1} }]
                          [:red :green :green :green]))
  )

(deftest test-all-possible
  (is (= (* 6 6 6 6) (count all-possible)))
  (is (contains? (set all-possible) [:red :blue :green :green]))
  )

(println (str "Fact  [:red :green :orange :blue] \n"
              (reduce (fn [a b] (str a "\n" b))
                      (find-solution [:red :green :orange :blue] []))))