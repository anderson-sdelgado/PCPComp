package br.com.usinasantafe.pcpcomp.di

import br.com.usinasantafe.pcpcomp.presenter.configuration.senha.SenhaViewModel
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.ConfigViewModel
import br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial.MenuInicialViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia.MatricVigiaViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.nomevigia.NomeVigiaViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.local.LocalViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.menuapont.MenuApontViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.movlist.MovEquipProprioListViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist.PassagColabListViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip.NroEquipProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.equipseglist.EquipSegListViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.destino.DestinoProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal.NotaFiscalViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.observ.ObservProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe.DetalheProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe.DetalheResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.motorista.MotoristaResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.moveditlist.MovEquipResidenciaEditListViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.movlist.MovEquipResidenciaListViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.observ.ObservResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.placa.PlacaResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.veiculo.VeiculoResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.splash.SplashViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.cpf.CpfVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.destino.DestinoVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe.DetalheVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.moveditlist.MovEquipVisitTercEditListViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.movlist.MovEquipVisitTercListViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.nome.NomeVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.observ.ObservVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.passaglist.PassagVisitTercListViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.placa.PlacaVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.tipo.TipoVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.veiculo.VeiculoVisitTercViewModel
import br.com.usinasantafe.pcpcomp.domain.usecases.background.*
import br.com.usinasantafe.pcpcomp.domain.usecases.config.*
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.*
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.*
import br.com.usinasantafe.pcpcomp.domain.usecases.common.*
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.*
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.*
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.*
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.*
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.*
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.*
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.*
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.*
import br.com.usinasantafe.pcpcomp.infra.repositories.variable.*
import br.com.usinasantafe.pcpcomp.infra.repositories.stable.*
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.*
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.*
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.*
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.*
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.*
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource.*
import br.com.usinasantafe.pcpcomp.external.room.datasource.stable.*
import br.com.usinasantafe.pcpcomp.external.room.datasource.variable.*
import br.com.usinasantafe.pcpcomp.external.retrofit.datasource.variable.*
import br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable.*
import br.com.usinasantafe.pcpcomp.external.retrofit.api.variable.*
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.*
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.providerSharedPreferences
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofit
import br.com.usinasantafe.pcpcomp.external.room.provideRoom
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.workmanager.dsl.workerOf

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val viewModelConfigModule = module {
    viewModelOf(::MenuInicialViewModel)
    viewModelOf(::SenhaViewModel)
    viewModelOf(::ConfigViewModel)
}

val viewModelInicialModule = module {
    viewModelOf(::MatricVigiaViewModel)
    viewModelOf(::NomeVigiaViewModel)
    viewModelOf(::LocalViewModel)
    viewModelOf(::MenuApontViewModel)
}

val viewModelSplashModule = module {
    viewModelOf(::SplashViewModel)
}

val viewModelProprioModule = module {
    viewModelOf(::DestinoProprioViewModel)
    viewModelOf(::DetalheProprioViewModel)
    viewModelOf(::EquipSegListViewModel)
    viewModelOf(::MatricColabViewModel)
    viewModelOf(::MovEquipProprioListViewModel)
    viewModelOf(::NomeColabViewModel)
    viewModelOf(::NotaFiscalViewModel)
    viewModelOf(::NroEquipProprioViewModel)
    viewModelOf(::ObservProprioViewModel)
    viewModelOf(::PassagColabListViewModel)
}

val viewModelResidenciaModule = module {
    viewModelOf(::DetalheResidenciaViewModel)
    viewModelOf(::MotoristaResidenciaViewModel)
    viewModelOf(::MovEquipResidenciaEditListViewModel)
    viewModelOf(::MovEquipResidenciaListViewModel)
    viewModelOf(::ObservResidenciaViewModel)
    viewModelOf(::PlacaResidenciaViewModel)
    viewModelOf(::VeiculoResidenciaViewModel)
}

