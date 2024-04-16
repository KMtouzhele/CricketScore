package au.edu.utas.kaimol.cricketscore.entity

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.UUID

@Parcelize //https://www.youtube.com/watch?v=HWGJbaHXZ1s
class Player (
    var id : String? = null,
    var position : Int? = null,
    var name : String? = null,
    var status: PlayerStatus? = null,
    var teamName: String? = null,
    var runs: Int? = null,
    var ballsFaced: Int? = null,
    var runsLost: Int? = null,
    var totalWickets: Int? = null,
    var ballsDelivered: Int? = null,
) : Parcelable