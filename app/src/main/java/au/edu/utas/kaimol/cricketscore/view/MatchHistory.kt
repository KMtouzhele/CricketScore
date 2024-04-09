package au.edu.utas.kaimol.cricketscore.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.adapter.HistoryContainerAdapter
import au.edu.utas.kaimol.cricketscore.controller.MatchHistoryController
import au.edu.utas.kaimol.cricketscore.database.MatchDataSource
import au.edu.utas.kaimol.cricketscore.databinding.ActivityMatchHistoryBinding
import au.edu.utas.kaimol.cricketscore.entity.Match
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
            matchAdapter.updateMatches(matches)
        }


        ui.createMatch.setOnClickListener {
            val i = Intent(this, BattingTeamSetup::class.java)
            startActivity(i)
        }
    }
}