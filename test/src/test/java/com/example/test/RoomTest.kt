package com.example.test

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.data.room.database.WishBookDataBase
import com.example.data.room.entity.WishBookEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class RoomTest {
    private lateinit var appDatabase: WishBookDataBase
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(
            appContext,
            WishBookDataBase::class.java
        ).build()
    }
    @After
    fun cleanUp() {
        appDatabase.close()
    }

    @Test
    fun insertTest()  {
        val testWishData = WishBookEntity(itemId = 123456L, imageUrl = "testUrl", title = "test")
        appDatabase.getWishBookDao().insertWishBook(testWishData)
        val testWishBookList = appDatabase.getWishBookDao().getWishBookList()

        Assert.assertNull(testWishBookList)
        Assert.assertNotNull(testWishBookList)
    }
}