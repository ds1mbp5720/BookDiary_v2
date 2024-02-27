package com.example.test

import android.app.Application
import com.example.domain.usecase.BookListUseCase
import com.example.domain.usecase.MyBookUseCase
import com.example.domain.usecase.OffStoreUseCase
import com.example.domain.usecase.WishBookUseCase
import com.example.presentation.bookdetail.BookDetailViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@HiltAndroidTest
class DetailViewModelTest {
    lateinit var viewModel: BookDetailViewModel
    @Mock
    lateinit var bookListUseCase: BookListUseCase
    @Mock
    lateinit var myBookUseCase: MyBookUseCase
    @Mock
    lateinit var wishBookUseCase: WishBookUseCase
    @Mock
    lateinit var offStoreUseCase: OffStoreUseCase

    @Mock
    lateinit var application: Application
    @Before
    fun setUp() {
        viewModel = BookDetailViewModel(
            bookListUseCase =  bookListUseCase,
            myBookUseCase =  myBookUseCase,
            wishBookUseCase = wishBookUseCase,
            offStoreUseCase = offStoreUseCase,
            application = application)
    }

    @Test
    fun getDetailDataTullTest() {
        val data = viewModel.bookDetail

        Assert.assertNotNull(data)
        Assert.assertNull(data)
    }
}