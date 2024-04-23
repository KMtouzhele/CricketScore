package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Team

class TeamDataSource {
    //Modified by ChatGPT
    fun add(team: Team){
            FireStore().teamCollection().document(team.name!!)
                .set(team)
                .addOnSuccessListener {
                    Log.d("FIREBASE", "DocumentSnapshot added with ID: ${team.name}")

                }
                .addOnFailureListener {
                    Log.e("FIREBASE", "Error adding document", it)
                }
    }

    fun update(team: Team) {
        FireStore().teamCollection().document(team.name!!)
            .set(team)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Successfully updated team: ${team.id}")
            }
            .addOnFailureListener{
                Log.e("FIREBASE", "Error updating team", it)
            }
    }

}