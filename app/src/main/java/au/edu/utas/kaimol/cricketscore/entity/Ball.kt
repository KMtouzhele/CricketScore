package au.edu.utas.kaimol.cricketscore.entity

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.type.DateTime
import java.time.LocalDateTime

class Ball (
    @get:Exclude var id: String? = null,
    var matchId: String? = null,
    var ballsDelivered: Boolean? = null,
    var currentBatter: String? = null,
    var nonBatter: String? = null,
    var bowler: String? = null,
    var result: ResultType? = null,
    var runs: Int = 0,
    var timestamp: Timestamp? = null
)