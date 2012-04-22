(ns mastermind.views
  (:require [noir.response :as response])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.page-helpers :only [html5 include-css]]
        [hiccup.form-helpers]
	[mastermind.core :only [find-solution]])
  )

(defn col-selector [with-id]
  [:select {:name with-id}
   (select-options [["red" "r"] ["green" "g"] ["blue" "b"] ["yellow" "y"] ["orange" "o"] ["purple" "p"]])]
  )
                    

(defpage "/" []
	(html5 
          (form-to [:get "findSolution"]
                   (col-selector "p1")
                   (col-selector "p2")
                   (col-selector "p3")
                   (col-selector "p4")
               (submit-button "Find solution")
               )
          
          )

        )

(defn codestr-to-color [colorstr]
  (let [colmap {"r" :red "g" :green "b" :blue "y" :yellow "o" :orange "p" :purple}]
    (last (find colmap colorstr))
    )
  )
(defn guess-as-string [guess]
  (str (reduce (fn [a b] (str a "+" b)) (:guess guess)) " -> Correct place: " (:place (:feedback guess)) " Correct color: "  (:place (:feedback guess)))
  )

(defpage [:get "/findSolution"] {:as parameters}
  (let [fact (map codestr-to-color [(:p1 parameters) (:p2 parameters) (:p3 parameters) (:p4 parameters)])]
  (html5
   [:body
    [:h1 (str "The code was: " (reduce (fn [a b] (str a "-" b)) fact))]
    [:ul
     (for [part (find-solution fact [])]
       [:li (guess-as-string part)]
       )
     ]
    [:a {:href "/"} "Try again"]
   ]
   )
  )
  )