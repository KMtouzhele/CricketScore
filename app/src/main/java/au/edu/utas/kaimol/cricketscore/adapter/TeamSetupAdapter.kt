package au.edu.utas.kaimol.cricketscore.adapter

import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.entity.Player

class TeamSetupAdapter {
    fun savePlayerList(playerList: MutableList<Player>) {
        for (i in 0 until playerList.size) {
            PlayerDataSource().add(playerList[i])
        }
    }
}