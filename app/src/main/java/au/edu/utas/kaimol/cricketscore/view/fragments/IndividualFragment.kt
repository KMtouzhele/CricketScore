package au.edu.utas.kaimol.cricketscore.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.adapter.IndividualBatterContainerAdapter
import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.databinding.FragmentIndividualBinding
import au.edu.utas.kaimol.cricketscore.entity.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndividualFragment : Fragment() {
    private lateinit var ui : FragmentIndividualBinding
    private var batterAdapter = IndividualBatterContainerAdapter(mutableListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentIndividualBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ui.BatterIndividualList.adapter = batterAdapter
        ui.BatterIndividualList.layoutManager = LinearLayoutManager(context)

        CoroutineScope(Dispatchers.Main).launch {
            val matchId = activity?.intent?.getStringExtra("matchId") ?: ""
            val runsMap = BallDataSource().getTotalRunsByCurrentBatter(matchId)
            val ballsFacedMap = BallDataSource().getBallsFacedByCurrentBatter(matchId)
            val batters = runsMap.map { (batter, runs) ->
                Player(name = batter, runs = runs, ballsFaced = ballsFacedMap[batter] ?: 0)
            }.toMutableList()
            for (batter in batters) {
                Log.d("IndividualFragment", "Batter: ${batter.name}, Runs: ${batter.runs}, Balls Faced: ${batter.ballsFaced}")
            }
            batterAdapter.updateBatters(batters)
        }
    }

}