val viewModelVisitTercModule = module {
    viewModelOf(::CpfVisitTercViewModel)
    viewModelOf(::DestinoVisitTercViewModel)
    viewModelOf(::DetalheVisitTercViewModel)
    viewModelOf(::MovEquipVisitTercEditListViewModel)
    viewModelOf(::MovEquipVisitTercListViewModel)
    viewModelOf(::NomeVisitTercViewModel)
    viewModelOf(::ObservVisitTercViewModel)
    viewModelOf(::PassagVisitTercListViewModel)
    viewModelOf(::PlacaVisitTercViewModel)
    viewModelOf(::TipoVisitTercViewModel)
    viewModelOf(::VeiculoVisitTercViewModel)
}

val usecaseBackgroundModule = module {
    singleOf(::StartProcessSendDataImpl) { bind<StartProcessSendData>() }
}

val usecaseCommonModule = module {
    singleOf(::CheckMatricColabImpl) { bind<CheckMatricColab>() }
    singleOf(::CloseAllMovImpl) { bind<CloseAllMov>() }
    singleOf(::GetHeaderImpl) { bind<GetHeader>() }
    singleOf(::GetTokenImpl) { bind<GetToken>() }
}

val usecaseConfigModule = module {
    singleOf(::CheckPasswordConfigImpl) { bind<CheckPasswordConfig>() }
    singleOf(::GetConfigInternalImpl) { bind<GetConfigInternal>() }
    singleOf(::SendDataConfigImpl) { bind<SendDataConfig>() }
    singleOf(::SaveDataConfigImpl) { bind<SaveDataConfig>() }
    singleOf(::SetCheckUpdateAllTableImpl) { bind<SetCheckUpdateAllTable>() }
    singleOf(::SetIdLocalConfigImpl) { bind<SetIdLocalConfig>() }
    singleOf(::SetMatricVigiaConfigImpl) { bind<SetMatricVigiaConfig>() }
}

val usecaseInitialModule = module {
    singleOf(::AdjustConfigImpl) { bind<AdjustConfig>() }
    singleOf(::CheckAccessMainImpl) { bind<CheckAccessMain>() }
    singleOf(::CheckMovOpenImpl) { bind<CheckMovOpen>() }
    singleOf(::DeleteMovSentImpl) { bind<DeleteMovSent>() }
    singleOf(::GetLocalListImpl) { bind<GetLocalList>() }
    singleOf(::GetNomeVigiaImpl) { bind<GetNomeVigia>() }
}

val usecaseProprioModule = module {
    singleOf(::CheckNroEquipProprioImpl) { bind<CheckNroEquipProprio>() }
    singleOf(::CheckSendMovProprioImpl) { bind<CheckSendMovProprio>() }
    singleOf(::CleanEquipSegImpl) { bind<CleanEquipSeg>() }
    singleOf(::CleanPassagColabImpl) { bind<CleanPassagColab>() }
    singleOf(::CloseAllMovProprioImpl) { bind<CloseAllMovProprio>() }
    singleOf(::CloseMovProprioImpl) { bind<CloseMovProprio>() }
    singleOf(::DeleteEquipSegImpl) { bind<DeleteEquipSeg>() }
    singleOf(::DeletePassagColabImpl) { bind<DeletePassagColab>() }
    singleOf(::GetDestinoProprioImpl) { bind<GetDestinoProprio>() }
    singleOf(::GetDetalheProprioImpl) { bind<GetDetalheProprio>() }
    singleOf(::GetEquipSegListImpl) { bind<GetEquipSegList>() }
    singleOf(::GetMatricColabImpl) { bind<GetMatricColab>() }
    singleOf(::GetMovEquipProprioOpenListImpl) { bind<GetMovEquipProprioOpenList>() }
    singleOf(::GetNomeColabImpl) { bind<GetNomeColab>() }
    singleOf(::GetNotaFiscalProprioImpl) { bind<GetNotaFiscalProprio>() }
    singleOf(::GetNroEquipImpl) { bind<GetNroEquip>() }
    singleOf(::GetObservProprioImpl) { bind<GetObservProprio>() }
    singleOf(::GetPassagColabListImpl) { bind<GetPassagColabList>() }
    singleOf(::GetTypeMovImpl) { bind<GetTypeMov>() }
    singleOf(::SaveMovEquipProprioImpl) { bind<SaveMovEquipProprio>() }
    singleOf(::SendMovProprioListImpl) { bind<SendMovProprioList>() }
    singleOf(::SetDestinoProprioImpl) { bind<SetDestinoProprio>() }
    singleOf(::SetMatricColabImpl) { bind<SetMatricColab>() }
    singleOf(::SetNotaFiscalProprioImpl) { bind<SetNotaFiscalProprio>() }
    singleOf(::SetNroEquipImpl) { bind<SetNroEquip>() }
    singleOf(::SetObservProprioImpl) { bind<SetObservProprio>() }
    singleOf(::SetStatusSentMovProprioImpl) { bind<SetStatusSentMovProprio>() }
    singleOf(::StartMovEquipProprioImpl) { bind<StartMovEquipProprio>() }
}

