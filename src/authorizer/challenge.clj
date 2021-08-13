(ns authorizer.challenge
  (:use clojure.pprint)
  (:require [authorizer.db :as db]
            [authorizer.logic :as c.logic]
            [schema.core :as s]
            [authorizer.model :as model]
            [datomic.api :as d]))

(def conn (db/abre-conexao!))
(db/cria-schema! conn)

(pprint (def marcos (model/new-client (model/uuid) "Marcos Wilson", "123456789", "marcos@test.com")))

(pprint (def card (model/new-card (model/uuid) "123456789", "123", "12/25", 1000M)))

(pprint (def purchase1 (model/new-purchase (model/uuid) "10/11/1995", 100M, "Piticas", "Roupas" (:client/id marcos))))
(pprint (def purchase2 (model/new-purchase (model/uuid) "15/01/2021", 502M, "Riot", "Games" (:client/id marcos))))
(pprint (def purchase3 (model/new-purchase (model/uuid) "10/11/2021", 150M, "Burguer King", "Fast Food" (:client/id marcos))))

(pprint (def card-id (:card/id card)))

(pprint card-id)



(pprint marcos)
(db/add-client! conn [marcos])
(pprint (db/add-client! conn [marcos card purchase1 purchase2 purchase3]))

(pprint @(db/add-client! conn [{:client/name     "Maria"
                                :client/cpf      "11155544488"
                                :client/email    "teste@test.com"
                                :client/id       (model/uuid)
                                :client/card     [:card/id card-id]}]))




(pprint (db/all-clients (d/db conn)))
(pprint (db/all-cards (d/db conn)))
(pprint (db/all-purchases(d/db conn)))

;(db/apaga-banco!)
