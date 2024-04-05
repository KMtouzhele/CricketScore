package au.edu.utas.kaimol.cricketscore.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentSharedViewModel : ViewModel(){
    val runsBatter1: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val runsBatter2:  MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val ballsFacedBatter1: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val ballsFacedBatter2: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val fourSBatter1: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val fourSBatter2: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val sixesBatter1: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val sixesBatter2: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val runsLost: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val ballsDelivered: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val totalWickets: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    val totalRuns: MutableLiveData<Int> by lazy { MutableLiveData<Int>().apply { value = 0 } }
    val totalWicketsLost : MutableLiveData<Int> by lazy { MutableLiveData<Int>().apply { value = 0 } }
    val overCompleted: MutableLiveData<Int> by lazy { MutableLiveData<Int>().apply { value = 0 } }
    val ballsDeliveredInOver : MutableLiveData<Int> by lazy { MutableLiveData<Int>().apply { value = 0 } }
    val totalExtras: MutableLiveData<Int> by lazy { MutableLiveData<Int>().apply { value = 0 } }
}