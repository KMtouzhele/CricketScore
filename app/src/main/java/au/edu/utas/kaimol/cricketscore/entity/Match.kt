package au.edu.utas.kaimol.cricketscore.entity

import com.google.firebase.firestore.Exclude
import com.google.type.DateTime
import java.time.LocalDateTime

class Match(
    @get:Exclude var id: String? = null,
    var battingTeam: Team? = null,
    var bowlingTeam: Team? = null,
    var timeStart: LocalDateTime? = null,
    var timeEnd: LocalDateTime? = null,
)