val usecaseVisitTercModule = module {
    singleOf(::CheckCpfVisitTercImpl) { bind<CheckCpfVisitTerc>() }
    singleOf(::CheckSendMovVisitTercImpl) { bind<CheckSendMovVisitTerc>() }
    singleOf(::CleanPassagVisitTercImpl) { bind<CleanPassagVisitTerc>() }
    singleOf(::CloseAllMovVisitTercImpl) { bind<CloseAllMovVisitTerc>() }
    singleOf(::CloseMovVisitTercImpl) { bind<CloseMovVisitTerc>() }
    singleOf(::DeletePassagVisitTercImpl) { bind<DeletePassagVisitTerc>() }
    singleOf(::GetCpfVisitTercImpl) { bind<GetCpfVisitTerc>() }
    singleOf(::GetDestinoVisitTercImpl) { bind<GetDestinoVisitTerc>() }
    singleOf(::GetDetalheVisitTercImpl) { bind<GetDetalheVisitTerc>() }
    singleOf(::GetMotoristaVisitTercImpl) { bind<GetMotoristaVisitTerc>() }
    singleOf(::GetMovEquipVisitTercInsideListImpl) { bind<GetMovEquipVisitTercInsideList>() }
    singleOf(::GetMovEquipVisitTercOpenListImpl) { bind<GetMovEquipVisitTercOpenList>() }
    singleOf(::GetNomeVisitTercImpl) { bind<GetNomeVisitTerc>() }
    singleOf(::GetObservVisitTercImpl) { bind<GetObservVisitTerc>() }
    singleOf(::GetPassagVisitTercListImpl) { bind<GetPassagVisitTercList>() }
    singleOf(::GetPlacaVisitTercImpl) { bind<GetPlacaVisitTerc>() }
    singleOf(::GetTitleCpfVisitTercImpl) { bind<GetTitleCpfVisitTerc>() }
    singleOf(::GetVeiculoVisitTercImpl) { bind<GetVeiculoVisitTerc>() }
    singleOf(::SetStatusOutsideMovVisitTercImpl) { bind<SetStatusOutsideMovVisitTerc>() }
    singleOf(::SaveMovEquipVisitTercImpl) { bind<SaveMovEquipVisitTerc>() }
    singleOf(::SendMovVisitTercListImpl) { bind<SendMovVisitTercList>() }
    singleOf(::SetDestinoVisitTercImpl) { bind<SetDestinoVisitTerc>() }
    singleOf(::SetIdVisitTercImpl) { bind<SetIdVisitTerc>() }
    singleOf(::SetObservVisitTercImpl) { bind<SetObservVisitTerc>() }
    singleOf(::SetPlacaVisitTercImpl) { bind<SetPlacaVisitTerc>() }
    singleOf(::SetStatusSentMovVisitTercImpl) { bind<SetStatusSentMovVisitTerc>() }
    singleOf(::SetTipoVisitTercImpl) { bind<SetTipoVisitTerc>() }
    singleOf(::SetVeiculoVisitTercImpl) { bind<SetVeiculoVisitTerc>() }
    singleOf(::StartInputMovEquipVisitTercImpl) { bind<StartInputMovEquipVisitTerc>() }
    singleOf(::StartOutputMovEquipVisitTercImpl) { bind<StartOutputMovEquipVisitTerc>() }
}

