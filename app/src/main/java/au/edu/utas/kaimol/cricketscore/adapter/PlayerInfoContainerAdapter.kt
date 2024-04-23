package au.edu.utas.kaimol.cricketscore.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
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

        holder.ui.editTxtPlayerName.isGone = true
        holder.ui.btnSave.isVisible = false
        holder.ui.btnDelete.isVisible = canBeDeleted(player)

        holder.ui.btnEditPlayerInfo.setOnClickListener{
            holder.ui.editTxtPlayerName.setText(player.name)
            holder.ui.editTxtPlayerName.isVisible = true
            holder.ui.txtInfoPlayerName.isVisible = false
            holder.ui.btnSave.isVisible = true
            holder.ui.btnEditPlayerInfo.isVisible = false
        }

        holder.ui.btnSave.setOnClickListener{
            val newName = holder.ui.editTxtPlayerName.text.toString()
            player.name = newName
            holder.ui.txtInfoPlayerName.text = newName
            holder.ui.editTxtPlayerName.isVisible = false
            holder.ui.txtInfoPlayerName.isVisible = true
            holder.ui.btnSave.isVisible = false
            holder.ui.btnEditPlayerInfo.isVisible = true
            PlayerDataSource().updatePlayerName(player)
        }

        holder.ui.btnDelete.setOnClickListener{
            val builder = AlertDialog.Builder(holder.ui.root.context)
            builder.setTitle("About to delete this player")
            builder.setMessage("There will be no way to recover this player. Are you sure?")
            builder.setPositiveButton("Delete") { _, _ ->
                PlayerDataSource().deletePlayer(player)
                updatePlayers(players.filter { it.id != player.id }.toMutableList())
                Log.d("PlayerInfoContainerAdapter", "Player to be deleted: $player")
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updatePlayers(newPlayers: MutableList<Player>){
        players = newPlayers
        notifyDataSetChanged()
    }

    private fun canBeDeleted(player: Player): Boolean {
        return when(player.status){
            PlayerStatus.AVAILABLE -> true
            PlayerStatus.PLAYING -> false
            PlayerStatus.DISMISSED -> false
            else -> false
        }
    }

}