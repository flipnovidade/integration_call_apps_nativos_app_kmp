package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.di.usecase.CatsUseCase
import br.com.kmp.demo.demo.firebase.FirebaseRemoteConfigsBridge
import br.com.kmp.demo.demo.model.Cat
import br.com.kmp.demo.demo.ui.components.KmpLogger
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListItemScreenViewModel(
    private val firebaseRemoteConfigsBridge: FirebaseRemoteConfigsBridge,
    kmpLogger: KmpLogger
) : BaseViewModel() {

    var job: Job? = SupervisorJob()

    init {

    }

    sealed class ListItemUiState {
        object Loading : ListItemUiState()
        data class Success(val cats: List<Cat>) : ListItemUiState()
        data class Error(val message: String) : ListItemUiState()
    }

    sealed class ListItemDetailUiState {
        object Loading : ListItemDetailUiState()
        data class SuccessGetCat(val cat: Cat) : ListItemDetailUiState()
        data class Error(val message: String) : ListItemDetailUiState()
    }

}


