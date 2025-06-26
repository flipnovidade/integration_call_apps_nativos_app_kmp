package br.com.kmp.demo.demo.ui.viewmodel

import br.com.kmp.demo.demo.di.usecase.CatsUseCase
import br.com.kmp.demo.demo.model.Cat
import br.com.kmp.demo.demo.ui.components.KmpLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val catsUseCase: CatsUseCase,
    kmpLogger: KmpLogger
) : BaseViewModel() {

    private val _state = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val state: StateFlow<MainUiState> = _state

    private val _stateDetailCat = MutableStateFlow<DetailCatUiState>(DetailCatUiState.Loading)
    val stateDetailCat: StateFlow<DetailCatUiState> = _stateDetailCat

    private var cat: Cat? = null

    init {
        getCats()
        kmpLogger.e("MainScreenViewModel", "MainScreenViewModel created")
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

    fun getCatById(){
        viewModelScope.launch {
            try {
                _stateDetailCat.value = DetailCatUiState.Loading
                val result = catsUseCase.getCatById(cat?.id!!.toInt())
                _stateDetailCat.value = DetailCatUiState.SuccessGetCat(result!!)
            } catch (e: Exception) {
                _stateDetailCat.value = DetailCatUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun setCatClicked(cat: Cat){
        this.cat = cat
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

    sealed class MainUiState {
        object Loading : MainUiState()
        data class Success(val cats: List<Cat>) : MainUiState()
        data class Error(val message: String) : MainUiState()
    }

    sealed class DetailCatUiState {
        object Loading : DetailCatUiState()
        data class SuccessGetCat(val cat: Cat) : DetailCatUiState()
        data class Error(val message: String) : DetailCatUiState()
    }

}


