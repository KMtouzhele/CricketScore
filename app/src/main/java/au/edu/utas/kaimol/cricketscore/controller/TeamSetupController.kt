package au.edu.utas.kaimol.cricketscore.controller

import au.edu.utas.kaimol.cricketscore.adapter.TeamSetupAdapter
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBattingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBowlingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.databinding.PlayersInfoListItemBinding
import au.edu.utas.kaimol.cricketscore.entity.Player

class TeamSetupController {
    fun savePlayers(players: MutableList<Player>) {
        TeamSetupAdapter().savePlayerList(players)
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