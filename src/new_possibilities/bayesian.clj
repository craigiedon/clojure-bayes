(ns new-possibilities.bayesian)

(defn variable [name values] {:name name :values values})

(defn graph [byn] (:graph byn))
(defn cpds [byn] (:cpds byn))

(defn bayesian-network
  ([graph cpds] {:graph graph :cpds cpds}))

;Factor operations
(defn factor [scope table] {:scope scope :table table})
;Multiply
;Normalize out?
;I can't remember how the variable elimination procedure works... Look up your java implementation or maybe the Russel / Norvig book
;Q: What is the *Interface* of a factor? Can you do other kinds of graph other than a bayes net? How about a factor graph or a markov network?


;Implement D-separation and other fun algorithms?

(def attention (variable :a [0 1]))
(def catcher (variable :c [0 1]))
(def bark (variable :b [0 1]))
(def hind-legs (variable :h [0 1]))
(def good-result (variable :g [0 1]))

;Here, we have modelled the graph as an "adjacency list", with DAGS, this is a "predecessor list"
(def dog-catcher-graph {attention #{bark hind-legs}
                      catcher #{}
                      bark #{}
                      hind-legs #{}
                      good-result #{attention catcher}})

(def dog-catcher-cpds {:a (factor [hind-legs bark attention] {[0 0 0] 0.9 [0 0 1] 0.1
                                                      [0 1 0] 0.3 [0 1 1] 0.7
                                                      [1 0 0] 0.3 [1 0 1] 0.7
                                                      [1 1 0] 0.1 [1 1 1] 0.9})
                       :c (factor [catcher] {[0] 0.8 [1] 0.2})
                       :b (factor [bark] {[0] 0.5 [1] 0.5})
                       :h (factor [hind-legs] {[0] 0.5 [1] 0.5})
                       :g (factor [catcher attention good-result] {[0 0 0] 0.6 [0 0 1] 0.4
                                                           [0 1 0] 0.8 [0 1 1] 0.2
                                                           [1 0 0] 0.7 [1 0 1] 0.3
                                                           [1 1 0] 1.0 [1 1 1] 0.0})
                       })

(def dog-catcher-net (bayesian-network dog-catcher-graph dog-catcher-cpds))

(defn probability-of [query evidence bn]

