package au.edu.utas.kaimol.cricketscore.adapter

import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.PlayerStatus
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import kotlinx.coroutines.runBlocking

class ScoringAdapter {
    fun saveBallToFirebase (ball: Ball) {
        BallDataSource().add(ball)
    }


    fun getAvailablePlayers(teamId: String): MutableList<Player> = runBlocking {
        PlayerDataSource().getAvailablePlayers(teamId)
    }

    fun updateBatterStatus(ball: Ball, batterName: String, position: Int) {
        val player = Player(name = batterName, position = position, status = PlayerStatus.DISMISSED)
        PlayerDataSource().update(player)
    }

    fun getTotalRuns(batter: String): Int = runBlocking {
        val balls = BallDataSource().getBalls(batter)
        return@runBlocking balls.sumOf { it.runs }
    }

}