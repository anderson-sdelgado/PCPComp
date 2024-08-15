package br.com.usinasantafe.pcpcomp.di

import br.com.usinasantafe.pcpcomp.presenter.configuration.senha.SenhaViewModel
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.ConfigViewModel
import br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial.MenuInicialViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia.MatricVigiaViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.nomevigia.NomeVigiaViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.local.LocalViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.menuapont.MenuApontViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.movproprio.MovEquipProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabViewModel
import br.com.usinasantafe.pcpcomp.domain.usecases.config.*
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.*
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.*
import br.com.usinasantafe.pcpcomp.domain.usecases.common.*
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.*
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.*
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.*
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.*
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.*
import br.com.usinasantafe.pcpcomp.infra.repositories.variable.*
import br.com.usinasantafe.pcpcomp.infra.repositories.stable.*
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.*
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.*
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.*
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.*
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.variable.*
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource.*
import br.com.usinasantafe.pcpcomp.external.room.datasource.stable.*
import br.com.usinasantafe.pcpcomp.external.room.datasource.variable.*
import br.com.usinasantafe.pcpcomp.external.webservices.datasource.variable.*
import br.com.usinasantafe.pcpcomp.external.webservices.datasource.stable.*
import br.com.usinasantafe.pcpcomp.external.webservices.api.variable.*
import br.com.usinasantafe.pcpcomp.external.webservices.api.stable.*
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.providerSharedPreferences
import br.com.usinasantafe.pcpcomp.external.webservices.provideRetrofit
import br.com.usinasantafe.pcpcomp.external.room.provideRoom
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val viewModelModule = module {

    viewModelOf(::MenuInicialViewModel)
    viewModelOf(::SenhaViewModel)
    viewModelOf(::ConfigViewModel)
    viewModelOf(::MatricVigiaViewModel)
    viewModelOf(::NomeVigiaViewModel)
    viewModelOf(::LocalViewModel)
    viewModelOf(::MenuApontViewModel)
    viewModelOf(::MovEquipProprioViewModel)
    viewModelOf(::MatricColabViewModel)
    viewModelOf(::NomeColabViewModel)

}

val usecaseCleanTableModule = module {

    singleOf(::CleanColabImpl) { bind<CleanColab>() }
    singleOf(::CleanEquipImpl) { bind<CleanEquip>() }
    singleOf(::CleanLocalImpl) { bind<CleanLocal>() }
    singleOf(::CleanTerceiroImpl) { bind<CleanTerceiro>() }
    singleOf(::CleanVisitanteImpl) { bind<CleanVisitante>() }

}

val usecaseCommonModule = module {

    singleOf(::CheckMatricColabImpl) { bind<CheckMatricColab>() }
    singleOf(::CloseAllMovOpenImpl) { bind<CloseAllMovOpen>() }
    singleOf(::RecoverHeaderImpl) { bind<RecoverHeader>() }

}

val usecaseConfigModule = module {

    singleOf(::CheckPasswordConfigImpl) { bind<CheckPasswordConfig>() }
    singleOf(::RecoverConfigInternalImpl) { bind<RecoverConfigInternal>() }
    singleOf(::SendDataConfigImpl) { bind<SendDataConfig>() }
    singleOf(::SaveDataConfigImpl) { bind<SaveDataConfig>() }
    singleOf(::SetCheckUpdateAllTableImpl) { bind<SetCheckUpdateAllTable>() }
    singleOf(::SetIdLocalConfigImpl) { bind<SetIdLocalConfig>() }
    singleOf(::SetMatricVigiaConfigImpl) { bind<SetMatricVigiaConfig>() }

}

val usecaseInitialModule = module {

    singleOf(::CheckAccessMainImpl) { bind<CheckAccessMain>() }
    singleOf(::RecoverLocalsImpl) { bind<RecoverLocals>() }
    singleOf(::RecoverNomeVigiaImpl) { bind<RecoverNomeVigia>() }

}

val usecaseProprioModule = module {

    singleOf(::RecoverMovEquipProprioOpenListImpl) { bind<RecoverMovEquipProprioOpenList>() }
    singleOf(::RecoverNomeColabImpl) { bind<RecoverNomeColab>() }
    singleOf(::SetMatricColabImpl) { bind<SetMatricColab>() }
    singleOf(::StartMovEquipProprioImpl) { bind<StartMovEquipProprio>() }

}

val usecaseRecoverServerModule = module {

    singleOf(::RecoverColabServerImpl) { bind<RecoverColabServer>() }
    singleOf(::RecoverEquipServerImpl) { bind<RecoverEquipServer>() }
    singleOf(::RecoverLocalServerImpl) { bind<RecoverLocalServer>() }
    singleOf(::RecoverTerceiroServerImpl) { bind<RecoverTerceiroServer>() }
    singleOf(::RecoverVisitanteServerImpl) { bind<RecoverVisitanteServer>() }

}

