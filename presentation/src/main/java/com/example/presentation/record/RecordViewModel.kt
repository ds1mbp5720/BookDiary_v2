package com.example.presentation.record

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MyBookModel
import com.example.domain.usecase.MyBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel  @Inject constructor(
    private val myBookUseCase: MyBookUseCase,
    application: Application
):  AndroidViewModel(application) {

    val myBookList = MutableLiveData<List<MyBookModel>>()
    fun getMyBookList() {
        viewModelScope.launch(Dispatchers.IO) {
            myBookUseCase.execute(
                onSuccess = {
                    myBookList.value = it
                },
                onError = {
                    Log.e("","room Error $it")
                }
            )
            myBookUseCase.getMyBookList()
        }
    }
}