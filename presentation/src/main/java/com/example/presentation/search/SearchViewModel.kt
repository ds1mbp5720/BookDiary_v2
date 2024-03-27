package com.example.presentation.search

import android.app.Application
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.BookModel
import com.example.domain.repository.SearchHistoryRepository
import com.example.domain.usecase.BookListUseCase
import com.example.presentation.util.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase,
    private val searchHistoryRepository: SearchHistoryRepository,
    application: Application
) : AndroidViewModel(application) {
    val searchState: SearchState = SearchState(query = TextFieldValue(""), focused = false, searching = false, noResult = false) // 상태에 맞춰 상단 검색 바 갱신
    private val _searchBookList: MutableStateFlow<PagingData<BookModel>> = MutableStateFlow(value = PagingData.empty())
    val searchBookList: StateFlow<PagingData<BookModel>> = _searchBookList.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun getSearchBookList(queryType: String, size: Int) {
        viewModelScope.launch {
            bookListUseCase.getSearchBookListPaging(queryType, size)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .onStart { _loading.emit(true) }
                .collect {
                    _loading.emit(false)
                    _searchBookList.emit(it)
                }
        }
    }

    private val _searchHistory = MutableStateFlow(listOf(""))
    val searchHistory = _searchHistory.asStateFlow()
    private fun setSearchHistory(context: Context, search: MutableSet<String>) {
        viewModelScope.launch {
            searchHistoryRepository.setSearchHistory(context, search.toList())
        }
    }

    fun getSearchHistory(context: Context) {
        viewModelScope.launch {
            _searchHistory.value = searchHistoryRepository.getSearchHistory(context)
        }
    }

    fun addSearchHistory(context: Context, search: String) {
        viewModelScope.launch {
            val copySearchHistory: MutableSet<String> = mutableSetOf()
            copySearchHistory.addAll(searchHistoryRepository.getSearchHistory(context))
            copySearchHistory.add(search)
            setSearchHistory(context, copySearchHistory)
            _searchHistory.value = copySearchHistory.toList()
        }
    }

    fun removeSearchHistory(context: Context, search: String) {
        viewModelScope.launch {
            val copySearchHistory: MutableSet<String> = mutableSetOf()
            copySearchHistory.addAll(searchHistoryRepository.getSearchHistory(context))
            copySearchHistory.remove(search)
            setSearchHistory(context, copySearchHistory)
            _searchHistory.value = copySearchHistory.toList()
        }
    }


}