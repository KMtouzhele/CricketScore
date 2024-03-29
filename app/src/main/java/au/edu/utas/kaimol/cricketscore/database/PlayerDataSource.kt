package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.Team
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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
}