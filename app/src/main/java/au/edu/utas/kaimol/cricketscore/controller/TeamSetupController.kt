package au.edu.utas.kaimol.cricketscore.controller

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import au.edu.utas.kaimol.cricketscore.adapter.TeamSetupAdapter
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBattingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBowlingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.databinding.PlayersInfoListItemBinding
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.PlayerStatus
import au.edu.utas.kaimol.cricketscore.entity.Team

class TeamSetupController() {

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
            val player = Player(
                position = i + 1,
                name = playerInfoBinding.txtPlayerName.text.toString(),
                teamName = viewBinding.txtBowlingTeamName.text.toString(),
                status = PlayerStatus.AVAILABLE
            )
            bowlers.add(player)
            bowlers.sortBy { it.position }
        }
        return bowlers
    }

    fun getBattersFromView(viewBinding: ActivityBattingTeamSetupBinding): MutableList<Player> {
        val batters = mutableListOf<Player>()
        for (i in 0 until viewBinding.battersInfoList.childCount) {
            val childView = viewBinding.battersInfoList.getChildAt(i)
            val playerInfoBinding = PlayersInfoListItemBinding.bind(childView)
            val player = Player(
                position = i + 1,
                name = playerInfoBinding.txtPlayerName.text.toString(),
                teamName = viewBinding.txtBattingTeamName.text.toString(),
                status = PlayerStatus.AVAILABLE
            )
            batters.add(player)
            batters.sortBy { it.position }
        }
        return batters
    }

    fun createValidationToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(context, message, duration).show()
    }
}