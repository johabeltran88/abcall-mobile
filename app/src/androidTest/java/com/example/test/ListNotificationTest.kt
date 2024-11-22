package com.example.test

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.test.ui.ListPccActivity
import com.example.test.ui.LoginActivity
import com.example.test.ui.adapters.NotificationAdapter
import com.example.test.ui.adapters.PccAdapter
import com.github.javafaker.Faker
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matcher
import android.view.View
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.junit.Assert.assertEquals

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListNotificationTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun ListnotificationCreatingPqrSuccess() {
        val subject = Faker().name().fullName()
        val description = Faker().lorem().paragraph(3)

        Espresso.onView(ViewMatchers.withId(R.id.email))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("proyecto_de_grado1@gmail.com"))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("password"))

        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.bienvenido)).inRoot(RootMatchers.isDialog())
            .check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )

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
            .perform(ViewActions.scrollTo(), ViewActions.typeText(subject))
        Espresso.onView(ViewMatchers.withId(R.id.description))
            .perform(
                ViewActions.scrollTo(),
                ViewActions.typeText(description)
            )
        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.incidente_creado))
            .inRoot(RootMatchers.isDialog()).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed())
            )

        Espresso.onView(ViewMatchers.withText("Ok"))
            .inRoot(RootMatchers.isDialog())
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        val listPccActivity = getCurrentActivity<ListPccActivity>()
        var position = 0
        for (pcc in listPccActivity?.pccAdapter?.listPcc!!) {
            if (pcc.subject == subject) {
                break
            }
            position += 1
        }
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pcc))
            .perform(
                RecyclerViewActions.scrollToPosition<PccAdapter.PccViewHolder>(position),
                RecyclerViewActions.actionOnItemAtPosition<PccAdapter.PccViewHolder>(
                    position,
                    ViewActions.click()
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.subject)).check(
            ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString(subject)))
        )
        Espresso.onView(ViewMatchers.withId(R.id.description)).check(
            ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString(description)))
        )

        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_pcc))
            .check(object : ViewAssertion {
                override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
                    // Verificar que el RecyclerView no sea null
                    if (view == null) {
                        throw AssertionError("RecyclerView not found!")
                    }

                    // Verificar el número de elementos en el RecyclerView
                    val recyclerView = view as RecyclerView
                    val itemCount = recyclerView.adapter?.itemCount ?: 0
                    assertEquals("RecyclerView item count should be 2", 2, itemCount)
                }
            })
    }

    private fun <T : AppCompatActivity?> getCurrentActivity(): T? {
        val activity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val activities =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (activities != null && !activities.isEmpty()) {
                activity[0] = activities.iterator().next()
            }
        }
        return activity[0] as? T
    }
}