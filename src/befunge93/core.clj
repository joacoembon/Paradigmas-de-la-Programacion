(ns befunge93.core
  (:gen-class)
  (:require [befunge93.pila :as Pila]
            [befunge93.PC :as PC]
            [befunge93.operaciones :as Operaciones]))

(defn cargar-archivo [ruta]   ;; Lee el archivo y carga una matriz de 80x25
  (let [lineas (line-seq (clojure.java.io/reader ruta))
        filas (take 25 (concat lineas (repeat "")))] ;; Asegura que haya 25 filas
    (vec (map (fn [linea]
                (vec (take 80 (concat (seq linea) (repeat \ ))))) ;; Trunca o rellena hasta 80 caracteres
              filas))))

(defn desapilar-2 [pila]
  (let [mapa (Pila/desapilar pila)  ;; Desapilar el primer valor
        b (:valor mapa)              ;; Guardamos el primer valor
        pila-desapilada (:pila mapa)        ;; Nueva pila después de desapilar
        mapa2 (Pila/desapilar pila-desapilada)  ;; Desapilamos el segundo valor
        a (:valor mapa2)             ;; Guardamos el segundo valor
        pila-desapilada2 (:pila mapa2)]
    {:pila pila-desapilada2
     :a a
     :b b}))

(defn desapilar-1 [pila]
  (let [mapa (Pila/desapilar pila)
        a (:valor mapa)
        vacia (:vacia mapa)
        nueva-pila (:pila mapa)]
    {:pila nueva-pila
     :a a
     :vacia vacia}))

