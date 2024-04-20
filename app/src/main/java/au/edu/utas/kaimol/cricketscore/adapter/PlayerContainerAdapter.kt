package au.edu.utas.kaimol.cricketscore.adapter

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.kaimol.cricketscore.databinding.PlayersInfoListItemBinding
import au.edu.utas.kaimol.cricketscore.view.BattingTeamSetup

class PlayerContainerAdapter(private val playerIndexes: MutableList<Int>)
    : RecyclerView.Adapter<PlayerContainerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerContainerHolder {
        val ui = PlayersInfoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerContainerHolder(ui)
    }

    override fun onBindViewHolder(holder: PlayerContainerHolder, position: Int) {
        val playerContainerIndex = playerIndexes[position]
        holder.ui.txtPlayerIndex.text = playerContainerIndex.toString()
    }

    override fun getItemCount(): Int {
        return playerIndexes.size
    }
}