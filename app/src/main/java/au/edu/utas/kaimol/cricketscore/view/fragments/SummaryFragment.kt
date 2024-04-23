package au.edu.utas.kaimol.cricketscore.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import au.edu.utas.kaimol.cricketscore.databinding.FragmentSummaryBinding
import au.edu.utas.kaimol.cricketscore.viewModel.FragmentSharedViewModel
import au.edu.utas.kaimol.cricketscore.viewModel.SpinnerViewModel

class SummaryFragment : Fragment() {
    private lateinit var ui : FragmentSummaryBinding
    private val sharedViewModel : FragmentSharedViewModel by activityViewModels()
    private val spinnerViewModel: SpinnerViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentSummaryBinding.inflate(inflater, container, false)
        return ui.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init team info
        ui.battingTeamName.text = activity?.intent?.getStringExtra("battingTeamName")
        ui.bowlingTeamName.text = activity?.intent?.getStringExtra("bowlingTeamName")

        //init player info
        val batterName1 = spinnerViewModel.selectedBatter1.value
        ui.currentBatter.text = batterName1
        val batterName2 = spinnerViewModel.selectedBatter2.value
        ui.nonBatter.text = batterName2
        val bowlerName = spinnerViewModel.selectedBowler.value
        ui.bowler.text = bowlerName

        //batting score
        val totalRuns = sharedViewModel.totalRuns.value.toString()
        val totalWickets = sharedViewModel.totalWicketsLost.value.toString()
        ui.txtBattingSocre.text = "$totalWickets/$totalRuns"

        //current over
        val overCompleted = sharedViewModel.overCompleted.value.toString()
        val ballsDeliveredInOver = sharedViewModel.ballsDeliveredInOver.value.toString()
        ui.txtCurrentOver.text = "$overCompleted.$ballsDeliveredInOver"

        //extras
        ui.txtExtraValue.text = sharedViewModel.totalExtras.value.toString()

        //run rate
        val totalBalls = 5 * overCompleted.toInt() + ballsDeliveredInOver.toInt()
        val runRate = totalRuns.toInt() / (totalBalls / (overCompleted.toInt() + 1 ) ).toDouble()
        ui.txtRunRateValue.text = runRate.toString().format("%.2f")

        //Share button
        val sharedText : String = "Sharing a match progress with you \n" +
                "Batting Team: ${ui.battingTeamName.text}\n" +
                "Bowling Team: ${ui.bowlingTeamName.text}\n" +
                "Current Batter: ${ui.currentBatter.text}\n" +
                "Non-Striker: ${ui.nonBatter.text}\n" +
                "Bowler: ${ui.bowler.text}\n" +
                "Batting Score: ${ui.txtBattingSocre.text}\n" +
                "Current Over: ${ui.txtCurrentOver.text}\n" +
                "Extras: ${ui.txtExtraValue.text}\n" +
                "Run Rate: ${ui.txtRunRateValue.text}"
        ui.btnShare.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, sharedText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "Share via..."))
        }
    }


}