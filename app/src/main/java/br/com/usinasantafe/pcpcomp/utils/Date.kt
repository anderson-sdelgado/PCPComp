package br.com.usinasantafe.pcpcomp.utils

import java.util.Calendar
import java.util.Date

fun dateToDelete(): Date {
    val c: Calendar = Calendar.getInstance()
    c.time = Date()
    c.add(Calendar.DATE, -3)
    return c.time
}