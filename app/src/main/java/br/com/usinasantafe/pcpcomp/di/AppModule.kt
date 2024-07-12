package br.com.usinasantafe.pcpcomp.di

import br.com.usinasantafe.pcpcomp.external.room.variable.ConfigWebServiceDatasourceImpl
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.ConfigSharedPreferencesDatasourceImpl
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.infra.repositories.variable.ConfigRepositoryImpl
import br.com.usinasantafe.pcpcomp.domain.usecases.*
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.SharedPreferences
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.ConfigWebServiceDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.presenter.senha.SenhaViewModel
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val datasourceModule = module {
    singleOf(::ConfigWebServiceDatasourceImpl) { bind<ConfigWebServiceDatasource>() }
    singleOf(::ConfigSharedPreferencesDatasourceImpl) { bind<ConfigSharedPreferencesDatasource>() }
}

val repositoryModule = module {
    singleOf(::ConfigRepositoryImpl) { bind<ConfigRepository>() }
}

val usecaseModule = module {
    singleOf(::CheckPasswordConfigImpl) { bind<CheckPasswordConfig>() }
    singleOf(::RecoverConfigImpl) { bind<RecoverConfig>() }
}

val viewModelModule = module {
    viewModelOf(::SenhaViewModel)
    viewModelOf(::ConfigViewModel)
}

val sharedModule = module {
    single { SharedPreferences(androidContext()) }
}
