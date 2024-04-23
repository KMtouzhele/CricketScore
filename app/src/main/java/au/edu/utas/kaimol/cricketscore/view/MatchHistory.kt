package au.edu.utas.kaimol.cricketscore.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.adapter.HistoryContainerAdapter
import au.edu.utas.kaimol.cricketscore.controller.MatchHistoryController
import au.edu.utas.kaimol.cricketscore.databinding.ActivityMatchHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchHistory : AppCompatActivity() {
    private lateinit var ui : ActivityMatchHistoryBinding
    private val matchAdapter = HistoryContainerAdapter(mutableListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMatchHistoryBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.historyList.adapter = matchAdapter
        ui.historyList.layoutManager = LinearLayoutManager(this)


        CoroutineScope(Dispatchers.Main).launch {
            val matches = MatchHistoryController().getMatches()
            if (matches.isEmpty()){
                ui.promptHistory.text = "No matches found."
            } else {
                matchAdapter.updateMatches(matches)
                val matchCount = matches.size
                ui.promptHistory.text = "$matchCount matches found."
            }

        }


        ui.createMatch.setOnClickListener {
            val i = Intent(this, BattingTeamSetup::class.java)
            startActivity(i)
        }
    }
}