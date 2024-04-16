package au.edu.utas.kaimol.cricketscore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.adapter.BallContainerAdapter
import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.databinding.ActivityMatchDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchDetail : AppCompatActivity() {
    private lateinit var ui : ActivityMatchDetailBinding
    private val ballAdapter = BallContainerAdapter(mutableListOf())
    private var matchId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(ui.root)
        matchId = intent.getStringExtra("matchId")!!
        ui.txtMatchId.text = matchId
        ui.ballList.adapter = ballAdapter
        ui.ballList.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch{
            val balls = BallDataSource().getByMatchId(matchId)
            balls.sortBy { it.timestamp }
            ballAdapter.updateBalls(balls)
        }
    }
}