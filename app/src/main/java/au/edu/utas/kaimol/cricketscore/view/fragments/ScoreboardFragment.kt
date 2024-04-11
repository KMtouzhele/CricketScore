package au.edu.utas.kaimol.cricketscore.view.fragments

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import au.edu.utas.kaimol.cricketscore.adapter.ScoringAdapter
import au.edu.utas.kaimol.cricketscore.adapter.SpinnerAdapter
import au.edu.utas.kaimol.cricketscore.controller.ScoringController
import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.validator.EmptyScoringValidator
import au.edu.utas.kaimol.cricketscore.view.MatchHistory
import au.edu.utas.kaimol.cricketscore.viewModel.FragmentSharedViewModel
import au.edu.utas.kaimol.cricketscore.viewModel.SpinnerViewModel
import com.google.android.material.chip.ChipGroup
import com.google.firebase.Timestamp
import java.time.LocalDateTime

class ScoreboardFragment : Fragment() {
    private lateinit var batterAdapter1 : SpinnerAdapter<String>
    private lateinit var batterAdapter2 : SpinnerAdapter<String>
    private lateinit var bowlerAdapter : SpinnerAdapter<String>
    private lateinit var ui : FragmentScoreboardBinding
    private var prompt: String = "Select"
    private val sharedViewModel : FragmentSharedViewModel by activityViewModels()
    private val spinnerViewModel: SpinnerViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentScoreboardBinding.inflate(inflater, container, false)
        return ui.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val scoringController = ScoringController(ui, sharedViewModel, spinnerViewModel)
        val battingTeamName = activity?.intent?.getStringExtra("battingTeamName")
        val bowlingTeamName = activity?.intent?.getStringExtra("bowlingTeamName")
        val batterNameArray = mutableListOf<String>()
        val bowlerNameArray = mutableListOf<String>()

        for (i in 1..5){
            val batterName = activity?.intent?.getStringExtra("batter$i")
            batterNameArray.add(batterName.toString())
        }
        batterNameArray.add(0,prompt)

        val batterNameArray2 = batterNameArray.toMutableList()

        for(i in 1..5){
            val bowlerName = activity?.intent?.getStringExtra("bowler$i")
            bowlerNameArray.add(bowlerName.toString())
        }
        bowlerNameArray.add(0,prompt)

        initSpinner(batterNameArray, batterNameArray2, bowlerNameArray)
        initViewModelObservations()

        val validator = EmptyScoringValidator(ui)

        setChipGroupListener(ui.chipRuns, validator)
        setChipGroupListener(ui.chipBoundaries, validator)
        setChipGroupListener(ui.chipWicket, validator)
        setChipGroupListener(ui.chipExtras, validator)

        setSpinnerListener(ui.spinnerBatter1, validator)
        setSpinnerListener(ui.spinnerBatter2, validator)
        setSpinnerListener(ui.spinnerBowler, validator)

