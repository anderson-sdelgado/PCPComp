package br.com.usinasantafe.pcpcomp

import android.app.Application
import androidx.work.Configuration
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
import br.com.usinasantafe.pcpcomp.di.usecaseChaveEquipModule
import br.com.usinasantafe.pcpcomp.di.usecaseChaveModule
import br.com.usinasantafe.pcpcomp.di.usecaseCleanTableModule
import br.com.usinasantafe.pcpcomp.di.usecaseCommonModule
import br.com.usinasantafe.pcpcomp.di.usecaseConfigModule
import br.com.usinasantafe.pcpcomp.di.usecaseInitialModule
import br.com.usinasantafe.pcpcomp.di.usecaseProprioModule
import br.com.usinasantafe.pcpcomp.di.usecaseRecoverServerModule
import br.com.usinasantafe.pcpcomp.di.usecaseResidenciaModule
import br.com.usinasantafe.pcpcomp.di.usecaseUpdateModule
import br.com.usinasantafe.pcpcomp.di.usecaseSaveAllTableModule
import br.com.usinasantafe.pcpcomp.di.usecaseVisitTercModule
import br.com.usinasantafe.pcpcomp.di.viewModelChaveEquipModule
import br.com.usinasantafe.pcpcomp.di.viewModelChaveModule
import br.com.usinasantafe.pcpcomp.di.viewModelConfigModule
import br.com.usinasantafe.pcpcomp.di.viewModelInicialModule
import br.com.usinasantafe.pcpcomp.di.viewModelProprioModule
import br.com.usinasantafe.pcpcomp.di.viewModelResidenciaModule
import br.com.usinasantafe.pcpcomp.di.viewModelSplashModule
import br.com.usinasantafe.pcpcomp.di.viewModelVisitTercModule
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
                    sharedPreferencesModule,
                    retrofitModule,
                    roomModule,
                ) + commonModuleList
            )
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}

val commonModuleList = listOf(
    viewModelConfigModule,
    viewModelInicialModule,
    viewModelSplashModule,
    viewModelChaveModule,
    viewModelChaveEquipModule,
    viewModelProprioModule,
    viewModelResidenciaModule,
    viewModelVisitTercModule,
    usecaseBackgroundModule,
    usecaseChaveModule,
    usecaseChaveEquipModule,
    usecaseCleanTableModule,
    usecaseCommonModule,
    usecaseConfigModule,
    usecaseInitialModule,
    usecaseProprioModule,
    usecaseVisitTercModule,
    usecaseResidenciaModule,
    usecaseUpdateModule,
    usecaseRecoverServerModule,
    usecaseSaveAllTableModule,
    repositoryModule,
    datasourceSharedPreferencesModule,
    datasourceRoomModule,
    datasourceRetrofitModule,
    apiRetrofitModule,
    apiRoomModule,
    workManagerModule,
)
