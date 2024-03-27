package au.edu.utas.kaimol.cricketscore.adapter

import au.edu.utas.kaimol.cricketscore.database.MatchDataSource
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.database.TeamDataSource
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.Team

class TeamSetupAdapter {
    fun savePlayerListToFirebase(playerList: MutableList<Player>) {
        for (i in 0 until playerList.size) {
            PlayerDataSource().add(playerList[i])
        }
    }

    fun saveTeamToFirebase(team: Team){
        TeamDataSource().add(team)
    }

    companion object {
        fun saveMatchSetupToFirebase(match: Match) {
            MatchDataSource().add(match)
        }
    }
}