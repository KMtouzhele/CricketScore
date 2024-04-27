package au.edu.utas.kaimol.cricketscore.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.adapter.BallContainerAdapter
import au.edu.utas.kaimol.cricketscore.database.BallDataSource
import au.edu.utas.kaimol.cricketscore.databinding.ActivityMatchDetailBinding
import com.google.gson.Gson
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

        ui.btnShareDetails.isEnabled = false

        CoroutineScope(Dispatchers.Main).launch{
            val balls = BallDataSource().getByMatchId(matchId)
            if(balls.isEmpty()){
                ui.promptMatchDetail.text = "No balls found."
            } else {
                ui.promptMatchDetail.text = "${balls.size} balls found."
                balls.sortBy { it.timestamp }
                ballAdapter.updateBalls(balls)

                ui.btnShareDetails.isEnabled = true

                ui.btnShareDetails.setOnClickListener {
                    //share balls in JSON format
                    val gson = Gson()
                    val json =gson.toJson(balls)
                    val i: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, json)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(i, null)
                    startActivity(shareIntent)
                }
            }

        }




    }
}