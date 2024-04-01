package au.edu.utas.kaimol.cricketscore.controller

import au.edu.utas.kaimol.cricketscore.adapter.ScoringAdapter
import au.edu.utas.kaimol.cricketscore.entity.Player

class PlayerController {
    fun getAvailablePlayers(teamId: String): MutableList<Player> {
        return ScoringAdapter().getAvailablePlayers(teamId)
    }
}