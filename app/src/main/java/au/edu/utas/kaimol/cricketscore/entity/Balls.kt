package au.edu.utas.kaimol.cricketscore.entity

import com.google.firebase.firestore.Exclude

class Balls (
    @get:Exclude var id: String? = null,
    var ballsDelivered: Boolean? = null,
    var currentBatter: String? = null,
    var nonBatter: String? = null,
    var bowler: String? = null,
    var result: ResultType? = null,
    var runs: Int? = null
)