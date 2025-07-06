package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.firebase.realtimedatabase.FirebaseDataBaseRealTimeBridge
import br.com.kmp.demo.demo.ui.components.KmpLogger
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirebaseRealTimeDataBaseViewModel(
    private val firebaseDataBaseRealTimeBridge: FirebaseDataBaseRealTimeBridge,
    kmpLogger: KmpLogger
): BaseViewModel() {

    private val _nodo = MutableStateFlow<String>( "messages")
    val nodo: StateFlow<String> = _nodo

    private val _stateSnapShotFiebaseDataBaseRealTimeByNodo = MutableStateFlow<GetRealTimeDataBaseUiState>(GetRealTimeDataBaseUiState.Loading(step = ""))
    val stateSnapShotFiebaseDataBaseRealTimeByNodo: StateFlow<GetRealTimeDataBaseUiState> = _stateSnapShotFiebaseDataBaseRealTimeByNodo
    var jobGetSnaptShotFirebaseDataBaseRealTime: Job? = SupervisorJob()


    private val _statePutFiebaseDataBaseRealTimeByNodo = MutableStateFlow<PutRealTimeDataBaseUiState>(PutRealTimeDataBaseUiState.Idle)
    val statePutFiebaseDataBaseRealTimeByNodo: StateFlow<PutRealTimeDataBaseUiState> = _statePutFiebaseDataBaseRealTimeByNodo
    var jobPutDataFirebaseDataBaseRealTime: Job? = SupervisorJob()

    init {
        kmpLogger.d("FirebaseRealTimeDataBaseViewModel", "init")
        viewModelScope.launch {
            getSnapShotFirebaseDataBaseRealTime(nodoName = nodo.value)
        }
    }
    
    fun putDataFirebaseDataBaseRealTime(nodoName: String, keyNewNodo: String, valueForNodo: String){
        jobPutDataFirebaseDataBaseRealTime?.cancel()
        jobPutDataFirebaseDataBaseRealTime = viewModelScope.launch {
            try {

                if(nodoName != nodo.value){
                    _nodo.value = nodoName
                    getSnapShotFirebaseDataBaseRealTime(nodoName = nodoName)
                }

                delay(timeMillis = 1000)
                firebaseDataBaseRealTimeBridge.putDataByNodoName(nodoName = nodoName, keyNewNodo = keyNewNodo, valueForNodo = valueForNodo)
                delay(timeMillis = 100)

            }catch (exception: Exception){
                _statePutFiebaseDataBaseRealTimeByNodo.value = PutRealTimeDataBaseUiState.Error(exception.message.toString())
                delay(timeMillis = 100)
            }

        }
    }

    fun getSnapShotFirebaseDataBaseRealTime(nodoName: String) {
        jobGetSnaptShotFirebaseDataBaseRealTime?.cancel()
        jobGetSnaptShotFirebaseDataBaseRealTime = viewModelScope.launch {
            try {
                _stateSnapShotFiebaseDataBaseRealTimeByNodo.value = GetRealTimeDataBaseUiState.Loading()
                delay(timeMillis = 100)
                firebaseDataBaseRealTimeBridge.fetchDataNodo(
                    nodoName = nodoName,
                    onSuccess = { it ->
                        _stateSnapShotFiebaseDataBaseRealTimeByNodo.value = GetRealTimeDataBaseUiState.Success(it)
                    },
                    onError =  { exception ->
                        _stateSnapShotFiebaseDataBaseRealTimeByNodo.value = GetRealTimeDataBaseUiState.Error(exception.message.toString())
                    }
                )
                delay(timeMillis = 100)
                _stateSnapShotFiebaseDataBaseRealTimeByNodo.value = GetRealTimeDataBaseUiState.Loading("")
            }catch (exception: Exception){
                _stateSnapShotFiebaseDataBaseRealTimeByNodo.value = GetRealTimeDataBaseUiState.Error(exception.message.toString())
                delay(timeMillis = 100)
                _stateSnapShotFiebaseDataBaseRealTimeByNodo.value = GetRealTimeDataBaseUiState.Loading("")
            }
        }
    }

    sealed class GetRealTimeDataBaseUiState {
        data class Loading(val step: String = "loading") : GetRealTimeDataBaseUiState()
        data class Error(val message: String = "") : GetRealTimeDataBaseUiState()
        data class Success(val valueSnapShot: String = "") : GetRealTimeDataBaseUiState()
    }

    sealed class PutRealTimeDataBaseUiState {
        object Idle : PutRealTimeDataBaseUiState()
        data class Error(val message: String = "") : PutRealTimeDataBaseUiState()
    }

}
