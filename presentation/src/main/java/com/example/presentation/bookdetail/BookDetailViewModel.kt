package com.example.presentation.bookdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BookListModel
import com.example.domain.model.MyBookModel
import com.example.domain.model.OffStoreListModel
import com.example.domain.model.WishBookModel
import com.example.domain.usecase.BookListUseCase
import com.example.domain.usecase.MyBookUseCase
import com.example.domain.usecase.OffStoreUseCase
import com.example.domain.usecase.WishBookUseCase
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
    private val wishBookUseCase: WishBookUseCase,
    private val offStoreUseCase: OffStoreUseCase,
    application: Application
) : AndroidViewModel(application) {
    private val _bookDetail = MutableStateFlow<BookListModel?>(null)
    val bookDetail: StateFlow<BookListModel?> = _bookDetail.asStateFlow()
    private val _offStoreInfo = MutableStateFlow<OffStoreListModel?>(null)
    val offStoreInfo: StateFlow<OffStoreListModel?> = _offStoreInfo.asStateFlow()
    fun getBookDetail(itemId: Long) {
        viewModelScope.launch {
            bookListUseCase.getBookDetail(itemId = itemId).collectLatest {
                _bookDetail.value = it
            }
            /*.onStart {
            _bookDetail.value = BookDetailState.Loading
        }.catch {
            _bookDetail.value = BookDetailState.Error()
        }.collect{
            _bookDetail.value = BookDetailState.Success(it)
        }*/
        }
    }

    fun getOffStoreInfo(itemId: String) {
        viewModelScope.launch {
            offStoreUseCase.getOffStoreInfo(itemId = itemId).collectLatest {
                _offStoreInfo.value = it
            }
        }
    }

    fun insertMyBook(book: MyBookModel) {
        viewModelScope.launch(Dispatchers.IO) {
            myBookUseCase.insertMyBook(book)
        }
    }

    fun insertWishBook(book: WishBookModel) {
        viewModelScope.launch(Dispatchers.IO) {
            wishBookUseCase.insertWishBook(book)
        }
    }
}

sealed class BookDetailState(val data: BookListModel? = null, val errorMessage: String? = "") {
    object Loading : BookDetailState()
    data class Error(val message: String? = "") : BookDetailState(errorMessage = message)
    data class Success(val book: BookListModel) : BookDetailState(data = book)
}