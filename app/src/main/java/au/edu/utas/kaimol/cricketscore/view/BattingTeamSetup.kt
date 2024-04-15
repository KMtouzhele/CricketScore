package au.edu.utas.kaimol.cricketscore.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.adapter.PlayerContainerAdapter
import au.edu.utas.kaimol.cricketscore.controller.TeamSetupController
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBattingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import au.edu.utas.kaimol.cricketscore.validator.EmptySetupValidator

private val playerIndexes = mutableListOf(1, 2, 3, 4, 5)
class BattingTeamSetup : AppCompatActivity() {
    private lateinit var ui : ActivityBattingTeamSetupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityBattingTeamSetupBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val teamSetupController = TeamSetupController()
        val teamSetupValidator = EmptySetupValidator()

        ui.battersInfoList.adapter = PlayerContainerAdapter(playerIndexes)
        ui.battersInfoList.layoutManager = LinearLayoutManager(this)

        ui.btnNext.setOnClickListener {
            if(teamSetupValidator.teamSetupValidation(ui)){
                teamSetupController.createValidationToast(
                    this,
                    "Please fill all the fields.",
                )
            } else {
                val teamName = ui.txtBattingTeamName.text.toString()
                val team = Team(name = teamName, teamType = TeamType.BATTING)

                teamSetupController.saveTeam(team)
                val batters = teamSetupController.getBattersFromView(ui)

                //save Batters into Firebase
                teamSetupController.savePlayers(batters, team)
                val i = Intent(this, BowlingTeamSetup::class.java)
                i.putExtra("battingTeamName", team.name)
                for (index in 0 until batters.size){
                    i.putExtra("batter${index + 1}", batters[index].name)
                }
                startActivity(i)
            }
        }
        ui.btnCancel.setOnClickListener {
            finish()
        }
    }
}