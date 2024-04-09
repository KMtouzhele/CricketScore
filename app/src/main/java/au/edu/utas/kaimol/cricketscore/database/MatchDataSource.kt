package au.edu.utas.kaimol.cricketscore.database

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class MatchDataSource {
    fun add(match: Match){
        FireStore().matchCollection().document(match.battingTeam!! + " vs " + match.bowlingTeam!!)
            .set(match)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${match.battingTeam!! + " vs " + match.bowlingTeam!!}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
    }
    fun updateTotalRuns(matchId: String, totalRuns: Int) {
        FireStore().matchCollection().document(matchId)
            .update("totalRuns", totalRuns)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot updated with ID: $matchId")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
    }

    fun updateTotalWickets(matchId: String, totalWickets: Int) {
        FireStore().matchCollection().document(matchId)
            .update("totalWickets", totalWickets)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot updated with ID: $matchId")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
    }

    fun updateLastModified(matchId: String, lastModified: Timestamp) {
        FireStore().matchCollection().document(matchId)
            .update("lastModified", lastModified)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot updated with ID: $matchId")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
    }

    suspend fun getMatches(): MutableList<Match> {
        return withContext(Dispatchers.IO) {
            val matches = mutableListOf<Match>()
            try {
                val querySnapshot = FireStore().matchCollection().get().await()
                for (document in querySnapshot.documents) {
                    val match = document.toObject(Match::class.java)
                    match?.let { matches.add(it) }
                }
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error getting matches", e)
            }
            matches
        }
    }
}