        ui.btnConfirm.setOnClickListener {
            if (battingTeamName != null && bowlingTeamName != null) {
                val ball = Ball()
                ball.matchId = "$battingTeamName vs $bowlingTeamName"
                ball.ballsDelivered = scoringController.isBallDelivered()
                ball.currentBatter = ui.spinnerBatter1.selectedItem.toString()
                ball.nonBatter = ui.spinnerBatter2.selectedItem.toString()
                ball.bowler = ui.spinnerBowler.selectedItem.toString()
                ball.runs = scoringController.getRuns()
                ball.result = scoringController.getType()
                ball.timestamp = Timestamp.now()

                scoringController.addBall(ball)
                if(ball.runs % 2 == 1 && sharedViewModel.ballsDeliveredInOver.value!! < 5 ){
                    swapSpinner()
                    swapBatters()
                }

                if(scoringController.wicketType() != null){
                    refreshBatterSpinner()
                    initCurrentBatter()
                }
                overCompleted()
                batterAdapter1.notifyDataSetChanged()
                batterAdapter2.notifyDataSetChanged()
                bowlerAdapter.notifyDataSetChanged()
                refreshChipSelection()

                scoringController.updateMatchResult(ball.matchId!!)

                if(scoringController.isMatchEnd()){
                    createEndMatchDialog()
                }
            }
        }
    }

    private fun createEndMatchDialog() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Match Ended")
        builder.setMessage("Match has ended. You can check the match in Match History")
        builder.setPositiveButton("OK") { _, _ ->
            val i = Intent(activity, MatchHistory::class.java)
            startActivity(i)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
    private fun refreshBatterSpinner(){
        val spinner1Selected = ui.spinnerBatter1.selectedItemPosition
        spinnerViewModel.disableBatter(spinner1Selected)
        ui.spinnerBatter1.setSelection(0)
        spinnerViewModel.spinner1SelectedPosition.value = 0
    }
    private fun refreshBowlerSpinner(){
        val spinnerSelected = ui.spinnerBowler.selectedItemPosition
        spinnerViewModel.disableBowler(spinnerSelected)
        ui.spinnerBowler.setSelection(0)
        spinnerViewModel.spinner3SelectedPosition.value = 0
    }
    private fun swapSpinner(){
        val spinner1Selected = ui.spinnerBatter1.selectedItemPosition
        val spinner2Selected = ui.spinnerBatter2.selectedItemPosition
        spinnerViewModel.spinner1SelectedPosition.value = spinner2Selected
        spinnerViewModel.spinner2SelectedPosition.value = spinner1Selected
    }
    private fun swapBatters(){
        try {
            //Swap runs
            val runs1: Int = ui.runsBatter1.text.toString().toInt()
            sharedViewModel.runsBatter1.value = ui.runsBatter2.text.toString().toInt()
            sharedViewModel.runsBatter2.value = runs1

            //Swap balls faced
            val ballsFaced1 = ui.ballsFacedBatter1.text.toString().toInt()
            sharedViewModel.ballsFacedBatter1.value = sharedViewModel.ballsFacedBatter2.value
            sharedViewModel.ballsFacedBatter2.value = ballsFaced1

            //Swap 4s
            val fourS1 = ui.fourSBatter1.text.toString().toInt()
            sharedViewModel.fourSBatter1.value = sharedViewModel.fourSBatter2.value
            sharedViewModel.fourSBatter2.value = fourS1

            //Swap 6s
            val sixS1 = ui.sixSBatter1.text.toString().toInt()
            sharedViewModel.sixesBatter1.value = sharedViewModel.sixesBatter2.value
            sharedViewModel.sixesBatter2.value = sixS1
        } catch(e: Exception){
            Log.e("ScoreboardFragment", "Error swapping batters", e)
        }
    }
    private fun initSpinner(batters1: MutableList<String>, batters2: MutableList<String>, bowlers: MutableList<String>){
        batterAdapter1 = SpinnerAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            batters1, spinnerViewModel, true)
        batterAdapter2 = SpinnerAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            batters2, spinnerViewModel, true)
        bowlerAdapter = SpinnerAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            bowlers, spinnerViewModel, false)
        batterAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        batterAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        bowlerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        ui.spinnerBatter1.adapter = batterAdapter1
        ui.spinnerBatter2.adapter = batterAdapter2
        ui.spinnerBowler.adapter = bowlerAdapter
    }
    private fun refreshChipSelection(){
        ui.chipRuns.clearCheck()
        ui.chipBoundaries.clearCheck()
        ui.chipWicket.clearCheck()
        ui.chipExtras.clearCheck()
        ui.btnConfirm.isEnabled = false
    }
    private fun initCurrentBatter(){
        sharedViewModel.runsBatter1.value = 0
        sharedViewModel.ballsFacedBatter1.value = 0
        sharedViewModel.fourSBatter1.value = 0
        sharedViewModel.sixesBatter1.value = 0
    }
    private fun overCompleted(){
        if(ui.ballsDeliveredBowler.text.toString().toInt() == 6){
            sharedViewModel.ballsDelivered.value = 0
            sharedViewModel.runsLost.value = 0
            sharedViewModel.totalWickets.value = 0
            swapSpinner()
            swapBatters()
            initBowler()
            refreshBowlerSpinner()
        }
    }
    private fun initBowler(){
        sharedViewModel.runsLost.value = 0
        sharedViewModel.ballsDelivered.value = 0
        sharedViewModel.totalWickets.value = 0
    }
    private fun setSpinnerListener(spinner: Spinner, validator: EmptyScoringValidator){
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                validator.buttonStatusUpdate()
                when(spinner){
                    ui.spinnerBatter1 ->{
                        spinnerViewModel.spinner1SelectedPosition.value = position
                        spinnerViewModel.selectedBatter1.value = spinner.selectedItem.toString()
                    }
                    ui.spinnerBatter2 -> {
                        spinnerViewModel.spinner2SelectedPosition.value = position
                        spinnerViewModel.selectedBatter2.value = spinner.selectedItem.toString()
                    }
                    ui.spinnerBowler -> {
                        spinnerViewModel.spinner3SelectedPosition.value = position
                        spinnerViewModel.selectedBowler.value = spinner.selectedItem.toString()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                validator.buttonStatusUpdate()
            }
        }

    }
    private fun setChipGroupListener(chip: ChipGroup, validator: EmptyScoringValidator){
        chip.setOnCheckedStateChangeListener() { _, _ ->
            validator.buttonStatusUpdate()
        }
    }

    private fun initViewModelObservations(){
        val battersName1OB = Observer<MutableList<String>> {
            batterAdapter1.clear()
            batterAdapter1.addAll(it)
            batterAdapter1.notifyDataSetChanged()
        }
        spinnerViewModel.battersName.observe(viewLifecycleOwner, battersName1OB)

        val battersName2OB = Observer<MutableList<String>> {
            batterAdapter2.clear()
            batterAdapter2.addAll(it)
            batterAdapter2.notifyDataSetChanged()
        }
        spinnerViewModel.battersName.observe(viewLifecycleOwner, battersName2OB)

        val bowlersNameOB = Observer<MutableList<String>> {
            bowlerAdapter.clear()
            bowlerAdapter.addAll(it)
            bowlerAdapter.notifyDataSetChanged()
        }
        spinnerViewModel.bowlersName.observe(viewLifecycleOwner, bowlersNameOB)

        val spinnerBatter1OB = Observer<Int> { batter1Position ->
            ui.spinnerBatter1.setSelection(batter1Position)
        }
        spinnerViewModel.spinner1SelectedPosition.observe(viewLifecycleOwner, spinnerBatter1OB)

        val spinnerBatter2OB = Observer<Int> { batter2Position ->
            ui.spinnerBatter2.setSelection(batter2Position)
        }
        spinnerViewModel.spinner2SelectedPosition.observe(viewLifecycleOwner, spinnerBatter2OB)

        val spinnerBowlerOB = Observer<Int> { bowlerPosition ->
            ui.spinnerBowler.setSelection(bowlerPosition)
        }
        spinnerViewModel.spinner3SelectedPosition.observe(viewLifecycleOwner, spinnerBowlerOB)

        val runsBatter1OB = Observer<Int> { runsBatter1 ->
            ui.runsBatter1.text = runsBatter1.toString()
        }
        sharedViewModel.runsBatter1.observe(viewLifecycleOwner, runsBatter1OB)

        val runsBatter2OB = Observer<Int> { runsBatter2 ->
            ui.runsBatter2.text = runsBatter2.toString()
        }
        sharedViewModel.runsBatter2.observe(viewLifecycleOwner, runsBatter2OB)

        val ballsFacedBatter1OB = Observer<Int> { ballsFacedBatter1 ->
            ui.ballsFacedBatter1.text = ballsFacedBatter1.toString()
        }
        sharedViewModel.ballsFacedBatter1.observe(viewLifecycleOwner, ballsFacedBatter1OB)

        val ballsFacedBatter2OB = Observer<Int> { ballsFacedBatter2 ->
            ui.ballsFacedBatter2.text = ballsFacedBatter2.toString()
        }
        sharedViewModel.ballsFacedBatter2.observe(viewLifecycleOwner, ballsFacedBatter2OB)

        val fourSBatter1OB = Observer<Int> { fourSBatter1 ->
            ui.fourSBatter1.text = fourSBatter1.toString()
        }
        sharedViewModel.fourSBatter1.observe(viewLifecycleOwner, fourSBatter1OB)

        val fourSBatter2OB = Observer<Int> { fourSBatter2 ->
            ui.fourSBatter2.text = fourSBatter2.toString()
        }
        sharedViewModel.fourSBatter2.observe(viewLifecycleOwner, fourSBatter2OB)

        val sixesBatter1OB = Observer<Int> { sixesBatter1 ->
            ui.sixSBatter1.text = sixesBatter1.toString()
        }
        sharedViewModel.sixesBatter1.observe(viewLifecycleOwner, sixesBatter1OB)

        val sixesBatter2OB = Observer<Int> { sixesBatter2 ->
            ui.sixSBatter2.text = sixesBatter2.toString()
        }
        sharedViewModel.sixesBatter2.observe(viewLifecycleOwner, sixesBatter2OB)

        val runsLostOB = Observer<Int> { runsLost ->
            ui.runsLostBowler.text = runsLost.toString()
        }
        sharedViewModel.runsLost.observe(viewLifecycleOwner, runsLostOB)

        val ballsDeliveredOB = Observer<Int> { ballsDelivered ->
            ui.ballsDeliveredBowler.text = ballsDelivered.toString()
        }
        sharedViewModel.ballsDelivered.observe(viewLifecycleOwner, ballsDeliveredOB)

        val totalWicketsOB = Observer<Int> { totalWickets ->
            ui.totalWicketBowler.text = totalWickets.toString()
        }
        sharedViewModel.totalWickets.observe(viewLifecycleOwner, totalWicketsOB)

        ui.btnConfirm.isEnabled = false
    }
}