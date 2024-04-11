package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.Match
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BallDataSource {
    fun add(ball: Ball){
        FireStore().ballCollection()
            .add(ball)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
                throw it
            }
    }

    suspend fun getByMatchId(matchId: String): MutableList<Ball> {
        return withContext(Dispatchers.IO){
            val balls = mutableListOf<Ball>()
            try {
                val querySnapshot = FireStore().ballCollection()
                    .whereEqualTo("matchId", matchId)
//                    .orderBy("timestamp")
                    .get()
                    .await()
                for (document in querySnapshot.documents) {
                    val ball = document.toObject(Ball::class.java)
                    ball?.let { balls.add(it) }
                }
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error getting matches", e)
            }
            balls
        }
    }
}