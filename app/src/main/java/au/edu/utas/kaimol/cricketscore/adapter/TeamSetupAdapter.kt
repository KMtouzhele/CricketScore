package au.edu.utas.kaimol.cricketscore.adapter

import au.edu.utas.kaimol.cricketscore.database.MatchDataSource
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.database.TeamDataSource
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.Team
import kotlinx.coroutines.runBlocking

class TeamSetupAdapter {
    fun saveTeamToFirebase (team: Team) = runBlocking {
        TeamDataSource().add(team)
    }

    fun savePlayersToFirebase (players: MutableList<Player>){
        for(player in players){
            savePlayerToFireBase(player)
        }
    }
    private fun savePlayerToFireBase (player: Player) = runBlocking {
        PlayerDataSource().add(player)
    }

    fun savePlayersToTeam (players: MutableList<Player>, team: Team){
        val playerList = mutableListOf<String>()
        for(player in players){
            playerList.add(player.id!!)
        }
        team.teamPlayers = playerList
        TeamDataSource().update(team)
    }

}