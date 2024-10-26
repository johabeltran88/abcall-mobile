package com.example.test

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
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
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.anything
import android.content.Context


@LargeTest
@RunWith(AndroidJUnit4::class)
class CreatePccTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun createPccSubjectEmptyError(){
        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        onView(withId(R.id.btnSubmit2)).perform(scrollTo(), click())

        onView(withId(R.id.company)).perform(click())
        onData(anything())  // Usa `anything()` para que no importe el tipo de dato en la lista
            .inRoot(RootMatchers.isPlatformPopup()) // Asegúrate de que está en el desplegable
            .atPosition(0) // Selecciona el primer elemento
            .perform(click());
        onView(withId(R.id.subject))
            .perform(scrollTo(), typeText(""))
        onView(withId(R.id.description))
            .perform(scrollTo(), typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun createPccDescriptionEmptyError(){
        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        onView(withId(R.id.btnSubmit2)).perform(scrollTo(), click())

        onView(withId(R.id.company)).perform(click())
        onData(anything())  // Usa `anything()` para que no importe el tipo de dato en la lista
            .inRoot(RootMatchers.isPlatformPopup()) // Asegúrate de que está en el desplegable
            .atPosition(0) // Selecciona el primer elemento
            .perform(click());
        onView(withId(R.id.subject))
            .perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.description))
            .perform(scrollTo(), typeText(""))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun createPccDescriptionLessMinimumError(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val errorMessage = context.getString(R.string.error5, 100, 1000)

        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        onView(withId(R.id.btnSubmit2)).perform(scrollTo(), click())

        onView(withId(R.id.company)).perform(click())
        onData(anything())  // Usa `anything()` para que no importe el tipo de dato en la lista
            .inRoot(RootMatchers.isPlatformPopup()) // Asegúrate de que está en el desplegable
            .atPosition(0) // Selecciona el primer elemento
            .perform(click());
        onView(withId(R.id.subject))
            .perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.description))
            .perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(errorMessage)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun createPccDescriptionGraterMaximumError(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val errorMessage = context.getString(R.string.error5, 100, 1000)

        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        onView(withId(R.id.btnSubmit2)).perform(scrollTo(), click())

        onView(withId(R.id.company)).perform(click())
        onData(anything())  // Usa `anything()` para que no importe el tipo de dato en la lista
            .inRoot(RootMatchers.isPlatformPopup()) // Asegúrate de que está en el desplegable
            .atPosition(0) // Selecciona el primer elemento
            .perform(click());
        onView(withId(R.id.subject))
            .perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.description))
            .perform(scrollTo(), typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(errorMessage)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun createPccSubjectGraterMaximumError(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val errorMessage = context.getString(R.string.error3, 250)

        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        onView(withId(R.id.btnSubmit2)).perform(scrollTo(), click())

        onView(withId(R.id.company)).perform(click())
        onData(anything())  // Usa `anything()` para que no importe el tipo de dato en la lista
            .inRoot(RootMatchers.isPlatformPopup()) // Asegúrate de que está en el desplegable
            .atPosition(0) // Selecciona el primer elemento
            .perform(click());
        onView(withId(R.id.subject))
            .perform(scrollTo(), typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        onView(withId(R.id.description))
            .perform(scrollTo(), typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(errorMessage)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun createPccNotSelectCompanyError(){
        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        onView(withId(R.id.btnSubmit2)).perform(scrollTo(), click())

        onView(withId(R.id.subject))
            .perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.description))
            .perform(scrollTo(), typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())
        closeSoftKeyboard()

        onView(ViewMatchers.withText(R.string.error1)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun createPccSuccess(){
        onView(withId(R.id.email))
            .perform(scrollTo(), typeText("proyecto_de_grado@gmail.com"))

        onView(withId(R.id.password))
            .perform(scrollTo(), typeText("password"))

        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        onView(withId(R.id.btnSubmit2)).perform(scrollTo(), click())

        onView(withId(R.id.company)).perform(click())
        onData(anything())  // Usa `anything()` para que no importe el tipo de dato en la lista
            .inRoot(RootMatchers.isPlatformPopup()) // Asegúrate de que está en el desplegable
            .atPosition(0) // Selecciona el primer elemento
            .perform(click());
        onView(withId(R.id.subject))
            .perform(scrollTo(), typeText("Test"))
        onView(withId(R.id.description))
            .perform(scrollTo(), typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        onView(withId(R.id.btnSubmit)).perform(scrollTo(), click())

        onView(ViewMatchers.withText(R.string.incidente_creado)).inRoot(RootMatchers.isDialog()).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}