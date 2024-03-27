package au.edu.utas.kaimol.cricketscore.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.adapter.PlayerContainerAdapter
import au.edu.utas.kaimol.cricketscore.controller.TeamSetupController
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBowlingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.validator.EmptySetupValidator

private val playerIndexes = mutableListOf(1, 2, 3, 4, 5)
class BowlingTeamSetup : AppCompatActivity() {
    private lateinit var ui : ActivityBowlingTeamSetupBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityBowlingTeamSetupBinding.inflate(layoutInflater)
        setContentView(ui.root)
        ui.bowlersInfoList.adapter = PlayerContainerAdapter(playerIndexes)
        ui.bowlersInfoList.layoutManager = LinearLayoutManager(this)
        ui.btnBack.setOnClickListener {
            finish()
        }
        ui.btnMatchStarts.setOnClickListener {
            if(!EmptySetupValidator().teamSetupValidation(ui)){
                val bowlers = TeamSetupController().getBowlers(ui)
                val bowlingTeamName = ui.txtBowlingTeamName.text.toString()
                val bowlingTeam: Team = TeamSetupController().getTeam(ui, bowlingTeamName, bowlers)
                val battingTeam = intent.extras?.getSerializable("battingTeam") as Team

                TeamSetupController().saveMatchSetup(battingTeam, bowlingTeam)

                val i = Intent(this, Scoring::class.java)
                startActivity(i)

            } else{
                Log.d("Invalid", "Text fields are empty. Validation failed.")
            }


        }
    }
}