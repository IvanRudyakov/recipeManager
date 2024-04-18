package com.example.nestednavigationbottombardemo.unitTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nestednavigationbottombardemo.User
import com.example.nestednavigationbottombardemo.mocks.getMockUsersRepo
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UsersRepositoryTest {

    private val underTestOnline = getMockUsersRepo(isOnline = true);
    private val underTestOffline = getMockUsersRepo(isOnline = false);

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @Test
    fun testGetUsersOnline() = runTest {

        val users = underTestOnline.getUsers();

        Assert.assertEquals(
            users, listOf(
                User("Alice", 2)
            )
        )
    }

    @Test
    fun testGetUsersOffline() = runTest {

        val users = underTestOffline.getUsers();

        Assert.assertEquals(
            users, listOf(
                User("Sam", 1)
            )
        )
    }

}