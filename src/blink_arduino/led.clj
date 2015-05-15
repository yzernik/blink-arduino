(ns blink-arduino.led
  (:require [clojure.core.async :refer :all]
            [firmata.core :as fc]))

(def board (fc/open-serial-board :auto-detect))

(defn make-pin
  [pin-number]
  (let [state (atom nil)]
    {:number pin-number :state state}))

(defn set-pin
  [pin new-state]
  (let [state (:state pin)
        num (:number pin)]
    (do (reset! state new-state)
        (fc/set-digital board num new-state)
        (println pin))))

(defn blink
  [pin duration]
  (go (do
        (set-pin pin :high)
        (<! (timeout duration))
        (set-pin pin :low))))

(defn keep-blinking
  [pin-num]
  (let [pin (make-pin pin-num)]
    (go (while true
          (<! (timeout 500))
          (blink pin 50)))))
