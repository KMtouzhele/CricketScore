package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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

    fun updatePlayerStatus(player: Player) {
        FireStore().playerCollection().document(player.name!!)
            .update("status", player.status.toString())
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error updating document", it)
            }
    }

    fun updatePlayerName(player:Player){
        FireStore().playerCollection().document(player.name!!)
            .update("id",player.name, "name", player.name)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error updating document", it)
            }
    }


    suspend fun getPlayerByTeamName(teamName: String): MutableList<Player>{
        return withContext(Dispatchers.IO){
            val players = mutableListOf<Player>()
            try {
                val querySnapshot = FireStore().playerCollection()
                    .whereEqualTo("teamName", teamName)
                    .get()
                    .await()
                for (document in querySnapshot.documents) {
                    val player = document.toObject(Player::class.java)
                    player?.let { players.add(it) }
                }
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error getting players", e)
            }
            players
        }
    }
}