val usecaseResidenciaModule = module {
    singleOf(::CheckSendMovResidenciaImpl) { bind<CheckSendMovResidencia>() }
    singleOf(::CloseAllMovResidenciaImpl) { bind<CloseAllMovResidencia>() }
    singleOf(::CloseMovResidenciaImpl) { bind<CloseMovResidencia>() }
    singleOf(::GetDetalheResidenciaImpl) { bind<GetDetalheResidencia>() }
    singleOf(::GetMotoristaResidenciaImpl) { bind<GetMotoristaResidencia>() }
    singleOf(::GetMovEquipResidenciaInsideListImpl) { bind<GetMovEquipResidenciaInsideList>() }
    singleOf(::GetMovEquipResidenciaOpenListImpl) { bind<GetMovEquipResidenciaOpenList>() }
    singleOf(::GetObservResidenciaImpl) { bind<GetObservResidencia>() }
    singleOf(::GetPlacaResidenciaImpl) { bind<GetPlacaResidencia>() }
    singleOf(::GetVeiculoResidenciaImpl) { bind<GetVeiculoResidencia>() }
    singleOf(::SetStatusOutsideMovResidenciaImpl) { bind<SetStatusOutsideMovResidencia>() }
    singleOf(::SaveMovEquipResidenciaImpl) { bind<SaveMovEquipResidencia>() }
    singleOf(::SendMovResidenciaListImpl) { bind<SendMovResidenciaList>() }
    singleOf(::SetMotoristaResidenciaImpl) { bind<SetMotoristaResidencia>() }
    singleOf(::SetObservResidenciaImpl) { bind<SetObservResidencia>() }
    singleOf(::SetPlacaResidenciaImpl) { bind<SetPlacaResidencia>() }
    singleOf(::SetStatusSentMovResidenciaImpl) { bind<SetStatusSentMovResidencia>() }
    singleOf(::SetVeiculoResidenciaImpl) { bind<SetVeiculoResidencia>() }
    singleOf(::StartInputMovEquipResidenciaImpl) { bind<StartInputMovEquipResidencia>() }
    singleOf(::StartOutputMovEquipResidenciaImpl) { bind<StartOutputMovEquipResidencia>() }
}

val usecaseUpdateModule = module {
    singleOf(::UpdateColabImpl) { bind<UpdateColab>() }
    singleOf(::UpdateEquipImpl) { bind<UpdateEquip>() }
    singleOf(::UpdateFluxoImpl) { bind<UpdateFluxo>() }
    singleOf(::UpdateLocalImpl) { bind<UpdateLocal>() }
    singleOf(::UpdateRLocalFluxoImpl) { bind<UpdateRLocalFluxo>() }
    singleOf(::UpdateTerceiroImpl) { bind<UpdateTerceiro>() }
    singleOf(::UpdateVisitanteImpl) { bind<UpdateVisitante>() }
}

/////////////////////////////////////////////////////////////////////////////////////////////

val usecaseCleanTableModule = module {
    singleOf(::CleanColabImpl) { bind<CleanColab>() }
    singleOf(::CleanEquipImpl) { bind<CleanEquip>() }
    singleOf(::CleanFluxoImpl) { bind<CleanFluxo>() }
    singleOf(::CleanLocalImpl) { bind<CleanLocal>() }
    singleOf(::CleanRLocalFluxoImpl) { bind<CleanRLocalFluxo>() }
    singleOf(::CleanTerceiroImpl) { bind<CleanTerceiro>() }
    singleOf(::CleanVisitanteImpl) { bind<CleanVisitante>() }
}

val usecaseRecoverServerModule = module {
    singleOf(::GetAllColabServerImpl) { bind<GetAllColabServer>() }
    singleOf(::GetAllEquipServerImpl) { bind<GetAllEquipServer>() }
    singleOf(::GetAllFluxoServerImpl) { bind<GetAllFluxoServer>() }
    singleOf(::GetAllLocalServerImpl) { bind<GetAllLocalServer>() }
    singleOf(::GetAllRLocalFluxoServerImpl) { bind<GetAllRLocalFluxoServer>() }
    singleOf(::GetAllTerceiroServerImpl) { bind<GetAllTerceiroServer>() }
    singleOf(::GetAllVisitanteServerImpl) { bind<GetAllVisitanteServer>() }
}

