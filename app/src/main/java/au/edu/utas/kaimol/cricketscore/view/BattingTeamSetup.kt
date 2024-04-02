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
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBattingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import au.edu.utas.kaimol.cricketscore.validator.EmptySetupValidator
import kotlinx.coroutines.runBlocking

private val playerIndexes = mutableListOf(1, 2, 3, 4, 5)
class BattingTeamSetup : AppCompatActivity() {
    private lateinit var ui : ActivityBattingTeamSetupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityBattingTeamSetupBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.battersInfoList.adapter = PlayerContainerAdapter(playerIndexes)
        ui.battersInfoList.layoutManager = LinearLayoutManager(this)

        ui.btnNext.setOnClickListener {
            if(!EmptySetupValidator().teamSetupValidation(ui)){
                val teamName = ui.txtBattingTeamName.text.toString()
                val team = Team(name = teamName, teamType = TeamType.BATTING)

                TeamSetupController().saveTeam(team)
                val batters = TeamSetupController().getBattersFromView(ui)

                //save Batters into Firebase
                TeamSetupController().savePlayers(batters, team)
                val i = Intent(this, BowlingTeamSetup::class.java)
                i.putExtra("battingTeamName", team.name)
                for (index in 0 until batters.size){
                    i.putExtra("batter${index + 1}", batters[index].name)
                }
                startActivity(i)
            } else {
                Log.d("Invalid", "Text fields are empty. Validation failed.")
            }
        }

        ui.btnCancel.setOnClickListener {
            finish()
        }
    }





}