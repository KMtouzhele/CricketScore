package au.edu.utas.kaimol.cricketscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import au.edu.utas.kaimol.cricketscore.databinding.ActivityMainBinding
import au.edu.utas.kaimol.cricketscore.view.MatchHistory
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var ui: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.start.setOnClickListener {
            val i = Intent(this, MatchHistory::class.java)
            startActivity(i)
        }
    }
}