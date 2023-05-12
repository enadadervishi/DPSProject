# Project questions

In class Robot is it necessary to check the requests
passing through the server? 
So: client enter ID -> server -> check into server
-> response to the client ...
(io non ho fatto bene la parte di gBlaBlaBla con la somma in quanto non capisco 
come ricevere due messaggi insieme e poi elaborarli l'un l'altro)

collegamento fra server e client:
ovvero
il client sceglie un numero
il numero viene inviato al server
ora però il server dovrebbe mandare insietro al client con il metodo del lab1 
la lista dei partecipanti, o comunque il risultato. 
però io per ora ho semplicemente creato si un collegamento fra server e client
giusto per inviare al server il valore premuto dal client
e poi però è la classe client a eseguire le getRequest etc etc
credo non sia giusto, credo che tutte le operazioni vadano eseguite 
informando in certo senso il server, giusto?
P.S.: non mi legge il client quello che il server ha scritto -.-


altro prob in robotClient class
nel metodo add quando provo ad aggiungere qualcosa mi da errore 400 bad request
come mai?


LAVORO:
TUTTO REST 
CLIENT ROBOT: ADD REMOVE
CLIENT ADMIN: GET 

gprc: solo client robot



il server è a contatto con il service
mentre i check vanno fatto sul singletone e sincronizzati percio in classe 
robot dove c'è listaaa yuhuuu daje che ce la puoi fare