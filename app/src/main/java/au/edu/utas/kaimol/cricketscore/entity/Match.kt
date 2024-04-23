package au.edu.utas.kaimol.cricketscore.entity

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

class Match(
    @get:Exclude var id: String? = null,
    var matchId: String? = null,
    var battingTeam: String? = null,
    var bowlingTeam: String? = null,
    var totalWickets: Int? = null,
    var totalRuns: Int? = null,
    var lastModified: Timestamp? = null
)