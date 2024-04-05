package au.edu.utas.kaimol.cricketscore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.databinding.ActivityScoringBinding
import au.edu.utas.kaimol.cricketscore.view.fragments.IndividualFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.ScoreboardFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.SettingsFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.SummaryFragment
import au.edu.utas.kaimol.cricketscore.viewModel.FragmentSharedViewModel

class Scoring : AppCompatActivity() {
    private lateinit var ui : ActivityScoringBinding
    //TODO https://chat.openai.com/share/b6b85c7e-05c0-4018-991e-f46b9aa028e8
    private val sharedViewModel: FragmentSharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityScoringBinding.inflate(layoutInflater)
        setContentView(ui.root)

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