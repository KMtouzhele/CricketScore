package au.edu.utas.kaimol.cricketscore.validator

import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding

class EmptyScoringValidator (private val ui: FragmentScoreboardBinding) {

    fun buttonStatusUpdate() {
        ui.btnConfirm.isEnabled = isResultSelected() && isPlayerSelected()

    }

    private fun isResultSelected(): Boolean{
        val runsSelected = ui.chipRuns.checkedChipId
        val boundariesSelected = ui.chipBoundaries.checkedChipId
        val wicketSelected = ui.chipWicket.checkedChipId
        val extrasSelected = ui.chipExtras.checkedChipId
        return !(runsSelected != -1
                && boundariesSelected != -1
                && wicketSelected != -1
                && extrasSelected != -1)
    }

    private fun isPlayerSelected(): Boolean{
        val batter1Selected = ui.spinnerBatter1.selectedItemPosition
        val batter2Selected = ui.spinnerBatter2.selectedItemPosition
        val bowlerSelected = ui.spinnerBowler.selectedItemPosition
        return !(batter1Selected == 0 || batter2Selected == 0 || bowlerSelected == 0)
    }

}