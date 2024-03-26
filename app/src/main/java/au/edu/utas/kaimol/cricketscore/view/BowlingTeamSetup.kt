package au.edu.utas.kaimol.cricketscore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.adapter.PlayerContainerAdapter
import au.edu.utas.kaimol.cricketscore.controller.TeamSetupController
import au.edu.utas.kaimol.cricketscore.databinding.ActivityBowlingTeamSetupBinding

private val playerIndexes = mutableListOf(1, 2, 3, 4, 5)
class BowlingTeamSetup : AppCompatActivity() {
    private lateinit var ui : ActivityBowlingTeamSetupBinding
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
            val bowlers = TeamSetupController().getBowlers(ui)

            TeamSetupController().savePlayers(bowlers)
        }
    }
}