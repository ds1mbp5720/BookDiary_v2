package com.example.presentation.bookdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BookModel
import com.example.domain.usecase.BookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase,
    application: Application
): AndroidViewModel(application) {
    private val _bookDetail: MutableStateFlow<BookDetailState> = MutableStateFlow(value = BookDetailState.Loading)
    val bookDetail: StateFlow<BookDetailState> = _bookDetail.asStateFlow()

    fun getBookDetail(itemId: Long){
        viewModelScope.launch {
            bookListUseCase.getBookDetail(itemId = itemId).onStart {
                _bookDetail.value = BookDetailState.Loading
            }.catch {
                _bookDetail.value = BookDetailState.Error()
            }.collect{
                _bookDetail.value = BookDetailState.Success(it)
            }
        }
    }
}

sealed class BookDetailState(val data: BookModel? = null, val errorMessage: String? = ""){
    object Loading: BookDetailState()
    data class Error(val message: String? = ""): BookDetailState(errorMessage = message)
    data class Success(val book: BookModel): BookDetailState(data = book)
}