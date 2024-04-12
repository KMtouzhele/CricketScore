package au.edu.utas.kaimol.cricketscore.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.databinding.FragmentSettingsBinding
import au.edu.utas.kaimol.cricketscore.view.MatchHistory

class SettingsFragment : Fragment() {
    private lateinit var ui : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentSettingsBinding.inflate(inflater, container, false)
        return ui.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ui.btnNewMatch.setOnClickListener {
            createNewMatchDialog()
        }
    }

    private fun createNewMatchDialog(){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Creating New Match")
        builder.setMessage("Are you sure you want to start a new match?")
        builder.setPositiveButton("Yes") { _, _ ->
            val i = Intent(activity, MatchHistory::class.java)
            startActivity(i)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

}