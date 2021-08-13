(ns authorizer.db
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/authorizer")

(defn abre-conexao! []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco! []
  (d/delete-database db-uri))

(abre-conexao!)

(apaga-banco!)

(def schema [
             ;Clients

             {:db/ident       :client/name
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O nome do cliente"}
             {:db/ident       :client/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O cpf do cliente"}
             {:db/ident       :client/email
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O email do cliente"}
             {:db/ident       :client/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :client/card
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}


             ;Cards

             {:db/ident       :card/number
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O numero do cartão"}
             {:db/ident       :card/cvv
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "cvv do cartão"}
             {:db/ident       :card/validate
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "validade do cartão"}
             {:db/ident       :card/limit
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "limite do cartão"}
             {:db/ident       :card/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}

             ;Purchases

             {:db/ident       :purchase/date
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Data da compra"}
             {:db/ident       :purchase/establishment
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Nome do estabelecimento"}
             {:db/ident       :purchase/category
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Categoria"}
             {:db/ident       :purchase/value
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "Valor da compra"}
             {:db/ident       :purchase/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             {:db/ident       :purchase/client
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}

             ])



(defn cria-schema! [conn]
  (d/transact conn schema))

(defn add-client!
  ([conn produtos]
   (d/transact conn produtos)))


(defn all-clients
  [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :client/name]] db))

(defn all-cards
  [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :card/number]] db))

(defn all-purchases
  [db]
  (d/q '[:find (pull ?entidade [*])
         :where [?entidade :purchase/id]] db))