# Project questions

## brew services start mosquitto
## brew services stop mosquitto


LAVORO:
TUTTO REST 
CLIENT ROBOT: ADD-REMOVE
CLIENT ADMIN: GET 



gprc: solo client robot

USO gPRC per mandare a quelli che sono i client in ascolto la posizione del nuovo arrivato?


il server è a contatto con il service
mentre i check vanno fatto sul singletone e sincronizzati percio in classe 
robots dove c'è listaaa yuhuuu daje che ce la puoi fare


quando si aggiungono robot handled la posizione un po a caso 
pos 3, 2, 1, 0,... e cosi via metodo ad cazzum

File protos are used to send infos to clients 

Save air pollution levels on an object "airPoll Levels" to save the infos about each district air poll:
id
district
timestamp 
level 


Vedi prima lezione
Il thread sta in wait fino a quando non ho il buffer pieno a 8. 
quando è pieno a 8 allora notifyall col thread che quindi mi manda l'avergage di questi
poi questo average verrà salvato da qualche parte e poi il buffer veràà svuotato di 4 misurazioni più vecchie
e a questo punto di nuovo wait fino a quando non si riempie e cosi via dicendo

Poi dal cleaning robot ogni 15 sec viene inviato al server un mex trhough mosquitto:
questo contiene una lista di medie delle misurazioni


Manca assolutamente capire come "fermarmi" a 8 misurazioni per fare le media
