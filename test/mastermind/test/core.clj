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
  (is (= 2 (correct-color [:red :green :blue :blue] [:green :blue :yellow :yellow])))
  )
