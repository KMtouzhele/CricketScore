package au.edu.utas.kaimol.cricketscore.entity

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Team (
    @get:Exclude var id : String? = null,
    var name : String? = null,
    var teamType : TeamType? = null,
    var teamPlayers : MutableList<String>? = null
) : Serializable