package au.edu.utas.kaimol.cricketscore.controller

import androidx.viewbinding.ViewBinding
import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding

class ScoringController(private val ui: FragmentScoreboardBinding) {

    fun addBall() {
        val currentBatter = ui.spinnerBatter1.selectedItem.toString()
        val nonBatter = ui.spinnerBatter2.selectedItem.toString()
        val bowler = ui.spinnerBowler.selectedItem.toString()
    }
    fun addRun() {
        var runs: Int? = null
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
        addBall()
    }

    fun addBoundaries() {}

    fun addWicket() {}

    fun addExtras() {}

    fun btnStatusUpdates() {
        ui.btnConfirm.isEnabled = !(ui.chipRuns.checkedChipId != -1
                && ui.chipBoundaries.checkedChipId != -1
                && ui.chipWicket.checkedChipId != -1
                && ui.chipExtras.checkedChipId != -1)

    }

    fun getResult(){

    }
}