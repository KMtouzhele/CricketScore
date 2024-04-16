package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.ResultType
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


    suspend fun getBallsDeliveredByBowler(matchId: String): Map<String, Int>{
        return withContext(Dispatchers.IO){
            val ballsDeliveredMap = mutableMapOf<String, Int>()
            try {
                val querySnapshot = FireStore().ballCollection()
                    .whereEqualTo("matchId", matchId)
                    .get()
                    .await()
                Log.d("FIREBASE", "Searching documents with matchId: $matchId")
                for (document in querySnapshot.documents) {
                    val ball = document.toObject(Ball::class.java)
                    ball?.let {
                        val bowler = it.bowler
                        if (bowler != null) {
                            ballsDeliveredMap[bowler] = (ballsDeliveredMap[bowler] ?: 0) + 1
                        }
                    }
                }
                Log.d("FIREBASE", "Successfully retrieved balls delivered data: $ballsDeliveredMap")
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error getting balls delivered", e)
            }
            ballsDeliveredMap
        }
    }

    suspend fun getRunsLostByBowler(matchId: String): Map<String, Int>{
        return withContext(Dispatchers.IO){
            val runsLostMap = mutableMapOf<String, Int>()
            try {
                val querySnapshot = FireStore().ballCollection()
                    .whereEqualTo("matchId", matchId)
                    .get()
                    .await()
                Log.d("FIREBASE", "Searching documents with matchId: $matchId")
                for (document in querySnapshot.documents) {
                    val ball = document.toObject(Ball::class.java)
                    ball?.let {
                        val bowler = it.bowler
                        val runs = it.runs
                        if (bowler != null) {
                            runsLostMap[bowler] = (runsLostMap[bowler] ?: 0) + runs
                        }
                    }
                }
                Log.d("FIREBASE", "Successfully retrieved runs lost data: $runsLostMap")
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error getting runs lost", e)
            }
            runsLostMap
        }
    }

    suspend fun getTotalWicketsByBowler(matchId: String): Map<String, Int>{
        return withContext(Dispatchers.IO){
            val totalWicketsMap = mutableMapOf<String, Int>()
            try {
                val querySnapshot = FireStore().ballCollection()
                    .whereEqualTo("matchId", matchId)
                    .get()
                    .await()
                Log.d("FIREBASE", "Searching documents with matchId: $matchId")
                for (document in querySnapshot.documents) {
                    val ball = document.toObject(Ball::class.java)
                    ball?.let {
                        val bowler = it.bowler
                        val result = it.result
                        if (
                            bowler != null
                            && result != ResultType.BOUNDARIES
                            && result != ResultType.RUNS
                            && result != null
                            ) {
                            totalWicketsMap[bowler] = (totalWicketsMap[bowler] ?: 0) + 1
                        }
                    }
                }
                Log.d("FIREBASE", "Successfully retrieved total wickets data: $totalWicketsMap")
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error getting total wickets", e)
            }
            totalWicketsMap
        }
    }
}