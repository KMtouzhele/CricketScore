package au.edu.utas.kaimol.cricketscore.database

import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore

class FireStore {
    fun playerCollection() : CollectionReference = Firebase.firestore.collection("players")
    fun teamCollection(): CollectionReference = Firebase.firestore.collection("teams")
    fun matchCollection(): CollectionReference = Firebase.firestore.collection("matches")
}