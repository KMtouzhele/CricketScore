package au.edu.utas.kaimol.cricketscore.adapter

import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.Player
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
}