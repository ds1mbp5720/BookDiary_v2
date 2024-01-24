package com.example.presentation.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.domain.usecase.BookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase,
    application: Application
):  AndroidViewModel(application){


}