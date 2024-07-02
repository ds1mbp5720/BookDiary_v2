package com.example.test

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.presentation.search.SearchHistoryCard
import com.example.presentation.theme.BookDiaryTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    @Before
    fun setUpApp(){
        hiltTestRule.inject()
        composeTestRule.setContent {
            BookDiaryTheme{
                SearchHistoryCard(Modifier,"test", onClickEvent = {})
            }
        //BookDiaryApp()
        }
    }
    @Test
    fun firstTest() {
        composeTestRule.onNodeWithText("HOME").assertIsDisplayed()
    }
}