(ns befunge93.pila)

(defprotocol OperacionesPila
  (apilar [pila dato] "Apilar")
  (desapilar [pila] "Desapilar"))


(defrecord Pila [lista]
  OperacionesPila
  (apilar [pila dato]             ;; Apilar
    (Pila. (vec (conj (:lista pila) dato))))

  (desapilar [pila]                   ;; Desapilar
    (if (empty? lista)
      {:valor (char 48) :pila (Pila. []) :vacia true}
      (let [top (peek lista)
            new-list (vec (pop (:lista pila)))]
        {:valor top :pila (Pila. new-list) :vacia false}))))