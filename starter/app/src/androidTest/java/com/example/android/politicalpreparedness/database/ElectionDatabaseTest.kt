package com.example.android.politicalpreparedness.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.filters.SmallTest


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ElectionDatabaseTest
{

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

//    private lateinit var database: ToDoDatabase
//
//    @Before
//    fun initDb() {
//        // Using an in-memory database so that the information stored here disappears when the
//        // process is killed.
//        database = Room.inMemoryDatabaseBuilder(
//                ApplicationProvider.getApplicationContext(),
//                ToDoDatabase::class.java
//        ).build()
//    }
//
//    @After
//    fun closeDb() = database.close()
//
//    @Test
//    fun insertTaskAndGetById() = runBlockingTest {
//        // GIVEN - Insert a task.
//        val task = Task("title", "description")
//        database.taskDao().insertTask(task)
//
//        // WHEN - Get the task by id from the database.
//        val loaded = database.taskDao().getTaskById(task.id)
//
//        // THEN - The loaded data contains the expected values.
//        MatcherAssert.assertThat<Task>(loaded as Task, Matchers.notNullValue())
//        MatcherAssert.assertThat(loaded.id, `is`(task.id))
//        MatcherAssert.assertThat(loaded.title, `is`(task.title))
//        MatcherAssert.assertThat(loaded.description, `is`(task.description))
//        MatcherAssert.assertThat(loaded.isCompleted, `is`(task.isCompleted))
//    }
//
//    @Test
//    fun insertTaskAndUpdateTask() = runBlockingTest {
//        // GIVEN - Insert a task.
//        val task = Task("title", "description")
//        database.taskDao().insertTask(task)
//
//        // WHEN - Update the task Get the task by id from the database.
//        task.apply {
//            title = "updatedTitle"
//            description = "updatedDescription"
//        }
//        database.taskDao().updateTask(task)
//        val loaded = database.taskDao().getTaskById(task.id)
//
//        // THEN - The loaded data contains the expected values.
//        MatcherAssert.assertThat<Task>(loaded as Task, Matchers.notNullValue())
//        MatcherAssert.assertThat(loaded.id, `is`(task.id))
//        MatcherAssert.assertThat(loaded.title, `is`(task.title))
//        MatcherAssert.assertThat(loaded.description, `is`(task.description))
//        MatcherAssert.assertThat(loaded.isCompleted, `is`(task.isCompleted))
//    }
}