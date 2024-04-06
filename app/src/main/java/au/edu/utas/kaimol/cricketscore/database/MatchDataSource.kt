package au.edu.utas.kaimol.cricketscore.database

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateTimeEnd(matchId: String){
        FireStore().matchCollection().document(matchId)
            .update("timeEnd", LocalDateTime.now())
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot updated with ID: $matchId")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
    }
}