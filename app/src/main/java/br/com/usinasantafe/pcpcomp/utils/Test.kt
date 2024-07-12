package br.com.usinasantafe.pcpcomp.utils

import android.app.Instrumentation
import android.os.Bundle
import androidx.test.platform.app.InstrumentationRegistry

fun logToConsole(stringToPrint: String) {
    val bundle = Bundle()
    bundle.putString(
        Instrumentation.REPORT_KEY_STREAMRESULT, stringToPrint
    )
    InstrumentationRegistry.getInstrumentation().sendStatus(0, bundle)
}