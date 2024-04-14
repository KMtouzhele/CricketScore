package au.edu.utas.kaimol.cricketscore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.kaimol.cricketscore.database.PlayerDataSource
import au.edu.utas.kaimol.cricketscore.databinding.PlayerInfoListItemBinding
import au.edu.utas.kaimol.cricketscore.entity.Player
import au.edu.utas.kaimol.cricketscore.entity.PlayerStatus

class PlayerInfoContainerAdapter(private var players: MutableList<Player>) : RecyclerView.Adapter<PlayerInfoContainerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerInfoContainerHolder {
        val ui = PlayerInfoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerInfoContainerHolder(ui)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerInfoContainerHolder, position: Int) {
        val player = players[position]
        holder.ui.txtInfoPlayerName.text = player.name
        holder.ui.txtInfoTeamType.text = player.teamName
        holder.ui.btnEditPlayerInfo.isGone = !canBeEdited(player)
        holder.ui.editTxtPlayerName.isGone = true
        holder.ui.btnSave.isGone = true
        holder.ui.btnEditPlayerInfo.setOnClickListener{
            holder.ui.editTxtPlayerName.setText(player.name)
            holder.ui.editTxtPlayerName.isGone = false
            holder.ui.txtInfoPlayerName.isGone = true
            holder.ui.btnSave.isGone = false
        }

        holder.ui.btnSave.setOnClickListener{
            val newName = holder.ui.editTxtPlayerName.text.toString()
            player.name = newName
            holder.ui.txtInfoPlayerName.text = newName
            holder.ui.editTxtPlayerName.isGone = true
            holder.ui.txtInfoPlayerName.isGone = false
            holder.ui.btnSave.isGone = true
            PlayerDataSource().updatePlayerName(player)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updatePlayers(newplayers: MutableList<Player>){
        players = newplayers
        notifyDataSetChanged()
    }

    private fun canBeEdited(player: Player): Boolean {
        return when(player.status){
            PlayerStatus.AVAILABLE -> true
            PlayerStatus.PLAYING -> false
            PlayerStatus.DISMISSED -> false
            else -> false
        }

    }
}