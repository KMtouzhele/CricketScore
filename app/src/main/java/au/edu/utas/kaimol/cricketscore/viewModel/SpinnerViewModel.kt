package au.edu.utas.kaimol.cricketscore.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO https://chat.openai.com/share/1d123100-0a06-4f08-bc19-3d03c027eb71
class SpinnerViewModel: ViewModel() {
    val battersName : MutableLiveData<MutableList<String>> by lazy { MutableLiveData<MutableList<String>>() }
    val bowlersName : MutableLiveData<MutableList<String>> by lazy { MutableLiveData<MutableList<String>>() }
    val selectedBatter1 : MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "No batter selected" } }
    val selectedBatter2 : MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "No batter selected" } }
    val selectedBowler : MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "No bowler selected" } }
    val spinner1SelectedPosition : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val spinner2SelectedPosition : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val spinner3SelectedPosition : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }


    val disabledBatters = MutableLiveData<HashSet<Int>>()
    val disabledBowlers = MutableLiveData<HashSet<Int>>()

    init{
        disabledBatters.value = HashSet()
        disabledBowlers.value = HashSet()
    }

    fun disableBatter(position: Int) {
        val disabledSet = disabledBatters.value ?: HashSet()
        disabledSet.add(position)
        disabledBatters.value = disabledSet
    }
    fun disableBowler(position: Int) {
        val disabledSet = disabledBowlers.value ?: HashSet()
        disabledSet.add(position)
        disabledBowlers.value = disabledSet
    }

}