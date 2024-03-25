package com.example.presentation.record

import android.app.Application
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MyBookModel
import com.example.domain.model.WishBookModel
import com.example.domain.usecase.MyBookUseCase
import com.example.domain.usecase.WishBookUseCase
import com.example.presentation.util.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val myBookUseCase: MyBookUseCase,
    private val wishBookUseCase: WishBookUseCase,
    application: Application
) : AndroidViewModel(application) {
    val searchState: SearchState = SearchState(query = TextFieldValue(""), focused = false, searching = false) // 상태에 맞춰 상단 검색 바 갱신
    val myBookList = MutableLiveData<List<MyBookModel>>()
    val wishBookList = MutableLiveData<List<WishBookModel>>()
    fun getMyBookList() {
        viewModelScope.launch(Dispatchers.IO) {
            myBookUseCase.execute(
                onSuccess = {
                    myBookList.value = it
                },
                onError = {
                    Log.e("", "room Error (MyBook) $it")
                }
            )
            myBookUseCase.getMyBookList()
        }
    }
    fun getWishBookList() {
        viewModelScope.launch(Dispatchers.IO) {
            wishBookUseCase.execute(
                onSuccess = {
                    wishBookList.value = it
                },
                onError = {
                    Log.e("", "room Error (WishBook) $it")
                }
            )
            wishBookUseCase.getWishBookList()
        }
    }

    fun deleteMyBook(bookId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            myBookUseCase.delete(bookId)
            myBookUseCase.execute(
                onSuccess = {
                    myBookList.value = it
                },
                onError = {
                    Log.e("", "room Error (MyBook) $it")
                }
            )
        }
    }

    fun deleteWishBook(bookId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            wishBookUseCase.delete(bookId)
            wishBookUseCase.execute(
                onSuccess = {
                    wishBookList.value = it
                },
                onError = {
                    Log.e("", "room Error (WishBook) $it")
                }
            )
        }
    }
}