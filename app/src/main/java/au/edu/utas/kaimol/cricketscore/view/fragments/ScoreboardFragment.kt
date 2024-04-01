package au.edu.utas.kaimol.cricketscore.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import au.edu.utas.kaimol.cricketscore.controller.ScoringController
import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding
import au.edu.utas.kaimol.cricketscore.entity.Ball
import kotlinx.coroutines.DelicateCoroutinesApi

class ScoreboardFragment : Fragment() {
    private lateinit var batterAdapter1 : ArrayAdapter<String>
    private lateinit var batterAdapter2 : ArrayAdapter<String>
    private lateinit var bowlerAdapter : ArrayAdapter<String>
    private lateinit var ui : FragmentScoreboardBinding
    private var prompt: String = "Select"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentScoreboardBinding.inflate(inflater, container, false)
        return ui.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val battingTeamId = activity?.intent?.getStringExtra("battingTeamId")
        val bowlingTeamId = activity?.intent?.getStringExtra("bowlingTeamId")
        val batterNameArray = mutableListOf<String>()
        val bowlerNameArray = mutableListOf<String>()
        val matchId = activity?.intent?.getStringExtra("matchId").toString()
        var currentOver  = 1
        var overBallsDelivered = 0
        var totalBallsDelivered = 0

        for (i in 1..5){
            val batterName = activity?.intent?.getStringExtra("batter$i")
            batterNameArray.add(batterName.toString())
        }
        batterNameArray.add(0,prompt)

        for(i in 1..5){
            val bowlerName = activity?.intent?.getStringExtra("bowler$i")
            bowlerNameArray.add(bowlerName.toString())
        }
        bowlerNameArray.add(0,prompt)

        initializeSpinners(batterNameArray, bowlerNameArray)

        ui.btnConfirm.isEnabled = false


        ui.chipRuns.setOnCheckedStateChangeListener() { _, _ ->
            ScoringController(ui).btnStatusUpdates()
        }
        ui.chipBoundaries.setOnCheckedStateChangeListener() { _, _ ->
            ScoringController(ui).btnStatusUpdates()
        }
        ui.chipWicket.setOnCheckedStateChangeListener() { _, _ ->
            ScoringController(ui).btnStatusUpdates()
        }
        ui.chipExtras.setOnCheckedStateChangeListener() { _, _ ->
            ScoringController(ui).btnStatusUpdates()
        }
        ui.btnConfirm.setOnClickListener {
            if (battingTeamId != null && bowlingTeamId != null) {
                val ball = Ball()
                ball.matchId = matchId
                ball.ballsDelivered = ScoringController(ui).isBallDelivered()
                ball.currentBatter = ui.spinnerBatter1.selectedItem.toString()
                ball.nonBatter = ui.spinnerBatter2.selectedItem.toString()
                ball.bowler = ui.spinnerBowler.selectedItem.toString()
                ball.runs = ScoringController(ui).getRuns()
                ball.result = ScoringController(ui).getType()
                ScoringController(ui).addBall(ball, battingTeamId, bowlingTeamId)
                refreshSpinner(ui, batterNameArray, bowlerNameArray)
                if(ball.ballsDelivered!!){
                    overBallsDelivered++
                    totalBallsDelivered++
                }
                if(overBallsDelivered == 6){
                    currentOver++
                    overBallsDelivered = 0
                }

            }
        }
    }

    private fun initializeSpinners(batters: MutableList<String>, bowlers: MutableList<String>){

        batterAdapter1 = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            batters)
        batterAdapter2 = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            batters)
        bowlerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            bowlers)

        batterAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        batterAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bowlerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        ui.spinnerBatter1.adapter = batterAdapter1
        ui.spinnerBatter2.adapter = batterAdapter2
        ui.spinnerBowler.adapter = bowlerAdapter

    }

    //TODO refresh spinner
    private fun refreshSpinner(ui: FragmentScoreboardBinding, batters: MutableList<String>, bowlers: MutableList<String>){
        val spinner1Selected = ui.spinnerBatter1.selectedItemPosition
        val spinner2Selected = ui.spinnerBatter2.selectedItemPosition
        if(ui.chipWicket.checkedChipId != -1){
            initializeSpinners(batters, bowlers)
            ui.spinnerBatter1.setSelection(0)
        }
    }
}