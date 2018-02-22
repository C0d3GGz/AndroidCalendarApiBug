package de.thkoeln.calendarapibug

import android.support.test.runner.AndroidJUnit4
import org.hamcrest.core.AnyOf.anyOf
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)

class DateHelperTest{

    /** setting the week of month only once works */
    @Test
    fun shouldReturnCorrectStartOfWeek(){

        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, 2018)
        val yearInformation = cal.time

        val weeknumber = 8 //Sunday = 18.02, Monday = 19.02

        val result = DateHelper.getStartOfWeek(yearInformation, weeknumber)
        cal.time = result
        val acualDay = cal.get(Calendar.DAY_OF_MONTH)

        assertThat(acualDay, anyOf(equalTo(18), equalTo(19)))
    }

    /**
     * setting the week of month twice (calling DateHelper.getStartOfWeek multiple times)
     * doesn't work (for API level <= 23)
     */
    @Test
    fun shouldChangeStartOfWeekCorrectly(){
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, 2018)
        val yearInformation = cal.time

        var weeknumber = 8 //Sunday = 18.02, Monday = 19.02
        DateHelper.getStartOfWeek(yearInformation, weeknumber) //initial call

        weeknumber = 9 //incremented, Sunday = 25, Monday = 26 now
        val result = DateHelper.getStartOfWeek(yearInformation, weeknumber) //second, tested call
        cal.time = result
        val actualDay = cal.get(Calendar.DAY_OF_MONTH)
        assertThat(actualDay, anyOf(equalTo(25), equalTo(26)))
    }

    /**
     * same as shouldChangeStartOfWeekCorrectly() but with workaround
     * (internally calling cal.get())
     * */
    @Test
    fun shouldChangeStartOfWeekCorrectly_WithWorkaround(){
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, 2018)
        val yearInformation = cal.time

        var weeknumber = 8 //Sunday = 18.02, Monday = 19.02
        DateHelper.getStartOfWeek(yearInformation, weeknumber) //initial call

        weeknumber = 9 //incremented, Sunday = 25, Monday = 26 now
        val result = DateHelper.getStartOfWeek(yearInformation, weeknumber, applyFix = true) //second, tested call
        cal.time = result
        val actualDay = cal.get(Calendar.DAY_OF_MONTH)
        assertThat(actualDay, anyOf(equalTo(25), equalTo(26)))
    }
}