package au.edu.utas.kaimol.cricketscore.controller

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding
import au.edu.utas.kaimol.cricketscore.adapter.TeamSetupAdapter
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBattingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBowlingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.databinding.PlayersInfoListItemBinding
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.PlayerStatus
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

class TeamSetupController {

    fun saveMatch(match: Match) {
        TeamSetupAdapter().saveMatchToFirebase(match)
    }
    fun saveTeam(team: Team) {
        TeamSetupAdapter().saveTeamToFirebase(team)
    }

    fun savePlayers(players: MutableList<Player>, team: Team) {
        TeamSetupAdapter().savePlayersToFirebase(players)
        TeamSetupAdapter().savePlayersToTeam(players, team)
    }

    fun getBowlersFromView(viewBinding: ActivityBowlingTeamSetupBinding): MutableList<Player> {
        val bowlers = mutableListOf<Player>()
        for (i in 0 until viewBinding.bowlersInfoList.childCount) {
            val childView = viewBinding.bowlersInfoList.getChildAt(i)
            val playerInfoBinding = PlayersInfoListItemBinding.bind(childView)
            val player = Player(id = "", position = i + 1, name = playerInfoBinding.txtPlayerName.text.toString(), status = PlayerStatus.AVAILABLE)
            bowlers.add(player)
        }
        return bowlers
    }

    fun getBattersFromView(viewBinding: ActivityBattingTeamSetupBinding): MutableList<Player> {
        val batters = mutableListOf<Player>()
        for (i in 0 until viewBinding.battersInfoList.childCount) {
            val childView = viewBinding.battersInfoList.getChildAt(i)
            val playerInfoBinding = PlayersInfoListItemBinding.bind(childView)
            val player = Player(position = i + 1, name = playerInfoBinding.txtPlayerName.text.toString(), status = PlayerStatus.AVAILABLE)
            batters.add(player)
            batters.sortBy { it.position }
        }
        return batters
    }
}