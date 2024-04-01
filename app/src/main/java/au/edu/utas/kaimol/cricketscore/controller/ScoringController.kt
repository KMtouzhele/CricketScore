package au.edu.utas.kaimol.cricketscore.controller

import androidx.viewbinding.ViewBinding
import au.edu.utas.kaimol.cricketscore.adapter.ScoringAdapter
import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.ResultType
import au.edu.utas.kaimol.cricketscore.view.fragments.ScoreboardFragment

class ScoringController(private val ui: FragmentScoreboardBinding) {

    fun addBall(ball: Ball, battingTeamId: String, bowlingTeamId: String){
        ScoringAdapter().saveBallToFirebase(ball)
        refreshChipSelection()

        // TODO Update player status
        if (wicketType() != null){
            val position = ui.spinnerBatter1.selectedItemPosition
            ScoringAdapter().updateBatterStatus(ball, battingTeamId, position)
        }
    }


    fun btnStatusUpdates() {
        ui.btnConfirm.isEnabled = !(ui.chipRuns.checkedChipId != -1
                && ui.chipBoundaries.checkedChipId != -1
                && ui.chipWicket.checkedChipId != -1
                && ui.chipExtras.checkedChipId != -1)

    }

    fun isBallDelivered(): Boolean{
        return ui.chipExtras.checkedChipId == -1
    }

    fun getRuns(): Int{
        var runs: Int = 0
        runs = if (ui.chipExtras.checkedChipId != -1){
            getExtraRuns()
        } else if (ui.chipBoundaries.checkedChipId != -1){
            getBoundaryRuns()
        } else if (ui.chipWicket.checkedChipId != -1){
            0
        } else {
            getRunRuns()
        }
        return runs
    }

    private fun getRunRuns(): Int{
        var runs: Int = 0
        when(ui.chipRuns.checkedChipId){
            ui.chip1Runs.id -> { runs = 1 }
            ui.chip2Runs.id -> { runs = 2 }
            ui.chip3Runs.id -> { runs = 3 }
            ui.chip4Runs.id -> { runs = 4 }
            ui.chip5Runs.id -> { runs = 5 }
            ui.chip6Runs.id -> { runs = 6 }
            ui.chip7Runs.id -> { runs = 7 }
            ui.chip8Runs.id -> { runs = 8 }
            ui.chip9Runs.id -> { runs = 9 }
            ui.chip10Runs.id -> { runs = 10 }
            ui.chip11Runs.id -> { runs = 11 }
            ui.chip12Runs.id -> { runs = 12 }
            ui.chip13Runs.id -> { runs = 13 }
        }
        return runs
    }

    private fun getBoundaryRuns(): Int{
        var runs: Int = 0
        when(ui.chipBoundaries.checkedChipId){
            ui.chip4Runs.id -> { runs = 4 }
            ui.chip6Runs.id -> { runs = 6 }
        }
        return runs
    }

    private fun getExtraRuns(): Int{
        var runs: Int = 0
        when(ui.chipExtras.checkedChipId){
            ui.chipNoBall.id -> { runs = 1 }
            ui.chipBye.id -> { runs = getRunRuns() }
            ui.chipLegByes.id -> { runs = getRunRuns() }
        }
        return runs
    }

    fun getType(): ResultType{
        return if (wicketType() != null){
            wicketType()!!
        } else if (extraType() != null){
            extraType()!!
        } else if (boundaryType() != null){
            boundaryType()!!
        } else {
            ResultType.RUNS
        }
    }

    private fun wicketType(): ResultType? {
        var type: ResultType? = null
        when(ui.chipWicket.checkedChipId){
            ui.chipBowled.id -> { type = ResultType.BOWLED }
            ui.chipCaught.id -> { type = ResultType.CATCH }
            ui.chipCAndB.id -> { type = ResultType.CATCH_BOWLED}
            ui.chipLBW.id -> { type = ResultType.LBW }
            ui.chipRunOut.id -> { type = ResultType.RUN_OUT }
            ui.chipStumping.id -> { type = ResultType.STUMPING }
            ui.chipHitWicket.id -> { type = ResultType.HIT_WICKET }
        }
        return type
    }

    private fun extraType(): ResultType? {
        var type: ResultType? = null
        when(ui.chipExtras.checkedChipId){
            ui.chipNoBall.id -> { type = ResultType.NO_BALL }
            ui.chipBye.id -> { type = ResultType.BYE }
            ui.chipLegByes.id -> { type = ResultType.LEG_BYES }
            ui.chipWide.id -> { type = ResultType.WIDE }
            ui.chipDeadBall.id -> { type = ResultType.DEAD_BALL }
        }
        return type
    }

    private fun boundaryType(): ResultType? {
        var type: ResultType? = null
        if(ui.chipBoundaries.checkedChipId >= 0 ){
            type = ResultType.BOUNDARIES
        }
        return type
    }

    private fun refreshChipSelection(){
        ui.chipRuns.clearCheck()
        ui.chipBoundaries.clearCheck()
        ui.chipWicket.clearCheck()
        ui.chipExtras.clearCheck()
        ui.btnConfirm.isEnabled = false
    }
}