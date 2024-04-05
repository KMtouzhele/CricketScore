package au.edu.utas.kaimol.cricketscore.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.databinding.FragmentSummaryBinding
import au.edu.utas.kaimol.cricketscore.view.Scoring
import au.edu.utas.kaimol.cricketscore.viewModel.FragmentSharedViewModel

class SummaryFragment : Fragment() {
    private lateinit var ui : FragmentSummaryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentSummaryBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init team info
        ui.battingTeamName.text = activity?.intent?.getStringExtra("battingTeamName")
        ui.bowlingTeamName.text = activity?.intent?.getStringExtra("bowlingTeamName")

    }
}