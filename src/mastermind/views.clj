(ns mastermind.views
  (:require [noir.response :as response])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.page-helpers :only [html5 include-css]]
        [hiccup.form-helpers]
	[mastermind.core :only [find-solution]])
)

(defpage "/" []
	(html5 
          (form-to [:get "findSolution"]
               (text-field "fact")
               (submit-button "Search")
               )
          
          )

        )

(defn codestr-to-color [colorstr]
  (let [colmap {"r" :red "g" :green "b" :blue "y" :yellow "o" :orange "p" :purple}]
    (last (find colmap colorstr))
    )
  )

(defn convert-string-to-color-code [s]
  (map codestr-to-color (map #(str %) s))
  )

(defn guess-as-string [guess]
  (str (reduce (fn [a b] (str a "+" b)) (:guess guess)) " -> Correct place: " (:place (:feedback guess)) " Correct color: "  (:place (:feedback guess)))
  )
  
                
(defpage [:get "/findSolution"] {:as parameters} 
  (html5
   [:body
    [:h1 (str "The code was " (:fact parameters))]
    [:ul
     (for [part (find-solution (convert-string-to-color-code (:fact parameters)) [])]
       [:li (guess-as-string part)]
       )
     ]
   ]
   )
  )