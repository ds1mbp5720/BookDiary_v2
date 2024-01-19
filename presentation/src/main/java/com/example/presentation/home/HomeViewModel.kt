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
    private val _bookListData = MutableStateFlow<BookListState>(BookListState.Loading)
    val bookListData: StateFlow<BookListState> = _bookListData.asStateFlow()

    private val _categoryBookListData = MutableStateFlow<MutableList<BookListModel>?>(null)
    val categoryBookListData: StateFlow<MutableList<BookListModel>?> = _categoryBookListData.asStateFlow()
    private val copyCategoryList = mutableListOf<BookListModel>()

    fun getBookList(queryType: String){
        viewModelScope.launch {
           bookListUseCase.getBookList(queryType, 1).onStart {
               _bookListData.value = BookListState.Loading
           }.catch {
               _bookListData.value = BookListState.Error()
           }.collect {
               _bookListData.value = BookListState.Success(it)
           }
        }
    }
    private val _pagingBookListData: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val pagingBookListData: StateFlow<PagingData<BookModel>> = _pagingBookListData.asStateFlow()
    fun getBookPagingList(queryType: String){
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                _pagingBookListData.emit(it)
            }
        }
    }

    fun addCategoryBookList(bookListModel: BookListModel){
        copyCategoryList.add(bookListModel)
        Log.e("","통신 결과 viewModel 사이즈 ${copyCategoryList.size}")
        _categoryBookListData.value = copyCategoryList
    }
}

sealed class BookListState(val data: BookListModel? = null, val resultCode: String? = ""){
    object Loading: BookListState()
    data class Error(val message: String? = ""): BookListState(resultCode = message)
    data class Success(val bookList: BookListModel): BookListState(data = bookList)
}