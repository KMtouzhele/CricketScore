package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player

class MatchDataSource {
    fun add(match: Match): String? {
        var id: String? = null
        FireStore().matchCollection()
            .add(match)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${it.id}")
                id = it.id
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
        return id
    }
}