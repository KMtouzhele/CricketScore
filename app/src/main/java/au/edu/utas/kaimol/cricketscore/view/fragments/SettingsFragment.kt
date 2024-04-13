package au.edu.utas.kaimol.cricketscore.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.adapter.PlayerInfoContainerAdapter
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.databinding.FragmentSettingsBinding
import au.edu.utas.kaimol.cricketscore.view.MatchHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private lateinit var ui : FragmentSettingsBinding
    private var playerInfoAdapter = PlayerInfoContainerAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentSettingsBinding.inflate(inflater, container, false)
        return ui.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val battingTeamName = activity?.intent?.getStringExtra("battingTeamName")
        val bowlingTeamName = activity?.intent?.getStringExtra("bowlingTeamName")
        Log.d("SettingsFragment", "battingTeamName: $battingTeamName")
        Log.d("SettingsFragment", "bowlingTeamName: $bowlingTeamName")
        ui.listPlayer.adapter = playerInfoAdapter
        ui.listPlayer.layoutManager = LinearLayoutManager(context)

        val spinnerOptions : MutableList<String> = mutableListOf("Batters", "Bowlers")
        val spinnerAdapter : ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        ui.spinnerFilter.adapter = spinnerAdapter

        ui.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                val teamName = if (selectedItem == "Batters") battingTeamName else bowlingTeamName
                CoroutineScope(Dispatchers.Main).launch {
                    val players = PlayerDataSource().getPlayerByTeamName(teamName!!)
                    val sortedPlayers = players.sortedBy { it.position } as MutableList
                    playerInfoAdapter.updatePlayers(sortedPlayers)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val players = PlayerDataSource().getPlayerByTeamName(battingTeamName!!)
            playerInfoAdapter.updatePlayers(players)
        }

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