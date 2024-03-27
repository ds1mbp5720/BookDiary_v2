package com.example.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.BookModel
import com.example.domain.usecase.BookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase,
    application: Application
) : AndroidViewModel(application) {
    // 종류별 책 리스트 flow 생성
    private val _bookListDataItemNewAll: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val bookListDataItemNewAll: StateFlow<PagingData<BookModel>> = _bookListDataItemNewAll.asStateFlow()
    private val _bookListDataItemNewSpecial: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val bookListDataItemNewSpecial: StateFlow<PagingData<BookModel>> = _bookListDataItemNewSpecial.asStateFlow()
    private val _bookListDataBestseller: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val bookListDataBestseller: StateFlow<PagingData<BookModel>> = _bookListDataBestseller.asStateFlow()
    private val _bookListDataBlogBest: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val bookListDataBlogBest: StateFlow<PagingData<BookModel>> = _bookListDataBlogBest.asStateFlow()
    fun getBookListToItemNewAll(queryType: String, size: Int) {
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType, size)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _bookListDataItemNewAll.emit(it)
                }
        }
    }

    fun getBookListToItemNewSpecial(queryType: String, size: Int) {
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType, size)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _bookListDataItemNewSpecial.emit(it)
                }
        }
    }

    fun getBookListToItemBestseller(queryType: String, size: Int) {
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType, size)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _bookListDataBestseller.emit(it)
                }
        }
    }

    fun getBookListToItemBlogBest(queryType: String, size: Int) {
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType, size)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _bookListDataBlogBest.emit(it)
                }
        }
    }

    private val _singleCategoryBookList: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val singleCategoryBookList: StateFlow<PagingData<BookModel>> = _singleCategoryBookList.asStateFlow()

    fun getSingleCategoryBookList(queryType: String, size: Int) {
        viewModelScope.launch {
            bookListUseCase.getBookListPaging(queryType, size)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _singleCategoryBookList.emit(it)
                }
        }
    }
}