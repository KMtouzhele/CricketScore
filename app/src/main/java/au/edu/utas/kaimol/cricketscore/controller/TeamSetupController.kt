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
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import java.time.LocalDateTime

class TeamSetupController {
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveMatchSetup(
        batting: Team,
        bowling: Team,
    ) {
        val start = LocalDateTime.now()
        val end = null
        val match = Match(battingTeam = batting, bowlingTeam = bowling, timeStart = start, timeEnd = end)

        TeamSetupAdapter.saveMatchSetupToFirebase(match)
    }


    fun <T : ViewBinding> getTeam(
        viewBinding: T,
        name: String,
        players: MutableList<Player>
    ): Team {
        val teamType: TeamType? = when (viewBinding) {
            is ActivityBattingTeamSetupBinding -> {
                TeamType.BATTING
            }

            is ActivityBowlingTeamSetupBinding -> {
                TeamType.BOWLING
            }

            else -> {
                null
            }
        }
        return Team(name = name, teamType = teamType, teamPlayers = players)
    }

    fun savePlayers(players: MutableList<Player>) {
        TeamSetupAdapter().savePlayerListToFirebase(players)
    }

    fun getBowlers(viewBinding: ActivityBowlingTeamSetupBinding): MutableList<Player> {
        val bowlers = mutableListOf<Player>()
        for (i in 0 until viewBinding.bowlersInfoList.childCount) {
            val childView = viewBinding.bowlersInfoList.getChildAt(i)
            val playerInfoBinding = PlayersInfoListItemBinding.bind(childView)
            val player = Player("", i + 1, playerInfoBinding.txtPlayerName.text.toString())
            bowlers.add(player)
        }
        return bowlers
    }

    fun getBatters(viewBinding: ActivityBattingTeamSetupBinding): MutableList<Player> {
        val batters = mutableListOf<Player>()
        for (i in 0 until viewBinding.battersInfoList.childCount) {
            val childView = viewBinding.battersInfoList.getChildAt(i)
            val playerInfoBinding = PlayersInfoListItemBinding.bind(childView)
            val player = Player("", i + 1, playerInfoBinding.txtPlayerName.text.toString())
            batters.add(player)
        }
        return batters
    }

}