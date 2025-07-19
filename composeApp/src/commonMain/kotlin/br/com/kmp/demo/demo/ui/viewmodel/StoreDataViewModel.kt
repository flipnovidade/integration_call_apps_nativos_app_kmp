package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.setpref.SecureSettings
import br.com.kmp.demo.demo.setpref.SettingsApp
import br.com.kmp.demo.demo.ui.components.KmpLogger
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoreDataViewModel(
    private val secureSettings: SecureSettings,
    kmpLogger: KmpLogger,
): BaseViewModel() {

    private val _valueStorePrefs = MutableStateFlow<String>( "")
    val valueStorePrefs: StateFlow<String> = _valueStorePrefs

    var jobPutStorePrefes: Job? = SupervisorJob()
    var jobGetStorePrefes: Job? = SupervisorJob()

    var keyString = "valueString"

    init {
        kmpLogger.d("StoreDataViewModel", "init")
        viewModelScope.launch {
            getValueStringByKey()
        }
    }
    
    fun putValueStorePrefs(valueString: String, keystring: String = keyString){
        KmpLogger.d("StoreDataViewModel", "putDataFirebaseDataBaseRealTime-> valueString $valueString, keystring $keystring")
        jobPutStorePrefes?.cancel()
        jobPutStorePrefes = viewModelScope.launch {
            try {
                async {
                    secureSettings.putString(keystring, valueString)
                }.await()
                async {
                    getValueStringByKey(keystring)
                }.await()
            }catch (exception: Exception){
                _valueStorePrefs.value = "Error to save ${exception.message.toString()}"
            }
        }
    }

    fun getValueStringByKey(key: String = keyString) {
        KmpLogger.d("StoreDataViewModel", "getValueStringByKey-> $key")
        jobGetStorePrefes?.cancel()
        jobGetStorePrefes = viewModelScope.launch {
            try {
                delay(timeMillis = 1000)
                async {
                    secureSettings.getString(key)?.let {
                        _valueStorePrefs.value = it
                    }
                }.await()
                delay(timeMillis = 1000)

            }catch (exception: Exception){
                _valueStorePrefs.value = "Error to get ${exception.message.toString()}"
            }
        }

    }

}
