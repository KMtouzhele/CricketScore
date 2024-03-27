package au.edu.utas.kaimol.cricketscore.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import au.edu.utas.kaimol.cricketscore.databinding.ActivityMatchHistoryBinding

class MatchHistory : AppCompatActivity() {
    private lateinit var ui : ActivityMatchHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMatchHistoryBinding.inflate(layoutInflater)
        setContentView(ui.root)
        ui.createMatch.setOnClickListener {
            val i = Intent(this, BattingTeamSetup::class.java)
            startActivity(i)
        }
    }
}