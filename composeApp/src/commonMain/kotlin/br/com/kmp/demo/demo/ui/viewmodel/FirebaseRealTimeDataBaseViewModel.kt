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

    private val _stateSnapShotFiebaseDataBaseRealtimeByNodo = MutableStateFlow<GetRealTimeDataBaseUiState>(GetRealTimeDataBaseUiState.Loading(step = "loading"))
    val stateSnapShotFiebaseDataBaseRealtimeByNodo: StateFlow<GetRealTimeDataBaseUiState> = _stateSnapShotFiebaseDataBaseRealtimeByNodo
    var jobRemoteConfig: Job? = SupervisorJob()

    init {
        kmpLogger.d("FirebaseRealTimeDataBaseViewModel", "init")
        viewModelScope.launch {
            getSnapShotFiebaseDataBaseRealtime()
        }
    }

    suspend fun getSnapShotFiebaseDataBaseRealtime(){
        jobRemoteConfig?.cancel()
        jobRemoteConfig = viewModelScope.launch {
            try {
                _stateSnapShotFiebaseDataBaseRealtimeByNodo.value = GetRealTimeDataBaseUiState.Loading()
                delay(timeMillis = 100)
                firebaseDataBaseRealTimeBridge.fetchDataNodo(
                    nodoName = nodo.value,
                    onSuccess = { it ->
                        KmpLogger.d("getSnapShotFiebaseDataBaseRealtime", "Value is: $it")
                        _stateSnapShotFiebaseDataBaseRealtimeByNodo.value = GetRealTimeDataBaseUiState.Success(it)
                    },
                    onError =  { exception ->
                        _stateSnapShotFiebaseDataBaseRealtimeByNodo.value = GetRealTimeDataBaseUiState.Error(exception.message.toString())
                    }
                )
                delay(timeMillis = 100)
                _stateSnapShotFiebaseDataBaseRealtimeByNodo.value = GetRealTimeDataBaseUiState.Loading("")
            }catch (exception: Exception){
                _stateSnapShotFiebaseDataBaseRealtimeByNodo.value = GetRealTimeDataBaseUiState.Error(exception.message.toString())
                delay(timeMillis = 100)
                _stateSnapShotFiebaseDataBaseRealtimeByNodo.value = GetRealTimeDataBaseUiState.Loading("")
            }
        }
    }

    sealed class GetRealTimeDataBaseUiState {
        data class Loading(val step: String = "loading") : GetRealTimeDataBaseUiState()
        data class Success(val valueSnapShot: String = "") : GetRealTimeDataBaseUiState()
        data class Error(val message: String = "") : GetRealTimeDataBaseUiState()
    }


}