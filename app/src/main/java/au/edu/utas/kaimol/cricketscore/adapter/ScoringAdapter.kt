package au.edu.utas.kaimol.cricketscore.adapter

import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import kotlinx.coroutines.runBlocking

class ScoringAdapter {
    fun saveBallToFirebase (ball: Ball) = runBlocking {
        BallDataSource().add(ball)
    }

    fun getPlayerIdFromName(name: String): String = runBlocking{
        PlayerDataSource().getPlayerIdFromName(name)
    }

    fun getAvailablePlayers(teamId: String): MutableList<Player> = runBlocking {
        PlayerDataSource().getAvailablePlayers(teamId)
    }

    fun updateBatterStatus(ball: Ball, battingTeamId: String, position: Int) {
        val matchId = ball.matchId
        val teamType = TeamType.BATTING
        if (matchId != null) {
            PlayerDataSource().update(battingTeamId, position)
        }

    }

}