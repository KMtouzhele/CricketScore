package au.edu.utas.kaimol.cricketscore.database

import android.content.Context
import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PlayerDataSource {
    suspend fun add(player: Player) {
        val doc = FireStore().playerCollection()
            .add(player)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
            .await()
        player.id = doc.id
    }


    suspend fun get(id: String) : Player = suspendCancellableCoroutine{ continuation ->
        FireStore().playerCollection()
            .document(id)
            .get()
            .addOnSuccessListener {
                val player = it.toObject(Player::class.java)!!
                player.id = it.id
                continuation.resume(player)
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error getting document", it)
                continuation.resumeWithException(it)
            }
    }


    suspend fun getAvailablePlayersName(teamId: String): MutableList<String> {
        val db = FireStore().getInstance()
        val teamDoc = db.collection("teams").document(teamId).get().await()
        val playerIds = teamDoc.get("teamPlayers") as List<String>

        val availablePlayerNames = mutableListOf<String>()
        playerIds.forEach{
            val playerDoc = db.collection("players").document(it).get().await()
            val playerName = playerDoc.getString("name")
            val playerStatus = playerDoc.get("status") as String
            if(playerStatus == "AVAILABLE"){
                availablePlayerNames.add(playerName!!)
            }
        }
        return availablePlayerNames
    }
}