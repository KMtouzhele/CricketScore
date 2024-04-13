package au.edu.utas.kaimol.cricketscore.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.database.MatchDataSource
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.PlayerStatus
import com.google.firebase.Timestamp

class ScoringAdapter {
    fun saveBallToFirebase (ball: Ball) {
        BallDataSource().add(ball)
    }

    fun updateDismissedBatter(batterName: String) {
        val player = Player(name = batterName, status = PlayerStatus.DISMISSED)
        PlayerDataSource().updatePlayerStatus(player)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMatchResult(matchId: String, totalRuns: Int, totalWickets: Int) {
        MatchDataSource().updateTotalRuns(matchId, totalRuns)
        MatchDataSource().updateTotalWickets(matchId, totalWickets)
        MatchDataSource().updateLastModified(matchId, Timestamp.now())
    }

    fun updatePlayingPlayers(batterName: String) {
        val player = Player(name = batterName, status = PlayerStatus.PLAYING)
        PlayerDataSource().updatePlayerStatus(player)
    }


}