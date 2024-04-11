package au.edu.utas.kaimol.cricketscore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.databinding.ActivityMatchDetailBinding

class MatchDetail : AppCompatActivity() {
    private lateinit var ui : ActivityMatchDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.txtMatchId.text = intent.getStringExtra("matchId")
    }
}