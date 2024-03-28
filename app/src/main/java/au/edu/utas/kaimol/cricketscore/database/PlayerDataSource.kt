package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Player
import kotlinx.coroutines.tasks.await

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

    fun get(id: String) : Player{
        var player = Player()
        FireStore().playerCollection()
            .document(id)
            .get()
            .addOnSuccessListener {
                player = it.toObject(Player::class.java)!!
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error getting document", it)
            }
        return player
    }
}