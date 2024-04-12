package au.edu.utas.kaimol.cricketscore.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding
import au.edu.utas.kaimol.cricketscore.adapter.ScoringAdapter
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.ResultType
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import au.edu.utas.kaimol.cricketscore.view.fragments.ScoreboardFragment
import au.edu.utas.kaimol.cricketscore.viewModel.FragmentSharedViewModel
import au.edu.utas.kaimol.cricketscore.viewModel.SpinnerViewModel

class ScoringController(private val ui: FragmentScoreboardBinding, private val sharedViewModel: FragmentSharedViewModel, private val spinnerViewModel: SpinnerViewModel) {

    fun addBall(ball: Ball){
        ScoringAdapter().saveBallToFirebase(ball)
        if (wicketType() != null){
            val position = ui.spinnerBatter1.selectedItemPosition
            ScoringAdapter().updateBatterStatus(ui.spinnerBatter1.selectedItem.toString(), position)
        }
        scoreCounter()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMatchResult(matchId: String){
        val totalWickets = sharedViewModel.totalWicketsLost.value!!
        val totalRuns = sharedViewModel.totalRuns.value!!
        ScoringAdapter().updateMatchResult(matchId, totalRuns, totalWickets)
    }

    fun isMatchEnd(): Boolean {
        return ((spinnerViewModel.disabledBatters.value?.size ?: 0) == 4
                || (spinnerViewModel.disabledBowlers.value?.size?: 0) == 4
                || sharedViewModel.overCompleted.value == 5)
    }

    private fun scoreCounter(){
        runsCounter()
        ballsFacedCounter()
        boundaryCounter()
        runsLostCounter()
        ballsDeliveredCounter()
        wicketCounter()

        totalRunsCounter()
        totalWicketsLost()
        totalExtras()
        overCounter()
    }


    private fun runsCounter(){
        var runsBatter1 = ui.runsBatter1.text.toString().toInt()
        val runsAdded = getRuns()
        runsBatter1 += runsAdded
        sharedViewModel.runsBatter1.value = runsBatter1
    }

    private fun totalRunsCounter(){
        var totalRuns = sharedViewModel.totalRuns.value!!
        val addedRuns = getRuns()
        totalRuns += addedRuns
        sharedViewModel.totalRuns.value = totalRuns
    }

    private fun totalWicketsLost(){
        var wicketsLost = sharedViewModel.totalWicketsLost.value!!
        if (wicketType() != null){
            wicketsLost++
            sharedViewModel.totalWicketsLost.value = wicketsLost
        }
    }
    private fun totalExtras(){
        var totalExtras = sharedViewModel.totalExtras.value!!
        if (extraType() != null){
            totalExtras++
            sharedViewModel.totalExtras.value = totalExtras
        }
    }

    private fun overCounter(){
        var overCompleted = sharedViewModel.overCompleted.value!!
        var ballsDelivered = sharedViewModel.ballsDeliveredInOver.value!!
        if (isBallDelivered()){
            ballsDelivered++
            while (ballsDelivered == 6){
                overCompleted++
                ballsDelivered = 0
            }
            sharedViewModel.overCompleted.value = overCompleted
            sharedViewModel.ballsDeliveredInOver.value = ballsDelivered
        }
    }

    private fun ballsFacedCounter(){
        var ballsFaced = ui.ballsFacedBatter1.text.toString().toInt()
        when (isBallDelivered()){
            true -> {
                ballsFaced++
                //ui.ballsFacedBatter1.text = ballsFaced.toString()
                sharedViewModel.ballsFacedBatter1.value = ballsFaced
            }
            false ->{
                //ui.ballsFacedBatter1.text = ballsFaced.toString()
                sharedViewModel.ballsFacedBatter1.value = ballsFaced
            }
        }
        sharedViewModel.ballsFacedBatter2.value = ui.ballsFacedBatter2.text.toString().toInt()
    }

    private fun boundaryCounter(){
        var boundary4 = ui.fourSBatter1.text.toString().toInt()
        var boundary6 = ui.sixSBatter1.text.toString().toInt()
        when(getBoundaryRuns()){
            4 -> {
                boundary4++
                sharedViewModel.fourSBatter1.value = boundary4
            }
            6 -> {
                boundary6++
                sharedViewModel.sixesBatter1.value = boundary6
            }
        }
        sharedViewModel.fourSBatter2.value = ui.fourSBatter2.text.toString().toInt()
        sharedViewModel.sixesBatter2.value = ui.sixSBatter2.text.toString().toInt()
    }

    private fun runsLostCounter(){
        var runsLost = ui.runsLostBowler.text.toString().toInt()
        val runsLostAdded = getRuns()
        runsLost += runsLostAdded
        sharedViewModel.runsLost.value = runsLost
    }

    private fun ballsDeliveredCounter(){
        var ballsDelivered = ui.ballsDeliveredBowler.text.toString().toInt()
        if (isBallDelivered()){
            ballsDelivered++
            sharedViewModel.ballsDelivered.value = ballsDelivered
        }
    }

    private fun wicketCounter(){
        var wickets = ui.totalWicketBowler.text.toString().toInt()
        if (wicketType() != null){
            wickets++
            sharedViewModel.totalWickets.value = wickets
        }
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

    private fun getBoundaryRuns(): Int {
        val runs = when(ui.chipBoundaries.checkedChipId){
            ui.chip4s.id -> { 4 }
            ui.chip6s.id -> { 6 }
            else -> { 0 }
        }
        Log.d("BOUNDARIES", "Boundary runs: $runs" + " " + "Boundary chip id: ${ui.chipBoundaries.checkedChipId}")
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

    fun wicketType(): ResultType? {
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

    fun updateOutPlayerScore(playerName: String, teamType: TeamType) {
        val player = Player()
        when (teamType) {
            TeamType.BATTING -> {
                player.runs = sharedViewModel.runsBatter1.value!!
                player.ballsFaced = sharedViewModel.ballsFacedBatter1.value!!
            }

            TeamType.BOWLING -> {
                player.runsLost = sharedViewModel.runsLost.value!!
                player.ballsDelivered = sharedViewModel.ballsDelivered.value!!
                player.totalWickets = sharedViewModel.totalWickets.value!!
            }
        }
        PlayerDataSource().updateScores(player)
    }

}