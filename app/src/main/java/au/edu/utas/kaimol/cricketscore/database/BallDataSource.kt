package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Ball
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

    suspend fun getBalls(batter: String): MutableList<Ball> = withContext(Dispatchers.IO) {
        val balls = mutableListOf<Ball>()
        FireStore().ballCollection()
            .whereEqualTo("currentBatter", batter)
            .get()
            .await()
            .forEach { document ->
                document.toObject(Ball::class.java).let { balls.add(it) }
            }
        balls
    }


}