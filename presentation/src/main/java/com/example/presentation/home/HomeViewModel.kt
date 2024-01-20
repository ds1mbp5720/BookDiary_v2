package com.example.presentation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.BookListModel
import com.example.domain.model.BookModel
import com.example.domain.usecase.BookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase,
    application: Application
):AndroidViewModel(application) {
    // 종류별 책 리스트 flow 생성
    private val _bookListDataItemNewAll: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val bookListDataItemNewAll: StateFlow<PagingData<BookModel>> = _bookListDataItemNewAll.asStateFlow()
    private val _bookListDataItemNewSpecial: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val bookListDataItemNewSpecial: StateFlow<PagingData<BookModel>> = _bookListDataItemNewSpecial.asStateFlow()
    private val _bookListDataBestseller: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val bookListDataBestseller: StateFlow<PagingData<BookModel>> = _bookListDataBestseller.asStateFlow()
    private val _bookListDataBlogBest: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val bookListDataBlogBest: StateFlow<PagingData<BookModel>> = _bookListDataBlogBest.asStateFlow()
    fun getBookListToItemNewAll1(queryType: String){
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                     _bookListDataItemNewAll.emit(it)
            }
        }
    }
    fun getBookListToItemNewAll2(queryType: String){
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _bookListDataItemNewAll.emit(it)
                }
        }
    }
    fun getBookListToItemNewAll3(queryType: String){
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _bookListDataItemNewAll.emit(it)
                }
        }
    }
    fun getBookListToItemNewAll4(queryType: String){
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _bookListDataItemNewAll.emit(it)
                }
        }
    }
}

sealed class BookListState(val data: BookListModel? = null, val resultCode: String? = ""){
    object Loading: BookListState()
    data class Error(val message: String? = ""): BookListState(resultCode = message)
    data class Success(val bookList: BookListModel): BookListState(data = bookList)
}