val usecaseUpdateTableModule = module {

    singleOf(::SaveAllColabImpl) { bind<SaveAllColab>() }
    singleOf(::SaveAllEquipImpl) { bind<SaveAllEquip>() }
    singleOf(::SaveAllLocalImpl) { bind<SaveAllLocal>() }
    singleOf(::SaveAllTerceiroImpl) { bind<SaveAllTerceiro>() }
    singleOf(::SaveAllVisitanteImpl) { bind<SaveAllVisitante>() }

}

val repositoryModule = module {

    singleOf(::ConfigRepositoryImpl) { bind<ConfigRepository>() }
    singleOf(::MovEquipProprioRepositoryImpl) { bind<MovEquipProprioRepository>() }
    singleOf(::MovEquipVisitTercRepositoryImpl) { bind<MovEquipVisitTercRepository>() }
    singleOf(::MovEquipResidenciaRepositoryImpl) { bind<MovEquipResidenciaRepository>() }
    singleOf(::MovEquipProprioSegRepositoryImpl) { bind<MovEquipProprioSegRepository>() }
    singleOf(::MovEquipProprioPassagRepositoryImpl) { bind<MovEquipProprioPassagRepository>() }


    singleOf(::ColabRepositoryImpl) { bind<ColabRepository>() }
    singleOf(::EquipRepositoryImpl) { bind<EquipRepository>() }
    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }
    singleOf(::TerceiroRepositoryImpl) { bind<TerceiroRepository>() }
    singleOf(::VisitanteRepositoryImpl) { bind<VisitanteRepository>() }

}

val datasourceSharedPreferencesModule = module {

    singleOf(::ConfigSharedPreferencesDatasourceImpl) { bind<ConfigSharedPreferencesDatasource>() }
    singleOf(::MovEquipProprioSharedPreferencesDatasourceImpl) { bind<MovEquipProprioSharedPreferencesDatasource>() }
    singleOf(::MovEquipProprioSegSharedPreferencesDatasourceImpl) { bind<MovEquipProprioSegSharedPreferencesDatasource>() }
    singleOf(::MovEquipProprioPassagSharedPreferencesDatasourceImpl) { bind<MovEquipProprioPassagSharedPreferencesDatasource>() }

}

val datasourceRoomModule = module {

    singleOf(::MovEquipProprioRoomDatasourceImpl) { bind<MovEquipProprioRoomDatasource>() }
    singleOf(::MovEquipVisitTercRoomDatasourceImpl) { bind<MovEquipVisitTercRoomDatasource>() }
    singleOf(::MovEquipResidenciaRoomDatasourceImpl) { bind<MovEquipResidenciaRoomDatasource>() }

    singleOf(::ColabRoomDatasourceImpl) { bind<ColabRoomDatasource>() }
    singleOf(::EquipRoomDatasourceImpl) { bind<EquipRoomDatasource>() }
    singleOf(::LocalRoomDatasourceImpl) { bind<LocalRoomDatasource>() }
    singleOf(::TerceiroRoomDatasourceImpl) { bind<TerceiroRoomDatasource>() }
    singleOf(::VisitanteRoomDatasourceImpl) { bind<VisitanteRoomDatasource>() }

}

val datasourceRetrofitModule = module {

    singleOf(::ConfigRetrofitDatasourceImpl) { bind<ConfigRetrofitDatasource>() }

    singleOf(::ColabRetrofitDatasourceImpl) { bind<ColabRetrofitDatasource>() }
    singleOf(::EquipRetrofitDatasourceImpl) { bind<EquipRetrofitDatasource>() }
    singleOf(::LocalRetrofitDatasourceImpl) { bind<LocalRetrofitDatasource>() }
    singleOf(::TerceiroRetrofitDatasourceImpl) { bind<TerceiroRetrofitDatasource>() }
    singleOf(::VisitanteRetrofitDatasourceImpl) { bind<VisitanteRetrofitDatasource>() }

}

val apiRetrofitModule = module {
    single { get<Retrofit>().create(ConfigApi::class.java) }

    single { get<Retrofit>().create(ColabApi::class.java) }
    single { get<Retrofit>().create(EquipApi::class.java) }
    single { get<Retrofit>().create(LocalApi::class.java) }
    single { get<Retrofit>().create(TerceiroApi::class.java) }
    single { get<Retrofit>().create(VisitanteApi::class.java) }
}

val apiRoomModule = module {
    single { get<AppDatabaseRoom>().colabDao() }
    single { get<AppDatabaseRoom>().equipDao() }
    single { get<AppDatabaseRoom>().localDao() }
    single { get<AppDatabaseRoom>().terceiroDao() }
    single { get<AppDatabaseRoom>().visitanteDao() }
    single { get<AppDatabaseRoom>().movEquipProprioDao() }
    single { get<AppDatabaseRoom>().movEquipVisitTercDao() }
    single { get<AppDatabaseRoom>().movEquipResidenciaDao() }
}

val sharedPreferencesModule = module {
    single { providerSharedPreferences(androidContext()) }
}

val retrofitModule = module {
    single { provideRetrofit(androidContext()) }
}

val roomModule = module {
    single { provideRoom(androidContext()) }
}
