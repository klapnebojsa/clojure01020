(ns clojure01020.core)
;String
;Integer
;*ns*                                                 ;*ns* - daje ime namespace-a

(defn average
  "racuna srednju vrednost zbira brojeva"
  [numbers]
  (/ (apply + numbers) (count numbers)))
;(average [1 2 3 4 5 6 7 8 9 10])

(defn ako
  [x]
  (with-local-vars [y true, b 6]
    (if (< x 5) (var-set y true) (var-set y false))
    (if-not (= @y true) (var-set b(inc @b)) (var-set b (dec @b)))              ;@b je ekvivalentno sa (var-get b)
    (try(if-not (= @y true) (var-set b(inc @b)) (var-set b (dec @b)))                     
      (catch Exception e (println "greska:" (.getMessage e))))
    (println x @b)))
;(ako 10)
;(ako 1)

(defn ako1
  [x]
  (let [y (if (< x 5) true false)
        c (if-not (= y true) (inc x) (dec x)) 
        b (try(if-not (= y true) (inc c) (dec c)) 
             (catch Exception e (println "greska:" (.getMessage e))))]
    (println y x b)))
;(ako1 9)
;(ako1 4)

(def person {:name "Micic Nebojsa"
             :city "Sabac"
             :location "Francuska"
             ::location "6/3"})
;(:name person)
;person
;(:clojure0102.core/location person)

#_(
    komentar u 
    vise 
    linija) 

