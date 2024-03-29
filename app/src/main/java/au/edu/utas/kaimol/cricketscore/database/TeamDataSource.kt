package au.edu.utas.kaimol.cricketscore.database

import android.util.Log
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun get(id: String) : Team = suspendCancellableCoroutine{ continuation ->
        FireStore().teamCollection()
            .document(id)
            .get()
            .addOnSuccessListener {
                val team = it.toObject(Team::class.java)!!
                continuation.resume(team)
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "Error getting document", it)
                continuation.resumeWithException(it)
            }
    }
}