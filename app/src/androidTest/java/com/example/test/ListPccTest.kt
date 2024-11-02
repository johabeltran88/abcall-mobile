package com.example.test

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.test.ui.LoginActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListPccTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun ListPccEmptySuccess(){
        Espresso.onView(ViewMatchers.withId(R.id.email))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("proyecto_de_grado2@gmail.com"))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("password"))

        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pcc))  // Cambia 'recyclerView' al ID de tu lista
            .check { view, noViewFoundException ->
                if (noViewFoundException != null) throw noViewFoundException
                val recyclerView = view as RecyclerView
                assert(recyclerView.adapter?.itemCount == 0) { "La lista no está vacía" }
            }
    }

    @Test
    fun ListPccCreatingPqrSuccess(){
        Espresso.onView(ViewMatchers.withId(R.id.email))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("proyecto_de_grado1@gmail.com"))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("password"))

        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit2))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.company)).perform(ViewActions.click())
        Espresso.onData(Matchers.anything())
            .inRoot(RootMatchers.isPlatformPopup())
            .atPosition(0)
            .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.subject))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("Test"))
        Espresso.onView(ViewMatchers.withId(R.id.description))
            .perform(
                ViewActions.scrollTo(),
                ViewActions.typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            )
        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.incidente_creado)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pcc))  // Cambia 'recyclerView' al ID de tu lista
            .check { view, noViewFoundException ->
                if (noViewFoundException != null) throw noViewFoundException
                val recyclerView = view as RecyclerView
                assert(recyclerView.adapter?.itemCount ?: 0 >= 1) { "La lista está vacía" }
            }
    }
}