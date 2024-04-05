package au.edu.utas.kaimol.cricketscore.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import au.edu.utas.kaimol.cricketscore.viewModel.SpinnerViewModel

//TODO https://chat.openai.com/share/1d123100-0a06-4f08-bc19-3d03c027eb71
class SpinnerAdapter<T> (
    context: Context,
    resource: Int,
    objects: List<T>,
    private val viewModel: SpinnerViewModel,
    private val isBatter: Boolean
): ArrayAdapter<T>(context, resource, objects){
        init {
            if (isBatter) {
                viewModel.disabledBatters.observeForever {
                    notifyDataSetChanged()
                }
            } else {
                viewModel.disabledBowlers.observeForever {
                    notifyDataSetChanged()
                }
            }
        }

    override fun isEnabled(position: Int): Boolean {
        return if (isBatter) {
            !viewModel.disabledBatters.value?.contains(position)!! ?: true
        } else {
            !viewModel.disabledBowlers.value?.contains(position)!! ?: true
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        view.isEnabled = isEnabled(position)
        if (!isEnabled(position)) {
            view.alpha = 0.2f
        } else {
            view.alpha = 1.0f
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        view.isEnabled = isEnabled(position)
        if (!isEnabled(position)) {
            view.alpha = 0.2f
        } else {
            view.alpha = 1.0f
        }
        return view
    }

//    fun disableItem(position: Int) {
//        val disabledSet = viewModel.disabledItems.value ?: HashSet()
//        disabledSet.add(position)
//        viewModel.disabledItems.value = disabledSet
//    }
//
//    fun enableItem(position: Int) {
//        val disabledSet = viewModel.disabledItems.value ?: HashSet()
//        disabledSet.remove(position)
//        viewModel.disabledItems.value = disabledSet
//    }
}