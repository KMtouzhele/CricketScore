package au.edu.utas.kaimol.cricketscore.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.database.MatchDataSource
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.PlayerStatus
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import com.google.firebase.Timestamp
import com.google.type.DateTime
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

class ScoringAdapter {
    fun saveBallToFirebase (ball: Ball) {
        BallDataSource().add(ball)
    }

    fun updateBatterStatus(batterName: String, position: Int) {
        val player = Player(name = batterName, position = position, status = PlayerStatus.DISMISSED)
        PlayerDataSource().update(player)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMatchResult(matchId: String, totalRuns: Int, totalWickets: Int) {
        MatchDataSource().updateTotalRuns(matchId, totalRuns)
        MatchDataSource().updateTotalWickets(matchId, totalWickets)
        MatchDataSource().updateLastModified(matchId, Timestamp.now())
    }

}