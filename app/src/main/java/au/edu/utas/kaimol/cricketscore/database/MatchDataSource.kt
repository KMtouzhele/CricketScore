package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import kotlinx.coroutines.tasks.await

class MatchDataSource {
    suspend fun add(match: Match){
        val doc = FireStore().matchCollection()
            .add(match)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
            .await()
        match.id = doc.id
    }
}