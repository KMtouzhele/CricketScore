package au.edu.utas.kaimol.cricketscore.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.adapter.PlayerContainerAdapter
import au.edu.utas.kaimol.cricketscore.controller.SetupController
import au.edu.utas.kaimol.cricketscore.controller.TeamSetupController
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBattingTeamSetupBinding
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType

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
            if(!SetupController().teamSetupValidation(ui)){

                val batters = TeamSetupController().getBatters(ui)
                val battingTeam = Team(
                    name = ui.txtBattingTeamName.text.toString(),
                    teamType = TeamType.BATTING,
                    teamPlayers = batters
                )
                val i = Intent(this, BowlingTeamSetup::class.java)
                i.putExtra("battingTeam", battingTeam)
                startActivity(i)
                Log.d("PutExtra", "Team&Players added to intent.")
            } else {
                Log.d("Invalid", "Text fields are empty. Validation failed.")
            }
        }

        ui.btnCancel.setOnClickListener {
            finish()
        }
    }
}