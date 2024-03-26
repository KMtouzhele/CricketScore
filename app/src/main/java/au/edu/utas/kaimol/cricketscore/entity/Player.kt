package au.edu.utas.kaimol.cricketscore.entity

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Player (
    @get:Exclude var id : String,
    var position : Int,
    var name : String
) : Serializable