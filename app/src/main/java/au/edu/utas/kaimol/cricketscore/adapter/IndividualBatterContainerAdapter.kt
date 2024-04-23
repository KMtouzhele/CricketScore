package au.edu.utas.kaimol.cricketscore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.kaimol.cricketscore.R
import au.edu.utas.kaimol.cricketscore.databinding.BatterListItemBinding
import au.edu.utas.kaimol.cricketscore.entity.Player

class IndividualBatterContainerAdapter(private var batterList: MutableList<Player>): RecyclerView.Adapter<IndividualBatterContainerHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IndividualBatterContainerHolder {
        val ui = BatterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndividualBatterContainerHolder(ui)
    }

    override fun getItemCount(): Int {
        return batterList.size
    }

    override fun onBindViewHolder(holder: IndividualBatterContainerHolder, position: Int) {
        val batter = batterList[position]
        holder.ui.txtBatterName.text = batter.name
        holder.ui.txtIndividualRuns.text = batter.runs.toString()
        holder.ui.txtIndividualBallsFaced.text = batter.ballsFaced.toString()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateBatters(newBatters: MutableList<Player>) {
        batterList = newBatters
        notifyDataSetChanged()
    }
}