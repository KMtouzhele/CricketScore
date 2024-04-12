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

    fun updateScores(player: Player){
        FireStore().playerCollection().document(player.id!!)
            .update("runs", player.runs,
                "ballsFaced", player.ballsFaced,
                "runsLost", player.runsLost,
                "ballsDelivered", player.ballsDelivered,
                "totalWickets", player.totalWickets,
                "status", player.status
            )
            .addOnSuccessListener {
                Log.d("FIREBASE", "Player scores successfully updated!")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error updating player scores", it)
            }
    }
}