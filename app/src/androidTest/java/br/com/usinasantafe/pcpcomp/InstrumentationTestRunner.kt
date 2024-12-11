package br.com.usinasantafe.pcpcomp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.runner.AndroidJUnitRunner
import androidx.work.Configuration
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitAndroidTest
import br.com.usinasantafe.pcpcomp.external.room.provideRoomTest
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApplication : Application(), KoinComponent, Configuration.Provider {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            workManagerFactory()
        }

    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}

fun generateTestAppComponent(baseTestApi: String) = listOf(
    sharedModuleTest,
    retrofitModuleTest(baseTestApi),
    roomModuleTest,
) + commonModuleList

val sharedModuleTest = module {
    single { sharedPreferencesTest(androidContext()) }
}

fun sharedPreferencesTest(appContext: Context): SharedPreferences {
    return appContext.getSharedPreferences("teste", Context.MODE_PRIVATE)
}

fun retrofitModuleTest(baseTestApi: String) = module {
    single { provideRetrofitAndroidTest(baseTestApi) }
}

val roomModuleTest = module {
    single { provideRoomTest(androidContext()) }
}

class InstrumentationTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, TestApplication::class.java.name, context)
    }
}