(defn while-loop [toroide condicion pc pila modo-cadena]
  (loop [toroide toroide
         condicion condicion
         pc pc
         pila pila
         modo-cadena modo-cadena]
    (if (boolean condicion) ; Verifica la condición
      (let [actual (get (get toroide (:i pc)) (:j pc))] ; Obtiene el valor actual del toroide
          (if (Character/isDigit (char actual)) ; Si es dígito, apila
                (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar pila actual) modo-cadena)
                (if (and (boolean modo-cadena) (not= (str actual) "\"")) ; Si está en modo cadena, apila el valor como entero
                  (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar pila (int actual)) modo-cadena)
                  (case (str actual)
                    "+" (let [mapa (desapilar-2 pila)]
                          (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (:pila mapa) (Operaciones/suma (:a mapa) (:b mapa))) modo-cadena))

                    "-" (let [mapa (desapilar-2 pila)]
                          (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (:pila mapa) (Operaciones/resta (:a mapa) (:b mapa))) modo-cadena))

                    "*" (let [mapa (desapilar-2 pila)]
                          (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (:pila mapa) (Operaciones/multiplicacion (:a mapa) (:b mapa))) modo-cadena))

                    "/" (let [mapa (desapilar-2 pila)]
                          (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (:pila mapa) (Operaciones/division (:a mapa) (:b mapa))) modo-cadena))

                    "%" (let [mapa (desapilar-2 pila)]
                          (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (:pila mapa) (Operaciones/modulo (:a mapa) (:b mapa))) modo-cadena))

                    "!" (let [mapa (desapilar-1 pila)]
                          (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (:pila mapa) (Operaciones/negacion-logica (:a mapa))) modo-cadena))

                    "`" (let [mapa (desapilar-2 pila)]
                          (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (:pila mapa) (Operaciones/greater (:a mapa) (:b mapa))) modo-cadena))

                    ">" (recur toroide condicion (PC/avanzar-pc (PC/cambiar-direccion-pc pc "derecha")) pila modo-cadena)

                    "<" (recur toroide condicion (PC/avanzar-pc (PC/cambiar-direccion-pc pc "izquierda")) pila modo-cadena)

                    "^" (recur toroide condicion (PC/avanzar-pc (PC/cambiar-direccion-pc pc "arriba")) pila modo-cadena)

                    "v" (recur toroide condicion (PC/avanzar-pc (PC/cambiar-direccion-pc pc "abajo")) pila modo-cadena)

                    "?" (let [nueva-direccion (rand-nth ["derecha" "izquierda" "arriba" "abajo"])]
                          (recur toroide condicion (PC/avanzar-pc (PC/cambiar-direccion-pc pc nueva-direccion)) pila modo-cadena))

                    "_" (let [mapa (desapilar-1 pila)
                              new-pc (if (= (Integer/parseInt (str (:a mapa))) 0)
                                       (PC/avanzar-pc (PC/cambiar-direccion-pc pc "derecha"))
                                       (PC/avanzar-pc (PC/cambiar-direccion-pc pc "izquierda")))]
                          (recur toroide condicion new-pc (:pila mapa) modo-cadena))

                    "|" (let [mapa (desapilar-1 pila)
                              new-pc (if (= (Integer/parseInt (str (:a mapa))) 0)
                                       (PC/avanzar-pc (PC/cambiar-direccion-pc pc "abajo"))
                                       (PC/avanzar-pc (PC/cambiar-direccion-pc pc "arriba")))]
                          (recur toroide condicion new-pc (:pila mapa) modo-cadena))

                    "\"" (recur toroide condicion (PC/avanzar-pc pc) pila (not (boolean modo-cadena)))

                    ":" (let [mapa (desapilar-1 pila)]
                          (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (Pila/apilar (:pila mapa) (:a mapa)) (:a mapa)) modo-cadena))

                    "\\" (let [mapa (desapilar-2 pila)]
                           (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar (Pila/apilar (:pila mapa) (:b mapa)) (:a mapa)) modo-cadena))

                    "$" (let [mapa (desapilar-1 pila)] ; Descartamos el valor
                          (recur toroide condicion (PC/avanzar-pc pc) (:pila mapa) modo-cadena))

                    "." (let [mapa (desapilar-1 pila)]
                          (print (str (:a mapa) " "))
                          (recur toroide condicion (PC/avanzar-pc pc) (:pila mapa) modo-cadena))

                    "," (let [mapa (desapilar-1 pila)]
                          (if (:vacia mapa)
                            (recur toroide condicion (PC/avanzar-pc pc) (:pila mapa) modo-cadena)
                            (do
                              (print (char (:a mapa)))
                              (recur toroide condicion (PC/avanzar-pc pc) (:pila mapa) modo-cadena))))

                    "#" (recur toroide condicion (PC/avanzar-pc (PC/avanzar-pc pc)) pila modo-cadena)

                    "g" (let [mapa (desapilar-2 pila) ; Desapila las coordenadas
                              x (:a mapa)
                              y (:b mapa)
                              pila-restante (:pila mapa)
                              x-int (if (or (instance? Long x) (instance? Integer x))
                                      x
                                      (Character/getNumericValue (char x)))
                              y-int (if (or (instance? Long y) (instance? Integer y))
                                      y
                                      (Character/getNumericValue (char y)))
                              valor (if (or (< x-int 0) (> x-int 79) (< y-int 0) (> y-int 24))
                                      0
                                      (get (get toroide y-int) x-int))]
                          (if (Character/isDigit (char valor))
                            (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar pila-restante valor) modo-cadena)
                            (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar pila-restante (int valor)) modo-cadena)))

                    "p" (let [mapa (desapilar-2 pila) ; Desapila las coordenadas y el valor
                              mapa2 (desapilar-1 (:pila mapa))
                              x-normalizado (if (or (instance? Long (:a mapa)) (instance? Integer (:a mapa)))
                                              (:a mapa)
                                              (Character/getNumericValue (char (:a mapa))))
                              y-normalizado (if (or (instance? Long (:b mapa)) (instance? Integer (:b mapa)))
                                              (:b mapa)
                                              (Character/getNumericValue (char (:b mapa))))
                              nuevo-toroide (if (Character/isDigit (char (:a mapa2)))
                                              (assoc toroide
                                                y-normalizado (assoc (get toroide y-normalizado) x-normalizado (:a mapa2)))
                                              (assoc toroide
                                                y-normalizado (assoc (get toroide y-normalizado) x-normalizado (char (:a mapa2)))))]
                          (recur nuevo-toroide condicion (PC/avanzar-pc pc) (:pila mapa2) modo-cadena))

                    "&" (let [valor (Operaciones/leer-numero)]
                              (recur toroide condicion (PC/avanzar-pc pc) (Pila/apilar pila valor) modo-cadena))

                    "~" (let [char (try
                                     (.read *in*)
                                     (catch Exception _ -1))      ;Para EOF
                              nueva-pila (if (neg? char)
                                           pila ; No apilamos nada si no hay más entrada
                                           (Pila/apilar pila char))]
                          (recur toroide condicion (PC/avanzar-pc pc) nueva-pila modo-cadena))

                    "@" (recur toroide false pc pila modo-cadena)
                    (recur toroide condicion (PC/avanzar-pc pc) pila modo-cadena))))))))

(defn -main [& args]
  (let [path (first args)]
    (if path
      (let [toroide (cargar-archivo path)   ;; Crear una matriz vacía 80x25
            pc (PC/crear-pc 0 0 "derecha")  ;; Crear el Program Counter
            pila (Pila/->Pila [])           ;; Crear Pila
            modo-cadena false]
        (while-loop toroide true pc pila modo-cadena)))))
