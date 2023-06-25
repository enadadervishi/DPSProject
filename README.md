# Project questions

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

## BIG BIG ISSUE : mosquitto!!!
c'è un problema. quando ricevo dal robot i valori dell'average non capidsco come poter salvarli in aggiunta a quelli
precedenti. ovvero: in robot c'è una lista che contiene averages
dopodiche usando mqtt dei valori vengono inviati in piu al server. 
questi valori dal server li voglio aggiungere alla lista della classe robot
non riesco a farlo. ho problemi con "deserializzazione" dell'oggetto che viene mandato: 
io con mosquitto mando un messaggio che è prima un array di due elementi 
nel primo el contiene il nome robot e secondo tutti averages
prima di mandare il mex al server lo rendo "tostring" in modo tale che arrivi 
solo la stringa/getbye al server. il server lo legge. Nella class adminsub non 
capisco perche non riesco a deserializzare quell messggio stringa!!