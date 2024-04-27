package au.edu.utas.kaimol.cricketscore.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.databinding.FragmentSummaryBinding
import au.edu.utas.kaimol.cricketscore.viewModel.FragmentSharedViewModel
import au.edu.utas.kaimol.cricketscore.viewModel.SpinnerViewModel
import org.json.JSONObject

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
        val batter1Photo = activity?.intent?.getStringExtra("batterPhoto1")
        val batter2Photo = activity?.intent?.getStringExtra("batterPhoto2")
        val batter3Photo = activity?.intent?.getStringExtra("batterPhoto3")
        val batter4Photo = activity?.intent?.getStringExtra("batterPhoto4")
        val batter5Photo = activity?.intent?.getStringExtra("batterPhoto5")
        val bowler6Photo = activity?.intent?.getStringExtra("bowlerPhoto6")
        val bowler7Photo = activity?.intent?.getStringExtra("bowlerPhoto7")
        val bowler8Photo = activity?.intent?.getStringExtra("bowlerPhoto8")
        val bowler9Photo = activity?.intent?.getStringExtra("bowlerPhoto9")
        val bowler10Photo = activity?.intent?.getStringExtra("bowlerPhoto10")


        //init player info
        val batterName1 = spinnerViewModel.selectedBatter1.value
        ui.currentBatter.text = batterName1
        val batterName2 = spinnerViewModel.selectedBatter2.value
        ui.nonBatter.text = batterName2
        val bowlerName = spinnerViewModel.selectedBowler.value
        ui.bowler.text = bowlerName

        ui.imgStriker.setImageResource(R.drawable.avatar0)
        ui.imgNonStriker.setImageResource(R.drawable.avatar0)
        ui.imgBowler.setImageResource(R.drawable.avatar0)

        when(spinnerViewModel.spinner1SelectedPosition.value){
            0 -> ui.imgStriker.setImageResource(R.drawable.avatar0)
            1 -> if(batter1Photo == null){
                ui.imgStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgStriker.setImageBitmap(BitmapFactory.decodeFile(batter1Photo))
            }
            2 -> if(batter2Photo == null){
                ui.imgStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgStriker.setImageBitmap(BitmapFactory.decodeFile(batter2Photo))
            }
            3 -> if(batter3Photo == null){
                ui.imgStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgStriker.setImageBitmap(BitmapFactory.decodeFile(batter3Photo))
            }
            4 -> if(batter4Photo == null){
                ui.imgStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgStriker.setImageBitmap(BitmapFactory.decodeFile(batter4Photo))
            }
            5 -> if(batter5Photo == null){
                ui.imgStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgStriker.setImageBitmap(BitmapFactory.decodeFile(batter5Photo))
            }
        }

        when(spinnerViewModel.spinner2SelectedPosition.value){
            0 -> ui.imgNonStriker.setImageResource(R.drawable.avatar0)
            1 -> if(batter1Photo == null){
                ui.imgNonStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgNonStriker.setImageBitmap(BitmapFactory.decodeFile(batter1Photo))
            }
            2 -> if(batter2Photo == null){
                ui.imgNonStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgNonStriker.setImageBitmap(BitmapFactory.decodeFile(batter2Photo))
            }
            3 -> if(batter3Photo == null){
                ui.imgNonStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgNonStriker.setImageBitmap(BitmapFactory.decodeFile(batter3Photo))
            }
            4 -> if(batter4Photo == null){
                ui.imgNonStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgNonStriker.setImageBitmap(BitmapFactory.decodeFile(batter4Photo))
            }
            5 -> if(batter5Photo == null){
                ui.imgNonStriker.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgNonStriker.setImageBitmap(BitmapFactory.decodeFile(batter5Photo))
            }
        }

        when(spinnerViewModel.spinner3SelectedPosition.value){
            0 -> ui.imgBowler.setImageResource(R.drawable.avatar0)
            1 -> if(bowler6Photo == null){
                ui.imgBowler.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgBowler.setImageBitmap(BitmapFactory.decodeFile(bowler6Photo))
            }
            2 -> if(bowler7Photo == null){
                ui.imgBowler.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgBowler.setImageBitmap(BitmapFactory.decodeFile(bowler7Photo))
            }
            3 -> if(bowler8Photo == null){
                ui.imgBowler.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgBowler.setImageBitmap(BitmapFactory.decodeFile(bowler8Photo))
            }
            4 -> if(bowler9Photo == null){
                ui.imgBowler.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgBowler.setImageBitmap(BitmapFactory.decodeFile(bowler9Photo))
            }
            5 -> if(bowler10Photo == null){
                ui.imgBowler.setImageResource(R.drawable.avatar0)
            } else {
                ui.imgBowler.setImageBitmap(BitmapFactory.decodeFile(bowler10Photo))
            }
        }

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