package au.edu.utas.kaimol.cricketscore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.databinding.ActivityScoringBinding
import au.edu.utas.kaimol.cricketscore.view.fragments.IndividualFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.ScoreboardFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.SettingsFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.SummaryFragment
import au.edu.utas.kaimol.cricketscore.viewModel.FragmentSharedViewModel
import au.edu.utas.kaimol.cricketscore.viewModel.SpinnerViewModel

class Scoring : AppCompatActivity() {
    private lateinit var ui : ActivityScoringBinding
    //TODO https://chat.openai.com/share/b6b85c7e-05c0-4018-991e-f46b9aa028e8
    private val sharedViewModel: FragmentSharedViewModel by viewModels()
    private val spinnerViewModel: SpinnerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityScoringBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ViewModelProvider(this)[FragmentSharedViewModel::class.java]
        ViewModelProvider(this)[SpinnerViewModel::class.java]

        val matchId = intent.getStringExtra("matchId")
        Log.d("Scoring", "Match ID: $matchId")

        val batter1Photo = intent.getStringExtra("batterPhoto1")
        val batter2Photo = intent.getStringExtra("batterPhoto2")
        val batter3Photo = intent.getStringExtra("batterPhoto3")
        val batter4Photo = intent.getStringExtra("batterPhoto4")
        val batter5Photo = intent.getStringExtra("batterPhoto5")
        Log.d("Scoring", "Batter1Photo: $batter1Photo")
        Log.d("Scoring", "Batter2Photo: $batter2Photo")
        Log.d("Scoring", "Batter3Photo: $batter3Photo")
        Log.d("Scoring", "Batter4Photo: $batter4Photo")
        Log.d("Scoring", "Batter5Photo: $batter5Photo")

        val bowler6Photo = intent.getStringExtra("bowlerPhoto6")
        val bowler7Photo = intent.getStringExtra("bowlerPhoto7")
        val bowler8Photo = intent.getStringExtra("bowlerPhoto8")
        val bowler9Photo = intent.getStringExtra("bowlerPhoto9")
        val bowler10Photo = intent.getStringExtra("bowlerPhoto10")

        //The initial fragment should be scoreboard
        replaceFragment(ScoreboardFragment())

        //Bottom navigation based on user clicking
        ui.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navScoreboard -> {
                    replaceFragment(ScoreboardFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navSummary -> {
                    replaceFragment(SummaryFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navIndividual -> {
                    replaceFragment(IndividualFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navSettings -> {
                    replaceFragment(SettingsFragment())
                    return@setOnItemSelectedListener true
                }
                else -> false
            }

        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
}