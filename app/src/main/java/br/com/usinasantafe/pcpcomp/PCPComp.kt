package br.com.usinasantafe.pcpcomp

import android.app.Application
import br.com.usinasantafe.pcpcomp.di.datasourceModule
import br.com.usinasantafe.pcpcomp.di.repositoryModule
import br.com.usinasantafe.pcpcomp.di.sharedModule
import br.com.usinasantafe.pcpcomp.di.usecaseModule
import br.com.usinasantafe.pcpcomp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PCPComp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PCPComp)
            modules(listOf(
                viewModelModule,
                usecaseModule,
                repositoryModule,
                datasourceModule,
                sharedModule
            ))
        }
    }
}