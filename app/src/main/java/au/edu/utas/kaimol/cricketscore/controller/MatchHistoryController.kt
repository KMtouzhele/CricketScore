package au.edu.utas.kaimol.cricketscore.controller

import au.edu.utas.kaimol.cricketscore.database.MatchDataSource
import au.edu.utas.kaimol.cricketscore.entity.Match

class MatchHistoryController {
    suspend fun getMatches(): MutableList<Match> {
        return MatchDataSource().getMatches()
    }
}