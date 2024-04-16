package au.edu.utas.kaimol.cricketscore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.kaimol.cricketscore.databinding.BowlerListItemBinding
import au.edu.utas.kaimol.cricketscore.entity.Player

class IndividualBowlerContainerAdapter(private var bowlerList: MutableList<Player>): RecyclerView.Adapter<IndividualBowlerContainerHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IndividualBowlerContainerHolder {
        val ui = BowlerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndividualBowlerContainerHolder(ui)
    }

    override fun getItemCount(): Int {
        return bowlerList.size
    }

    override fun onBindViewHolder(holder: IndividualBowlerContainerHolder, position: Int) {
        val bowler = bowlerList[position]
        holder.ui.txtIndividualBowlerName.text = bowler.name
        holder.ui.txtIndividualRunLost.text = bowler.runsLost.toString()
        holder.ui.txtIndividualTotalWickets.text = bowler.totalWickets.toString()
        holder.ui.txtIndividualBallsDelivered.text = bowler.ballsDelivered.toString()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateBowlers(newBowlers: MutableList<Player>) {
        bowlerList = newBowlers
        notifyDataSetChanged()
    }
}