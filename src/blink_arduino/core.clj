(ns blink-arduino.core
  (:require [blink-arduino.led :as led]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "loop returns: " (led/timeout-loop))
  (println "Done!"))
