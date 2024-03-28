package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Team
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TeamDataSource {
    //Modified by ChatGPT
    suspend fun add(team: Team){
            val doc = FireStore().teamCollection()
                .add(team)
                .addOnSuccessListener {
                    Log.d("FIREBASE", "DocumentSnapshot added with ID: ${it.id}")

                }
                .addOnFailureListener {
                    Log.e("FIREBASE", "Error adding document", it)
                }
                .await()
        team.id = doc.id
    }

    fun update(team: Team) {
        FireStore().teamCollection()
            .document(team.id!!)
            .set(team)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Successfully updated team: ${team.id}")
            }
            .addOnFailureListener{
                Log.e("FIREBASE", "Error updating team", it)
            }
    }

    fun get(id: String) : Team{
        var team : Team = Team(teamPlayers = mutableListOf())
        FireStore().teamCollection()
            .document(id)
            .get()
            .addOnSuccessListener {
                team = it.toObject(Team::class.java)!!
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error getting document", it)
            }
        return team
    }
}