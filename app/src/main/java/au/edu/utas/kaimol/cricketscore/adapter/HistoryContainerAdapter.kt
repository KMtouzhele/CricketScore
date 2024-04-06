package au.edu.utas.kaimol.cricketscore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.kaimol.cricketscore.databinding.MatchHistoryListBinding
import au.edu.utas.kaimol.cricketscore.entity.Match

class HistoryContainerAdapter(private var matches: MutableList<Match>) : RecyclerView.Adapter<HistoryContainerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryContainerHolder {
        val ui = MatchHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryContainerHolder(ui)
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryContainerHolder, position: Int) {
        val match = matches[position]
        holder.ui.txtHistoryMatchName.text = match.matchId
        holder.ui.txtTotalRuns.text = "Runs: " + match.totalRuns.toString()
        holder.ui.txtTotalWickets.text = "Wickets: " + match.totalWickets.toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMatches(newMatches: MutableList<Match>) {
        matches = newMatches
        notifyDataSetChanged()
    }
}