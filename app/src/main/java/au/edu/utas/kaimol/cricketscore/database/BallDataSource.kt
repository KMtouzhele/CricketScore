package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Ball

class BallDataSource {
    fun add(ball: Ball){
        FireStore().ballCollection()
            .add(ball)
            .addOnSuccessListener {
                ball.id = it.id
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
                throw it
            }
    }
}