package de.thkoeln.calendarapibug

import java.util.*

class DateHelper{

    companion object {
        fun getStartOfWeek(yearInformation : Date, weekNumber: Int, applyFix: Boolean = false): Date {
            val cal = Calendar.getInstance()
            cal.time = yearInformation
            cal.set(Calendar.WEEK_OF_YEAR, weekNumber)
            if(applyFix) cal.get(Calendar.WEEK_OF_YEAR)
            cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
            return cal.time
        }
    }
}