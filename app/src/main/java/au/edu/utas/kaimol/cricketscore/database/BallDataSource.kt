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

    suspend fun getTotalRunsByCurrentBatter(matchId: String): Map<String, Int> {
        return withContext(Dispatchers.IO){
            val runsMap = mutableMapOf<String, Int>()
            try {
                val querySnapshot = FireStore().ballCollection()
                    .whereEqualTo("matchId", matchId)
                    .get()
                    .await()
                Log.d("FIREBASE", "Searching documents with matchId: $matchId")
                for (document in querySnapshot.documents) {
                    val ball = document.toObject(Ball::class.java)
                    ball?.let {
                        val currentBatter = it.currentBatter
                        val runs = it.runs
                        if (currentBatter != null) {
                            runsMap[currentBatter] = (runsMap[currentBatter] ?: 0) + runs
                        }
                    }
                }
                Log.d("FIREBASE", "Successfully retrieved runs data: $runsMap")
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error getting runs", e)
            }
            runsMap
        }
    }

    suspend fun getBallsFacedByCurrentBatter(matchId: String): Map<String, Int>{
        return withContext(Dispatchers.IO){
            val ballsFacedMap = mutableMapOf<String, Int>()
            try {
                val querySnapshot = FireStore().ballCollection()
                    .whereEqualTo("matchId", matchId)
                    .get()
                    .await()
                Log.d("FIREBASE", "Searching documents with matchId: $matchId")
                for (document in querySnapshot.documents) {
                    val ball = document.toObject(Ball::class.java)
                    ball?.let {
                        val currentBatter = it.currentBatter
                        if (currentBatter != null) {
                            ballsFacedMap[currentBatter] = (ballsFacedMap[currentBatter] ?: 0) + 1
                        }
                    }
                }
                Log.d("FIREBASE", "Successfully retrieved balls faced data: $ballsFacedMap")
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error getting balls faced", e)
            }
            ballsFacedMap
        }
    }
}