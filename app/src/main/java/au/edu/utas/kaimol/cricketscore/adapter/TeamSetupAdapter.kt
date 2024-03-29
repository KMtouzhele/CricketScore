package au.edu.utas.kaimol.cricketscore.adapter

import au.edu.utas.kaimol.cricketscore.database.MatchDataSource
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.database.TeamDataSource
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.PlayerStatus
import au.edu.utas.kaimol.cricketscore.entity.Team
import kotlinx.coroutines.runBlocking

class TeamSetupAdapter {

    fun saveMatchToFirebase (match: Match) = runBlocking {
        MatchDataSource().add(match)
    }

    fun saveTeamToFirebase (team: Team) = runBlocking {
        TeamDataSource().add(team)
    }

    fun savePlayersToFirebase (players: MutableList<Player>){
        for(player in players){
            savePlayerToFireBase(player)
        }
    }

    fun savePlayersToTeam (players: MutableList<Player>, team: Team){
        val playerList = mutableListOf<String>()
        for(player in players){
            playerList.add(player.id!!)
        }
        team.teamPlayers = playerList
        TeamDataSource().update(team)
    }


    fun getPlayersByTeamId(teamId: String): MutableList<Player> {
        val team = getTeamById(teamId)
        val teamPlayersId = team.teamPlayers
        val players = mutableListOf<Player>()
        if (teamPlayersId != null) {
            for(playerId in teamPlayersId){
                val player = getPlayerById(playerId)
                players.add(player)
            }
        }
        return players
    }


    private fun savePlayerToFireBase (player: Player) = runBlocking {
        PlayerDataSource().add(player)
    }

    private fun getPlayerById(playerId: String): Player  = runBlocking{
        PlayerDataSource().get2(playerId)
    }

    private fun getTeamById(teamId: String): Team = runBlocking{
        TeamDataSource().get2(teamId)
    }

}