val usecaseSaveAllTableModule = module {
    singleOf(::SaveAllColabImpl) { bind<SaveAllColab>() }
    singleOf(::SaveAllEquipImpl) { bind<SaveAllEquip>() }
    singleOf(::SaveAllFluxoImpl) { bind<SaveAllFluxo>() }
    singleOf(::SaveAllLocalImpl) { bind<SaveAllLocal>() }
    singleOf(::SaveAllRLocalFluxoImpl) { bind<SaveAllRLocalFluxo>() }
    singleOf(::SaveAllTerceiroImpl) { bind<SaveAllTerceiro>() }
    singleOf(::SaveAllVisitanteImpl) { bind<SaveAllVisitante>() }
}

/////////////////////////////////////////////////////////////////////////////////////////////

val repositoryModule = module {

    singleOf(::ConfigRepositoryImpl) { bind<ConfigRepository>() }
    singleOf(::MovEquipProprioRepositoryImpl) { bind<MovEquipProprioRepository>() }
    singleOf(::MovEquipProprioPassagRepositoryImpl) { bind<MovEquipProprioPassagRepository>() }
    singleOf(::MovEquipProprioEquipSegRepositoryImpl) { bind<MovEquipProprioEquipSegRepository>() }
    singleOf(::MovEquipVisitTercRepositoryImpl) { bind<MovEquipVisitTercRepository>() }
    singleOf(::MovEquipVisitTercPassagRepositoryImpl) { bind<MovEquipVisitTercPassagRepository>() }
    singleOf(::MovEquipResidenciaRepositoryImpl) { bind<MovEquipResidenciaRepository>() }

    singleOf(::ColabRepositoryImpl) { bind<ColabRepository>() }
    singleOf(::EquipRepositoryImpl) { bind<EquipRepository>() }
    singleOf(::FluxoRepositoryImpl) { bind<FluxoRepository>() }
    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }
    singleOf(::RLocalFluxoRepositoryImpl) { bind<RLocalFluxoRepository>() }
    singleOf(::TerceiroRepositoryImpl) { bind<TerceiroRepository>() }
    singleOf(::VisitanteRepositoryImpl) { bind<VisitanteRepository>() }

}

val datasourceSharedPreferencesModule = module {

    singleOf(::ConfigSharedPreferencesDatasourceImpl) { bind<ConfigSharedPreferencesDatasource>() }
    singleOf(::MovEquipProprioSharedPreferencesDatasourceImpl) { bind<MovEquipProprioSharedPreferencesDatasource>() }
    singleOf(::MovEquipProprioEquipSegSharedPreferencesDatasourceImpl) { bind<MovEquipProprioEquipSegSharedPreferencesDatasource>() }
    singleOf(::MovEquipProprioPassagSharedPreferencesDatasourceImpl) { bind<MovEquipProprioPassagSharedPreferencesDatasource>() }
    singleOf(::MovEquipVisitTercSharedPreferencesDatasourceImpl) { bind<MovEquipVisitTercSharedPreferencesDatasource>() }
    singleOf(::MovEquipVisitTercPassagSharedPreferencesDatasourceImpl) { bind<MovEquipVisitTercPassagSharedPreferencesDatasource>() }
    singleOf(::MovEquipResidenciaSharedPreferencesDatasourceImpl) { bind<MovEquipResidenciaSharedPreferencesDatasource>() }

}

val datasourceRoomModule = module {

    singleOf(::MovEquipProprioRoomDatasourceImpl) { bind<MovEquipProprioRoomDatasource>() }
    singleOf(::MovEquipProprioPassagRoomDatasourceImpl) { bind<MovEquipProprioPassagRoomDatasource>() }
    singleOf(::MovEquipProprioEquipSegRoomDatasourceImpl) { bind<MovEquipProprioEquipSegRoomDatasource>() }
    singleOf(::MovEquipVisitTercRoomDatasourceImpl) { bind<MovEquipVisitTercRoomDatasource>() }
    singleOf(::MovEquipResidenciaRoomDatasourceImpl) { bind<MovEquipResidenciaRoomDatasource>() }
    singleOf(::MovEquipVisitTercPassagRoomDatasourceImpl) { bind<MovEquipVisitTercPassagRoomDatasource>() }

    singleOf(::ColabRoomDatasourceImpl) { bind<ColabRoomDatasource>() }
    singleOf(::EquipRoomDatasourceImpl) { bind<EquipRoomDatasource>() }
    singleOf(::FluxoRoomDatasourceImpl) { bind<FluxoRoomDatasource>() }
    singleOf(::LocalRoomDatasourceImpl) { bind<LocalRoomDatasource>() }
    singleOf(::RLocalFluxoRoomDatasourceImpl) { bind<RLocalFluxoRoomDatasource>() }
    singleOf(::TerceiroRoomDatasourceImpl) { bind<TerceiroRoomDatasource>() }
    singleOf(::VisitanteRoomDatasourceImpl) { bind<VisitanteRoomDatasource>() }

}

