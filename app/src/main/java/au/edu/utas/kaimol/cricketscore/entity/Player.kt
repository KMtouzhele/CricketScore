package au.edu.utas.kaimol.cricketscore.entity

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize //https://www.youtube.com/watch?v=HWGJbaHXZ1s
class Player (
    @get:Exclude var id : String? = null,
    var position : Int? = null,
    var name : String? = null,
    var status: PlayerStatus? = null,
) : Parcelable