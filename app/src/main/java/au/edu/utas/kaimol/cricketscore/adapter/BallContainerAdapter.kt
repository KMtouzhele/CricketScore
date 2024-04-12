package au.edu.utas.kaimol.cricketscore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.kaimol.cricketscore.databinding.BallListBinding
import au.edu.utas.kaimol.cricketscore.entity.Ball
import au.edu.utas.kaimol.cricketscore.entity.ResultType
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

class BallContainerAdapter (private var balls: MutableList<Ball>): RecyclerView.Adapter<BallContainerHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallContainerHolder {
        val ui = BallListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BallContainerHolder(ui)
    }

    override fun getItemCount(): Int {
        return balls.size
    }

    override fun onBindViewHolder(holder: BallContainerHolder, position: Int) {
        val ball = balls[position]
        holder.ui.txtBallListBatter.text = ball.currentBatter
        holder.ui.txtBallListBowler.text = ball.bowler
        holder.ui.txtBallListTime.text = timestampToDateTime(ball.timestamp!!)
        holder.ui.txtBallListResult.text = resultFormatter(ball.result!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateBalls(newBalls: MutableList<Ball>) {
        balls = newBalls
        notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    fun timestampToDateTime(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }

    private fun resultFormatter(result: ResultType): String {
        val resultDisplay = when(result){
            ResultType.RUNS -> "Runs"
            ResultType.BOWLED -> "Bowled"
            ResultType.CATCH -> "Caught"
            ResultType.CATCH_BOWLED -> "Caught & Bowled"
            ResultType.HIT_WICKET -> "Hit Wicket"
            ResultType.STUMPING -> "Stumping"
            ResultType.RUN_OUT -> "Run Out"
            ResultType.LBW -> "LBW"
            ResultType.NO_BALL -> "No Ball"
            ResultType.WIDE -> "Wide"
            ResultType.BYE -> "Bye"
            ResultType.LEG_BYES -> "Leg Bye"
            ResultType.DEAD_BALL -> "Dead Ball"
            ResultType.BOUNDARIES -> "Boundary"
        }
        return resultDisplay
    }
}