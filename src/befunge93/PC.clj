(ns befunge93.PC)

(defn crear-pc [i j direccion] ;; El Program Counter es un Hash, que tiene estas claves
  {:i i                        ;; Posicion i (int)
   :j j                        ;; Posicion j (int)
   :direccion direccion})      ;; Direccion (string)

(defn cambiar-direccion-pc [pc nueva-direccion]  ;; Para cambiar la direccion
  (assoc pc :direccion (str nueva-direccion)))

(defn avanzar-pc [pc]          ;; Avanza el PC
  (case (:direccion pc)
    "derecha" (if (= (:j pc) 79)
                (assoc pc :j 0)
                (assoc pc :j (+ (:j pc) 1)))
    "izquierda" (if (= (:j pc) 0)
                  (assoc pc :j 79)
                  (assoc pc :j (- (:j pc) 1)))
    "abajo" (if (= (:i pc) 24)
              (assoc pc :i 0)
              (assoc pc :i (+ (:i pc) 1)))
    "arriba" (if (= (:i pc) 0)
               (assoc pc :i 24)
               (assoc pc :i (- (:i pc) 1)))))