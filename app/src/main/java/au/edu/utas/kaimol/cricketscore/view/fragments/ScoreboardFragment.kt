package au.edu.utas.kaimol.cricketscore.view.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import au.edu.utas.kaimol.cricketscore.controller.ScoringController
import au.edu.utas.kaimol.cricketscore.databinding.FragmentScoreboardBinding
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.validator.EmptyScoringValidator
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.DelicateCoroutinesApi
import java.time.LocalDateTime

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

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val battingTeamName = activity?.intent?.getStringExtra("battingTeamName")
        val bowlingTeamName = activity?.intent?.getStringExtra("bowlingTeamName")
        val batterNameArray = mutableListOf<String>()
        val bowlerNameArray = mutableListOf<String>()

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

        initSpinner(batterNameArray, bowlerNameArray)

        ui.btnConfirm.isEnabled = false
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
                ball.matchId = battingTeamName + "vs" + bowlingTeamName
                ball.ballsDelivered = ScoringController(ui).isBallDelivered()
                ball.currentBatter = ui.spinnerBatter1.selectedItem.toString()
                ball.nonBatter = ui.spinnerBatter2.selectedItem.toString()
                ball.bowler = ui.spinnerBowler.selectedItem.toString()
                ball.runs = ScoringController(ui).getRuns()
                ball.result = ScoringController(ui).getType()
                ball.timestamp = LocalDateTime.now()

                ScoringController(ui).addBall(ball)
                if(ball.runs % 2 == 1){
                    swapSpinner(batterNameArray)
                    swapBatters()
                }

                if(ScoringController(ui).wicketType() != null){
                    refreshBatterSpinner(batterNameArray)
                    initCurrentBatter()
                }
                overCompleted(batterNameArray, bowlerNameArray)

                batterAdapter1.notifyDataSetChanged()
                batterAdapter2.notifyDataSetChanged()
                bowlerAdapter.notifyDataSetChanged()


                refreshChipSelection()
            }
        }
    }

    private fun refreshBatterSpinner(batters: MutableList<String>){
        val spinner1Selected = ui.spinnerBatter1.selectedItemPosition
        batters.removeAt(spinner1Selected)
        ui.spinnerBatter1.setSelection(0)
    }

    private fun refreshBowlerSpinner(bowlers: MutableList<String>){
        val spinnerSelected = ui.spinnerBowler.selectedItemPosition
        bowlers.removeAt(spinnerSelected)
        ui.spinnerBowler.setSelection(0)
    }
    private fun swapSpinner(batters: MutableList<String>){
        val spinner1Selected = ui.spinnerBatter1.selectedItemPosition
        val spinner2Selected = ui.spinnerBatter2.selectedItemPosition
        val temp = batters[spinner1Selected]
        batters[spinner1Selected] = batters[spinner2Selected]
        batters[spinner2Selected] = temp
    }
    private fun swapBatters(){
        //Swap runs
        var temp : Int = ui.runsBatter1.text.toString().toInt()
        ui.runsBatter1.text = ui.runsBatter2.text.toString()
        ui.runsBatter2.text = temp.toString()

        //Swap balls faced
        temp = ui.ballsFacedBatter1.text.toString().toInt()
        ui.ballsFacedBatter1.text = ui.ballsFacedBatter2.text.toString()
        ui.ballsFacedBatter2.text = temp.toString()

        //Swap 4s
        temp = ui.fourSBatter1.text.toString().toInt()
        ui.fourSBatter1.text = ui.fourSBatter2.text.toString()
        ui.fourSBatter2.text = temp.toString()

        //Swap 6s
        temp = ui.sixSBatter1.text.toString().toInt()
        ui.sixSBatter1.text = ui.sixSBatter2.text.toString()
        ui.sixSBatter2.text = temp.toString()
    }
    private fun initSpinner(batters: MutableList<String>, bowlers: MutableList<String>){
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

        ui.spinnerBatter1.setSelection(0)
        ui.spinnerBatter2.setSelection(0)
    }
    private fun refreshChipSelection(){
        ui.chipRuns.clearCheck()
        ui.chipBoundaries.clearCheck()
        ui.chipWicket.clearCheck()
        ui.chipExtras.clearCheck()
        ui.btnConfirm.isEnabled = false
    }
    private fun initCurrentBatter(){
        ui.runsBatter1.text = "0"
        ui.ballsFacedBatter1.text = "0"
        ui.fourSBatter1.text = "0"
        ui.sixSBatter1.text = "0"
    }

    private fun overCompleted(batters: MutableList<String>, bowlers: MutableList<String>){
        if(ui.ballsDeliveredBowler.text.toString().toInt() == 6){
            ui.ballsDeliveredBowler.text = "0"
            ui.runsLostBowler.text = "0"
            swapSpinner(batters)
            initBowler()
            refreshBowlerSpinner(bowlers)
        }
    }

    private fun initBowler(){
        ui.runsLostBowler.text = "0"
        ui.ballsDeliveredBowler.text = "0"
        ui.totalWicketBowler.text = "0"
    }

    private fun setSpinnerListener(spinner: Spinner, validator: EmptyScoringValidator){
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                validator.buttonStatusUpdate()
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

}