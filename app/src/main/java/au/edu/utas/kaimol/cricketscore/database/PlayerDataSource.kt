package au.edu.utas.kaimol.cricketscore.database

import android.content.Context
import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.PlayerStatus
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
    fun add(player: Player) {
        FireStore().playerCollection().document(player.name!!)
            .set(player)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${player.name}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
    }

    fun update(player: Player) {
        FireStore().playerCollection().document(player.name!!)
            .set(player)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error updating document", it)
            }
    }



    suspend fun getAvailablePlayers(teamId: String): MutableList<Player>{
        val db = FireStore().getInstance()
        val teamDoc = db.collection("teams").document(teamId).get().await()
        val playerIds = teamDoc.get("teamPlayers") as List<String>

        val availablePlayers = mutableListOf<Player>()
        playerIds.forEach{ it ->
            val playerDoc = db.collection("players").document(it).get().await()
            val playerName = playerDoc.getString("name")
            val playerStatus = playerDoc.get("status") as String
            val position = playerDoc.get("position") as Int
            if(playerStatus == "AVAILABLE"){
                val player = Player(id = it, name = playerName!!, position = position, status = PlayerStatus.AVAILABLE)
                availablePlayers.add(player)
                availablePlayers.sortBy { position }
            }
        }
        return availablePlayers
    }

}