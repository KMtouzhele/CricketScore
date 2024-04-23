package au.edu.utas.kaimol.cricketscore.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.adapter.IndividualBatterContainerAdapter
import au.edu.utas.kaimol.cricketscore.adapter.IndividualBowlerContainerAdapter
import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.databinding.FragmentIndividualBinding
import au.edu.utas.kaimol.cricketscore.entity.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndividualFragment : Fragment() {
    private lateinit var ui : FragmentIndividualBinding
    private var batterAdapter = IndividualBatterContainerAdapter(mutableListOf())
    private var bowlerAdapter = IndividualBowlerContainerAdapter(mutableListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentIndividualBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val matchId = activity?.intent?.getStringExtra("matchId") ?: ""
        val spinnerOptions : MutableList<String> = mutableListOf("Batters", "Bowlers")
        val spinnerAdapter : ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        ui.spinnerIndivudualFilter.adapter= spinnerAdapter

        ui.spinnerIndivudualFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Batters") {
                    ui.IndividualList.adapter = batterAdapter
                    ui.IndividualList.layoutManager = LinearLayoutManager(context)
                    CoroutineScope(Dispatchers.Main).launch {
                        val runsMap = BallDataSource().getTotalRunsByCurrentBatter(matchId)
                        val ballsFacedMap = BallDataSource().getBallsFacedByCurrentBatter(matchId)
                        val batters = runsMap.map { (batter, runs) ->
                            Player(name = batter,
                                runs = runs,
                                ballsFaced = ballsFacedMap[batter] ?: 0
                            )
                        }.toMutableList()
                        if (batters.isEmpty()){
                            ui.promptIndividual.text = "Match not started yet."
                        } else {
                            ui.promptIndividual.text = "${batters.size} batter(s) have been played."
                            batterAdapter.updateBatters(batters)
                        }
                    }
                } else {
                    ui.IndividualList.adapter = bowlerAdapter
                    ui.IndividualList.layoutManager = LinearLayoutManager(context)
                    CoroutineScope(Dispatchers.Main).launch {
                        val runsLostMap = BallDataSource().getRunsLostByBowler(matchId)
                        val ballsDeliveredMap = BallDataSource().getBallsDeliveredByBowler(matchId)
                        val wicketsMap = BallDataSource().getTotalWicketsByBowler(matchId)
                        val bowlers = runsLostMap.map { (bowler, runsLost) ->
                            Player(
                                name = bowler,
                                runsLost = runsLost,
                                ballsDelivered = ballsDeliveredMap[bowler] ?: 0,
                                totalWickets = wicketsMap[bowler] ?: 0,
                            )
                        }.toMutableList()
                        if (bowlers.isEmpty()){
                            ui.promptIndividual.text = "Match not started yet."
                        } else {
                            ui.promptIndividual.text = "${bowlers.size} bowler(s) have played."
                            bowlerAdapter.updateBowlers(bowlers)
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }


    }

}