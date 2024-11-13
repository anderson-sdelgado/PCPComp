package br.com.usinasantafe.pcpcomp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.runner.AndroidJUnitRunner
import androidx.work.Configuration
import androidx.work.WorkManager
import br.com.usinasantafe.pcpcomp.di.apiRetrofitModule
import br.com.usinasantafe.pcpcomp.di.apiRoomModule
import br.com.usinasantafe.pcpcomp.di.datasourceRetrofitModule
import br.com.usinasantafe.pcpcomp.di.datasourceRoomModule
import br.com.usinasantafe.pcpcomp.di.datasourceSharedPreferencesModule
import br.com.usinasantafe.pcpcomp.di.repositoryModule
import br.com.usinasantafe.pcpcomp.di.usecaseBackgroundModule
import br.com.usinasantafe.pcpcomp.di.usecaseCleanTableModule
import br.com.usinasantafe.pcpcomp.di.usecaseCommonModule
import br.com.usinasantafe.pcpcomp.di.usecaseConfigModule
import br.com.usinasantafe.pcpcomp.di.usecaseInitialModule
import br.com.usinasantafe.pcpcomp.di.usecaseProprioModule
import br.com.usinasantafe.pcpcomp.di.usecaseRecoverServerModule
import br.com.usinasantafe.pcpcomp.di.workManagerModule
import br.com.usinasantafe.pcpcomp.domain.usecases.background.ProcessWorkManager
import br.com.usinasantafe.pcpcomp.domain.usecases.background.providerWorkManager
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitAndroidTest
import br.com.usinasantafe.pcpcomp.external.room.provideRoomTest
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
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
