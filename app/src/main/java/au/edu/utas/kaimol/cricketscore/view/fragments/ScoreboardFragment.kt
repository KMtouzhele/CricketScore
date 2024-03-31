package au.edu.utas.kaimol.cricketscore.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import au.edu.utas.kaimol.cricketscore.controller.ScoringController
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScoreboardFragment : Fragment() {
    private lateinit var batterAdapter1 : ArrayAdapter<String>
    private lateinit var batterAdapter2 : ArrayAdapter<String>
    private lateinit var bowlerAdapter : ArrayAdapter<String>
    private lateinit var ui : FragmentScoreboardBinding
    private var matchId : String = ""
    private val prompt1 = "Select batter"
    private val prompt2 = "Select bowler"
    private val loading = "Loading..."
    private var placeholderBatters = mutableListOf<String>(prompt1,loading)
    private var placeholderBowlers = mutableListOf<String>(prompt2,loading)

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
        matchId = activity?.intent?.getStringExtra("matchId").toString()
        ui.btnConfirm.isEnabled = false

        GlobalScope.launch {
            val batters = PlayerDataSource().getAvailablePlayersName(battingTeamId!!)
            val bowlers = PlayerDataSource().getAvailablePlayersName(bowlingTeamId!!)
            requireActivity().runOnUiThread {
                initializeSpinners(batters, bowlers)
            }
        }


        ui.chipRuns.setOnCheckedStateChangeListener() { _, _ ->
            ScoringController(ui, matchId).btnStatusUpdates()
        }
        ui.chipBoundaries.setOnCheckedStateChangeListener() { _, _ ->
            ScoringController(ui, matchId).btnStatusUpdates()
        }
        ui.chipWicket.setOnCheckedStateChangeListener() { _, _ ->
            ScoringController(ui, matchId).btnStatusUpdates()
        }
        ui.chipExtras.setOnCheckedStateChangeListener() { _, _ ->
            ScoringController(ui, matchId).btnStatusUpdates()
        }
        ui.btnConfirm.setOnClickListener {
            ScoringController(ui, matchId).addBall()
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
}