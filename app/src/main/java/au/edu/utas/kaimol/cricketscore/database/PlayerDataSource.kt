package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PlayerDataSource {
    fun add(player: Player) {
        FireStore().playerCollection()
            .add(player)
            .addOnSuccessListener {
                val playerId = it.id
                player.id = playerId
                FireStore().playerCollection().document(playerId)
                    .update("id", playerId)
                    .addOnSuccessListener {
                        Log.d("FIREBASE", "DocumentSnapshot added with ID: $playerId")
                    }
                    .addOnFailureListener {
                        Log.e("FIREBASE", "Error updating player.id", it)
                    }
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding player", it)
            }
    }

    fun updatePlayerStatus(player: Player) {
        FireStore().playerCollection()
            .whereEqualTo("name", player.name)
            .whereEqualTo("position", player.position)
            .get()
            .addOnSuccessListener {
                FireStore().playerCollection().document(it.documents[0].id)
                .update("status", player.status.toString())
                .addOnSuccessListener {
                    Log.d("FIREBASE", "DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener {
                    Log.e("FIREBASE", "Error updating document", it)
                }
            }

    }

    fun updatePlayerName(player:Player){
        FireStore().playerCollection().document(player.id!!)
            .update( "name", player.name)
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

    fun deletePlayer(player: Player){
            FireStore().playerCollection().document(player.id!!)
                .delete()
                .addOnSuccessListener {
                    Log.d("FIREBASE", "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener {
                    Log.e("FIREBASE", "Error deleting document", it)
        }
    }
}