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
import au.edu.utas.kaimol.cricketscore.entity.Match
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.Team
import au.edu.utas.kaimol.cricketscore.entity.TeamType
import au.edu.utas.kaimol.cricketscore.validator.EmptySetupValidator
import java.time.LocalDateTime

private val playerIndexes = mutableListOf(1, 2, 3, 4, 5)
class BowlingTeamSetup : AppCompatActivity() {
    private lateinit var ui : ActivityBowlingTeamSetupBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityBowlingTeamSetupBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val batter1Name = intent.getStringExtra("batter1")
        val batter2Name = intent.getStringExtra("batter2")
        val batter3Name = intent.getStringExtra("batter3")
        val batter4Name = intent.getStringExtra("batter4")
        val batter5Name = intent.getStringExtra("batter5")

        ui.bowlersInfoList.adapter = PlayerContainerAdapter(playerIndexes)
        ui.bowlersInfoList.layoutManager = LinearLayoutManager(this)
        ui.btnBack.setOnClickListener {
            finish()
        }
        ui.btnMatchStarts.setOnClickListener {
            if(!EmptySetupValidator().teamSetupValidation(ui)){
                val teamName = ui.txtBowlingTeamName.text.toString()
                val bowlers = TeamSetupController().getBowlersFromView(ui)
                val team = Team(name = teamName, teamType = TeamType.BOWLING)

                TeamSetupController().saveTeam(team)

                TeamSetupController().savePlayers(bowlers, team)
                val bowlingTeamId = team.id
                val battingTeamId = intent.getStringExtra("battingTeamId")
                val match = Match(battingTeam = battingTeamId, bowlingTeam = bowlingTeamId, timeStart = LocalDateTime.now())
                TeamSetupController().saveMatch(match)
                val matchId = match.id

                val i = Intent(this, Scoring::class.java)
                i.putExtra("matchId", matchId)
                i.putExtra("battingTeamId", battingTeamId)
                i.putExtra("bowlingTeamId", bowlingTeamId)
                i.putExtra("batter1", batter1Name)
                i.putExtra("batter2", batter2Name)
                i.putExtra("batter3", batter3Name)
                i.putExtra("batter4", batter4Name)
                i.putExtra("batter5", batter5Name)
                for (index in 0 until bowlers.size){
                    i.putExtra("bowler${index + 1}", bowlers[index].name)
                }
                startActivity(i)

            } else{
                Log.d("Invalid", "Text fields are empty. Validation failed.")
            }


        }
    }
}