(defn fun [& args]
  (re-seq #"(...)(....)(..)" "12345678901234567890")          ; #   -oznaka za makro
  (re-seq #"(\d+)-(\d+)" "123-45-6-7-89-0-1"))
;(fun)
;(["123-45" "123" "45"] ["6-7" "6" "7"] ["89-0" "89" "0"])

(defn fun1 [& args]
  (read-string "(+ 1 2 3 #_(* 2 3) 8)")             ; #_ - oznaka za komentar - zato izlazi (1 2 3 8)
  (read-string "(+ 1 2 3 #(* 2 3) 8)")              ; #  - oznaka za makro    - zato izlazi (+ 1 2 3 (fn* [] (* 2 3)) 8)
                                                    ;                 (bezimena funkcija) makro      (fn* [] (* 2 3))
   (comment (println "hello")))                     ; comment - komentar                          
;(fun1)

                                                   
(defn quote1 [& args]
  (do
    (println "simbol" (symbol? (quote x)))
    (println "list?" (list? '(+ x x)))             ;  '-quote(citat)
    (list '+ 'c 'v 'p '+ '- '/ 'o)))
;(quote1)
;simbol true
;list? true
;(+ c v p + - / o)
 
(defn do1 [& args]
  (println "do1")
  (let [a (inc (rand-int 6))
        b (inc (rand-int 6))]
    (println (format "You rolled a %s and a %s" a b))
    (println (+ a b)))
  )
;(do1)

(defn do2 [& args]
  (println "do2")
  (let [a (inc (rand-int 6))                            ;rand-int 6   - bir Nasumicni integer u intervalu od 0 do 5
        b (inc (rand-int 6))]
    (do
      (println (format "You rolled a %s and a %s" a b))
      (println (+ a b))))  
  )
;(do2)

(def p "konj")
(defn pisip [& args]
  p)
;(pisip)
;konj

(defn hipotenuza
  [x y]
  (let [x2 (* x x)
        y2 (* y y)]
        (Math/sqrt (+ x2 y2))))
;(hipotenuza 3 4)
;5

(def v [42 "tekst" 15.47 [2 3 4] "tekst2"])             ;Clojure Vector
(defn prva3
  [& args]
  (let [[x y z] v]
    (+ x z)))
(defn neka3
  [& args]
  (let [[x _ _ [y z t]] v]
    (+ x y z t)))
(defn ostatak 
  [& args]
  (let [[x & rest] v]
    rest))
(defn as 
  [& args]
  (let [[x _ y :as ceo] v]
    (println x y "ceo:" v)))
;(first v) (last v) (nth v 3) (v 3) (.get v 3) (+ (first v)(v 2)) (+ (first v)(first(v 3)))
;(prva3)
;(neka3)
;(ostatak)
;(as)

(def m{:a 5
       :b 6
       :c [7 8 9]
       :d {:e 10 :f 11}
       "tekst" 88
       42 false})
(defn polja 
  [& args]
  (let [{t "tekst" c 42 a :a} m]
    (println "vrednost polja text-" t)
    (println "vrednost polja 42-" c)
    (println "vrednost polja a-"  a)
    (if c "istina" "laž")))
;(polja)
;vrednost polja text- 88
;vrednost polja 42- false
;vrednost polja a- 5
;"laž"

(defn dodela [& args]
  (let [{x 3 y 8} [12 0 0 -18 44 6 0 0 7]]         ;sabira cetvrti i deveti clan niza (-18 + 7)
    (+ x y)))
;(dodela)
;-11

(def map-in-vector ["James" {:birthday (java.util.Date. 73 4 6)}])
(defn rodjus [& args]
  (let [[name {bd :birthday}] map-in-vector]
    (str name " was born on " bd)))
;(rodjus)
;"James was born on Sun May 06 00:00:00 CET 1973"

(defn randa [& args]
  (let [{r1 :x r2 :y :as randoms}
        (zipmap [:x :y :z] (repeatedly (partial rand-int 10)))]
    (assoc randoms :sum (+ r1 r2))))
;(randa)
;{:sum 7, :z 4, :y 3, :x 4}

(defn neznam [& args]
  (let [{k :unknown x :a
         :or {k 50}} m]                   ;m je definisan ranije i kod njega je a=5. k se puni sa 50 smo ako je k nil
    (+ k x)))
;(neznam)
;55

(defn neznam2 [& args]
  (let [{k :unkown x :b} m                ;ako umedto unkown stavimo bilo koju poznau vrednost nece se izvrsiti vrednost 50
        k (or k 50)]                      ;k se puni sa 50 samo ako je vrednos do tog momenta nil
    (+ k x)))
;(neznam2)
;56

(def chas {:name "Chas" :age 31 :location "Massachusetts"})
(defn ispis2 [& args]
  (let [{name :name age :age location :location} chas]
    (format "%s is %s years old and lives in %s." name age location)))
;(ispis2)
;"Chas is 31 years old and lives in Massachusetts."
(defn ispis3 [& args]
  (let [{:keys [name age location]} chas]
    (format "%s is %s years old and lives in %s." name age location)))
;(ispis3)
;"Chas is 31 years old and lives in Massachusetts."

(def user-info ["robert8990" 2011 :name "Bob" :city "Boston"])
(defn ispis4 [& args]
  (let [[username account-year & extra-info] user-info
        {:keys [name city]} (apply hash-map extra-info)]    ;extra-info razlaze na name i city
    (format "%s is in %s" name city)))
;(ispis4)
;"Bob is in Boston"

(defn strange-adder
  ([x] (strange-adder x 1))
  ([x y] (+ x y)))
;(strange-adder 93)
;94

(defn concat-rest
  [x & rest]
  (apply str (butlast rest)))
;(concat-rest 0 1 2 3 4)
;"123"

(defn make-user
  [& [user-id]]
  {:user-id (or user-id (str (java.util.UUID/randomUUID)))})      ;ako nije unet user-id onda se formira preko random komande
;(make-user)
;{:user-id "9a8ff981-fa7c-4821-9de8-e6be90b1407c"}
;(make-user "Bobby")
;{:user-id "Bobby"}


(defn make-user1
  [username & {:keys [email join-date]
               :or {join-date (java.util.Date.)}}]
  {:username username
   :join-date join-date
   :email email
   ;; 2.592e9 -> one month in ms
   :exp-date (java.util.Date. (long (+ 2.592e9 (.getTime join-date))))})

;(make-user1 "Bobby")
;{:username "Bobby", :join-date #inst "2015-10-18T01:26:07.003-00:00", :email nil, :exp-date #inst "2015-11-17T01:26:07.003-00:00"}
#_(make-user1 "Bobby"
               :join-date (java.util.Date. 111 0 1)
               :email "bobby@example.com")
;{:username "Bobby", :join-date #inst "2010-12-31T23:00:00.000-00:00", :email "bobby@example.com", :exp-date #inst "2011-01-30T23:00:00.000-00:00"}

(defn countdown
  [x]
  (if (zero? x)
    :nula!                        ;true
    (do (println x)               ;false
      (recur (dec x)))))
;(countdown 3)
#_(3
2
1
:nula!)













