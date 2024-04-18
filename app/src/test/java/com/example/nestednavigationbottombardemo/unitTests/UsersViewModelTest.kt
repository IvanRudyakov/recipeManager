package com.example.nestednavigationbottombardemo.unitTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nestednavigationbottombardemo.User
import com.example.nestednavigationbottombardemo.mocks.getMockUserViewModel
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UsersViewModelTest {

    private val underTest = getMockUserViewModel()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @Test
    fun testRefresh() = runTest {

        val liveData = underTest.userList
        underTest.refresh()

        val value = liveData.value
        Assert.assertEquals(
            value, listOf(
                User("Alice", 2)
            )
        )
    }

}