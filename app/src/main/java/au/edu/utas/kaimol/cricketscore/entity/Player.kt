package au.edu.utas.kaimol.cricketscore.entity

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Player (
    @get:Exclude var id : String? = null,
    var position : Int? = null,
    var name : String? = null,
    var status: PlayerStatus? = null,
) : Serializable