val datasourceRetrofitModule = module {

    singleOf(::ConfigRetrofitDatasourceImpl) { bind<ConfigRetrofitDatasource>() }
    singleOf(::MovEquipProprioRetrofitDatasourceImpl) { bind<MovEquipProprioRetrofitDatasource>() }
    singleOf(::MovEquipVisitTercRetrofitDatasourceImpl) { bind<MovEquipVisitTercRetrofitDatasource>() }
    singleOf(::MovEquipResidenciaRetrofitDatasourceImpl) { bind<MovEquipResidenciaRetrofitDatasource>() }

    singleOf(::ColabRetrofitDatasourceImpl) { bind<ColabRetrofitDatasource>() }
    singleOf(::EquipRetrofitDatasourceImpl) { bind<EquipRetrofitDatasource>() }
    singleOf(::FluxoRetrofitDatasourceImpl) { bind<FluxoRetrofitDatasource>() }
    singleOf(::LocalRetrofitDatasourceImpl) { bind<LocalRetrofitDatasource>() }
    singleOf(::RLocalFluxoRetrofitDatasourceImpl) { bind<RLocalFluxoRetrofitDatasource>() }
    singleOf(::TerceiroRetrofitDatasourceImpl) { bind<TerceiroRetrofitDatasource>() }
    singleOf(::VisitanteRetrofitDatasourceImpl) { bind<VisitanteRetrofitDatasource>() }

}

val apiRetrofitModule = module {

    single { get<Retrofit>().create(ConfigApi::class.java) }
    single { get<Retrofit>().create(MovEquipProprioApi::class.java) }
    single { get<Retrofit>().create(MovEquipVisitTercApi::class.java) }
    single { get<Retrofit>().create(MovEquipResidenciaApi::class.java) }

    single { get<Retrofit>().create(ColabApi::class.java) }
    single { get<Retrofit>().create(EquipApi::class.java) }
    single { get<Retrofit>().create(FluxoApi::class.java) }
    single { get<Retrofit>().create(LocalApi::class.java) }
    single { get<Retrofit>().create(RLocalFluxoApi::class.java) }
    single { get<Retrofit>().create(TerceiroApi::class.java) }
    single { get<Retrofit>().create(VisitanteApi::class.java) }

}

val apiRoomModule = module {

    single { get<AppDatabaseRoom>().colabDao() }
    single { get<AppDatabaseRoom>().equipDao() }
    single { get<AppDatabaseRoom>().fluxoDao() }
    single { get<AppDatabaseRoom>().localDao() }
    single { get<AppDatabaseRoom>().rLocalFluxoDao() }
    single { get<AppDatabaseRoom>().rLocalFluxoDao() }
    single { get<AppDatabaseRoom>().terceiroDao() }
    single { get<AppDatabaseRoom>().visitanteDao() }

    single { get<AppDatabaseRoom>().movEquipProprioDao() }
    single { get<AppDatabaseRoom>().movEquipProprioPassagDao() }
    single { get<AppDatabaseRoom>().movEquipProprioEquipSegDao() }
    single { get<AppDatabaseRoom>().movEquipVisitTercDao() }
    single { get<AppDatabaseRoom>().movEquipVisitTercPassagDao() }
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

val workManagerModule = module {
    single { providerWorkManager(androidContext()) }
    workerOf(::ProcessWorkManager) { named<ProcessWorkManager>() }
}