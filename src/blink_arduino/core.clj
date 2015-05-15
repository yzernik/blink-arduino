(ns blink-arduino.core
  (:require [clojure.core.async :refer :all]
            [blink-arduino.led :as led]))

(def my-led-pin-number 13)

(defn blink-routine
  []
  (<!!
   (led/keep-blinking my-led-pin-number)))

(defn -main
  [& args]
  (blink-routine)
  (println "Done!"))
