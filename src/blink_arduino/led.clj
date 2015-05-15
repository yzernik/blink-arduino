(ns blink-arduino.led
  (:require [clojure.core.async :refer :all]
            [firmata.core :as fc]))


(defn make-pin [pin-number]
  (let [state (atom nil)]
    {:number pin-number :state state}))

(defn set-pin [pin new-state-fn]
  (let [state (:state pin)
        new-state (swap! state new-state-fn)]
    (do (println "Pin:" (:number pin) ", state:" new-state))))

(defn opposite-state [pin-state]
  (if (= :high pin-state) :low :high))

(defn toggle-pin [pin]
  (set-pin pin opposite-state))

(defn alternate [pin-num]
  (let [pin (make-pin pin-num)]
    (go (while true
          (<! (timeout 100))
          (toggle-pin pin)))))

(defn blink [pin duration]
  (go (do
        (<! (timeout 100))
        (toggle-pin pin))))

(defn timeout-loop []
  (<!!
   (alternate 13)))
