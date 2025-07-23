package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.firebase.remoteconfig.FirebaseRemoteConfigsBridge
import br.com.kmp.demo.demo.ui.components.ImageLoader
import br.com.kmp.demo.demo.ui.components.KmpLogger
import io.ktor.client.HttpClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListItemScreenViewModel(
    private val firebaseRemoteConfigsBridge: FirebaseRemoteConfigsBridge,
    kmpLogger: KmpLogger,
    private val httpClient: HttpClient
) : BaseViewModel() {

    private val _stateRemoteConfig = MutableStateFlow<RemoteConfigUiState>(RemoteConfigUiState.Loading(step = "loading"))
    val stateRemoteConfig: StateFlow<RemoteConfigUiState> = _stateRemoteConfig
    var jobRemoteConfig: Job? = SupervisorJob()

    private val _myByteArray = MutableStateFlow<ByteArray>(ByteArray(0))
    val myByteArray = _myByteArray

    init {
        kmpLogger.d("ListItemScreenViewModel", "init")
        firebaseRemoteConfigsBridge.fetchAndActivateFirebaseRemoteConfigs(2.0)
        viewModelScope.launch {
            loadImgeByUrl()
            getValueRemoteConfigs()
        }
    }

    suspend fun loadImgeByUrl() {
        viewModelScope.launch {
            val byteArray = ImageLoader(httpClient = httpClient).loadImageByUrl()
            myByteArray.value = byteArray
        }
    }

    suspend fun getValueRemoteConfigs() {
        jobRemoteConfig?.cancel()
        jobRemoteConfig = viewModelScope.launch {
            try {
                _stateRemoteConfig.value = RemoteConfigUiState.Loading(step = "loading")
                val remoteConfigvalue = firebaseRemoteConfigsBridge.getRemoteConfigString(key = "forteach").toBoolean()
                _stateRemoteConfig.value = RemoteConfigUiState.Success(valueRemoteConfig = remoteConfigvalue)
                delay(100)
                _stateRemoteConfig.value = RemoteConfigUiState.Loading(step = "")
            }catch (e: Exception){
                _stateRemoteConfig.value = RemoteConfigUiState.Error(message = "Error ${e.message}")
                delay(100)
                _stateRemoteConfig.value = RemoteConfigUiState.Loading(step = "")
            }
        }
        jobRemoteConfig?.join()
    }

    sealed class RemoteConfigUiState {
        data class Loading(val step: String = "loading") : RemoteConfigUiState()
        data class Success(val valueRemoteConfig: Boolean = false) : RemoteConfigUiState()
        data class Error(val message: String = "") : RemoteConfigUiState()
    }

}
