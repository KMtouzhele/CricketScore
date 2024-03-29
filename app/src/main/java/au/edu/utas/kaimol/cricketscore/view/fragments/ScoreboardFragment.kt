package au.edu.utas.kaimol.cricketscore.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import au.edu.utas.kaimol.cricketscore.controller.TeamSetupController
import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding

class ScoreboardFragment : Fragment() {
    private lateinit var batterAdapter1 : ArrayAdapter<String>
    private lateinit var batterAdapter2 : ArrayAdapter<String>
    private lateinit var bowlerAdapter : ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle =arguments
        val matchId = activity?.intent?.getStringExtra("matchId")
        val battingTeamId = activity?.intent?.getStringExtra("battingTeamId")
        val bowlingTeamId = activity?.intent?.getStringExtra("bowlingTeamId")

        val ui = FragmentScoreboardBinding.inflate(inflater, container, false)

        batterAdapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mutableListOf("1","2","3","4"))
        batterAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        batterAdapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mutableListOf("1","2","3","4"))
        batterAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        bowlerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mutableListOf("1","2","3","4"))
        bowlerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        ui.spinnerBatter1.adapter = batterAdapter1
        ui.spinnerBatter2.adapter = batterAdapter2
        ui.spinnerBowler.adapter = bowlerAdapter
        return ui.root
    }
}