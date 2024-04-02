package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import kotlinx.coroutines.tasks.await

class MatchDataSource {
    fun add(match: Match){
        FireStore().matchCollection().document(match.battingTeam!! + "vs" + match.bowlingTeam!!)
            .set(match)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${match.battingTeam!! + "vs" + match.bowlingTeam!!}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
    }
}