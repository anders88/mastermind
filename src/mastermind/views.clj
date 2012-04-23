(ns mastermind.views
  (:require [noir.response :as response])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.page :only [html5 include-css]]
        [hiccup.form]
	[mastermind.core :only [find-solution]])
  )

(defn col-selector [with-id]
  [:select {:name with-id}
   (select-options [["red" "r"] ["green" "g"] ["blue" "b"] ["yellow" "y"] ["orange" "o"] ["purple" "p"]])]
  )

(defpage "/" []
	(html5 
    [:head
      (include-css "screen.css")]
    [:body.form
          (form-to [:get "findSolution"]
                   (col-selector "p1")
                   (col-selector "p2")
                   (col-selector "p3")
                   (col-selector "p4")
               (submit-button "Find solution")
               )
    ]))

(defn codestr-to-color [colorstr]
  (let [colmap {"r" :red "g" :green "b" :blue "y" :yellow "o" :orange "p" :purple}]
    (last (find colmap colorstr))
    )
  )

(defpartial color-div [color]
  [:div {:class (str (name color) " color")}])

(defpartial row-of-colors [row]
  (map color-div row))

(defpartial guess-as-string [guess]
  [:div.guess
    (row-of-colors (:guess guess))
    [:div.correct (str "Correct place: " (:place (:feedback guess)))]
    [:div.correct (str "Correct color: " (:color (:feedback guess)))]])

(defpage [:get "/findSolution"] {:as parameters}
  (let [fact (map codestr-to-color [(:p1 parameters) (:p2 parameters) (:p3 parameters) (:p4 parameters)])]
  (html5
    [:head
      (include-css "screen.css")]
   [:body
    [:h1 (str "The code was: " (row-of-colors fact))]
     (for [part (find-solution fact [])]
       (guess-as-string part)
     )
    [:p.again [:a {:href "/"} "Try again"]]])))

