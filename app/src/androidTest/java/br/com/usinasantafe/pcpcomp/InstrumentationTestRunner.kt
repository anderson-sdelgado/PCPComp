package br.com.usinasantafe.pcpcomp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.runner.AndroidJUnitRunner
import br.com.usinasantafe.pcpcomp.di.apiRetrofitModule
import br.com.usinasantafe.pcpcomp.di.apiRoomModule
import br.com.usinasantafe.pcpcomp.di.datasourceRetrofitModule
import br.com.usinasantafe.pcpcomp.di.datasourceRoomModule
import br.com.usinasantafe.pcpcomp.di.datasourceSharedPreferencesModule
import br.com.usinasantafe.pcpcomp.di.repositoryModule
import br.com.usinasantafe.pcpcomp.di.usecaseModule
import br.com.usinasantafe.pcpcomp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
        }
    }
}

fun generateTestAppComponent(baseTestApi: String) = listOf(
    viewModelModule,
    usecaseModule,
    repositoryModule,
    datasourceSharedPreferencesModule,
    datasourceRoomModule,
    datasourceRetrofitModule,
    apiRetrofitModule,
    apiRoomModule,
    sharedModuleTest,
    retrofitModuleTest(baseTestApi),
    roomModuleTest,
)

fun retrofitModuleTest(baseTestApi: String) = module {
    single { provideRetrofitAndroidTest(baseTestApi) }
}

val sharedModuleTest = module {
    single { sharedPreferencesTest(androidContext()) }
}

fun sharedPreferencesTest(appContext: Context): SharedPreferences {
    return appContext.getSharedPreferences("teste", Context.MODE_PRIVATE)
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

val roomModuleTest = module {
    single { provideRoomTest(androidContext()) }
}
