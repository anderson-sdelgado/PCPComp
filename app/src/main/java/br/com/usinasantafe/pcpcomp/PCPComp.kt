package br.com.usinasantafe.pcpcomp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import br.com.usinasantafe.pcpcomp.di.apiRetrofitModule
import br.com.usinasantafe.pcpcomp.di.apiRoomModule
import br.com.usinasantafe.pcpcomp.di.datasourceRetrofitModule
import br.com.usinasantafe.pcpcomp.di.datasourceRoomModule
import br.com.usinasantafe.pcpcomp.di.datasourceSharedPreferencesModule
import br.com.usinasantafe.pcpcomp.di.retrofitModule
import br.com.usinasantafe.pcpcomp.di.repositoryModule
import br.com.usinasantafe.pcpcomp.di.roomModule
import br.com.usinasantafe.pcpcomp.di.sharedPreferencesModule
import br.com.usinasantafe.pcpcomp.di.usecaseBackgroundModule
import br.com.usinasantafe.pcpcomp.di.usecaseCleanTableModule
import br.com.usinasantafe.pcpcomp.di.usecaseCommonModule
import br.com.usinasantafe.pcpcomp.di.usecaseConfigModule
import br.com.usinasantafe.pcpcomp.di.usecaseInitialModule
import br.com.usinasantafe.pcpcomp.di.usecaseProprioModule
import br.com.usinasantafe.pcpcomp.di.usecaseRecoverServerModule
import br.com.usinasantafe.pcpcomp.di.usecaseUpdateTableModule
import br.com.usinasantafe.pcpcomp.di.viewModelModule
import br.com.usinasantafe.pcpcomp.di.workManagerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class PCPComp : Application(), KoinComponent, Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PCPComp)
            workManagerFactory()
            modules(
                listOf(
                    viewModelModule,
                    usecaseBackgroundModule,
                    usecaseCleanTableModule,
                    usecaseCommonModule,
                    usecaseConfigModule,
                    usecaseInitialModule,
                    usecaseProprioModule,
                    usecaseRecoverServerModule,
                    usecaseUpdateTableModule,
                    repositoryModule,
                    datasourceSharedPreferencesModule,
                    datasourceRoomModule,
                    datasourceRetrofitModule,
                    apiRetrofitModule,
                    apiRoomModule,
                    sharedPreferencesModule,
                    retrofitModule,
                    roomModule,
                    workManagerModule
                )
            )
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}