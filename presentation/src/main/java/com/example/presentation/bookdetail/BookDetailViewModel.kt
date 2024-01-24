package com.example.presentation.bookdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BookListModel
import com.example.domain.model.MyBookModel
import com.example.domain.usecase.BookListUseCase
import com.example.domain.usecase.MyBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase,
    private val myBookUseCase: MyBookUseCase,
    application: Application
): AndroidViewModel(application) {
    private val _bookDetail=  MutableStateFlow<BookListModel?>(null)
    val bookDetail: StateFlow<BookListModel?> = _bookDetail.asStateFlow()

    fun getBookDetail(itemId: Long){
        viewModelScope.launch {
            bookListUseCase.getBookDetail(itemId = itemId).collectLatest {
                _bookDetail.value = it
            }
                /*.onStart {
                Log.e("","책 상세정보 호출 로딩")
                _bookDetail.value = BookDetailState.Loading
            }.catch {
                Log.e("","책 상세정보 호출 에러")
                _bookDetail.value = BookDetailState.Error()
            }.collect{
                Log.e("","책 상세정보 호출 성공")
                _bookDetail.value = BookDetailState.Success(it)
            }*/
        }
    }
    fun insertMyBook(book: MyBookModel){
        viewModelScope.launch(Dispatchers.IO) {
            myBookUseCase.insertMyBook(book)
        }
    }
}

sealed class BookDetailState(val data: BookListModel? = null, val errorMessage: String? = ""){
    object Loading: BookDetailState()
    data class Error(val message: String? = ""): BookDetailState(errorMessage = message)
    data class Success(val book: BookListModel): BookDetailState(data = book)
}