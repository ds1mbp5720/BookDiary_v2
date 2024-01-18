package com.example.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BookListModel
import com.example.domain.usecase.BookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase

):ViewModel() {
    private val _bookListData = MutableStateFlow<BookListState>(BookListState.Loading)
    val bookListData: StateFlow<BookListState> = _bookListData.asStateFlow()

    fun getBookList(){
        viewModelScope.launch {
           bookListUseCase.getBookList().onStart {
               _bookListData.value = BookListState.Loading
           }.catch {
               _bookListData.value = BookListState.Error()
           }.collect {
               _bookListData.value = BookListState.Success(it)
           }
        }
    }
}

sealed class BookListState(val data: BookListModel? = null, val resultCode: String? = ""){
    object Loading: BookListState()
    data class Error(val message: String? = ""): BookListState(resultCode = message)
    data class Success(val bookList: BookListModel): BookListState(data = bookList)
}