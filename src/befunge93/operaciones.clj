(ns befunge93.operaciones)

(defn suma [a b]
  (+ (Integer/parseInt (str a)) (Integer/parseInt (str b))))

(defn resta [a b]
  (- (Integer/parseInt (str a)) (Integer/parseInt (str b))))

(defn multiplicacion [a b]
  (* (Integer/parseInt (str a)) (Integer/parseInt (str b))))

(defn division [a b]
  (quot (Integer/parseInt (str a)) (Integer/parseInt (str b))))

(defn modulo [a b]
  (mod (Integer/parseInt (str a)) (Integer/parseInt (str b))))

(defn negacion-logica [a]
  (if (= a 0)
    1
    0))

(defn greater [a b]
  (if (> a b)
    1
    0))

(defn leer-numero []
  (let [num-str (loop [chars ""]
                  (let [c (.read *in*)]
                    (if (Character/isDigit (char c))
                      (recur (str chars (char c))) ; Acumula los dígitos
                      chars)))] ; Retorna la cadena acumulada cuando encuentra un no-dígito
    (if (empty? num-str)
      0
      (Integer/parseInt num-str))))
