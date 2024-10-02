package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

class MovEquipVisitTercSharedPreferencesDatasourceImpl(
): MovEquipVisitTercSharedPreferencesDatasource {

    override suspend fun clear(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun get(): Result<MovEquipVisitTercSharedPreferencesModel> {
        TODO("Not yet implemented")
    }

    override suspend fun setDestino(destino: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setIdVisitTerc(idVisitTerc: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setObserv(observ: String?): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setPlaca(placa: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setTipo(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setVeiculo(veiculo: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun start(
        movEquipVisitTercSharedPreferencesModel: MovEquipVisitTercSharedPreferencesModel
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }
}