package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.di.usecase.CatsUseCase
import br.com.kmp.demo.demo.model.Cat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val catsUseCase: CatsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val state: StateFlow<MainUiState> = _state

    init {
        getCats()
    }

    fun getCats(){
        viewModelScope.launch {
            try {
                _state.value = MainUiState.Loading
                val result = catsUseCase.getAllCats()
                _state.value = MainUiState.Success(result)
            } catch (e: Exception) {
                _state.value = MainUiState.Error(e.message ?: "Erro desconhecido")
            }

        }
//        CoroutineScope(Dispatchers.Default).launch {
//
//        }
    }

    fun insertCat(){
        viewModelScope.launch {

        }
    }

    fun updateCat(){
        viewModelScope.launch {

        }
    }

    fun deleteCat(){
        viewModelScope.launch {

        }
    }

    fun selectCat(post: Cat) {
        _state.value = MainUiState.Selected(post)
    }

    sealed class MainUiState {
        object Loading : MainUiState()
        data class Success(val cats: List<Cat>) : MainUiState()
        data class Error(val message: String) : MainUiState()
        data class Selected(val post: Cat) : MainUiState()
    }

}


