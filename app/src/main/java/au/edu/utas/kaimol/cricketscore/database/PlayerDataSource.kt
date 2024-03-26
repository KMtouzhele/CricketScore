package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Player

class PlayerDataSource {
    fun add(player: Player){
        FireStore().playerCollection()
            .add(player)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error adding document", it)
            }
    }
}