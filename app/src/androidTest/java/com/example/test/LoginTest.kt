package com.example.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.test.ui.LoginActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun loginSuccess(){
        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loginError(){
        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado123@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.error4)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loginEmptyEmail(){
        onView(withId(R.id.email))
            .perform(scrollTo(), typeText(""))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loginEmptyPassword(){
        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado123@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText(""))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}