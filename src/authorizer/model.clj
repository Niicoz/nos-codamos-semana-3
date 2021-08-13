(ns authorizer.model
  (:use clojure.pprint)
  (:require [schema.core :as s]))
(s/set-fn-validation! true)

(defn uuid [] (java.util.UUID/randomUUID))

(defn new-client
  ([uuid name cpf email]
   {:client/id    uuid
    :client/name  name
    :client/cpf   cpf
    :client/email email}))

(defn new-card
  ([uuid number cvv validate limit ]
   {:card/id    uuid
    :card/number  number
    :card/cvv   cvv
    :card/validate validate
    :card/limit limit}))

(defn new-purchase
  ([uuid date value establishment category client-id]
   {:purchase/id            uuid
    :purchase/date          date
    :purchase/value         value
    :purchase/establishment establishment
    :purchase/category      category
    :purchase/client        [:client/id client-id]}))

