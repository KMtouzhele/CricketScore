package au.edu.utas.kaimol.cricketscore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.databinding.ActivityScoringBinding
import au.edu.utas.kaimol.cricketscore.view.fragments.IndividualFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.ScoreboardFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.SettingsFragment
import au.edu.utas.kaimol.cricketscore.view.fragments.SummaryFragment

class Scoring : AppCompatActivity() {
    private lateinit var ui : ActivityScoringBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityScoringBinding.inflate(layoutInflater)
        setContentView(ui.root)

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