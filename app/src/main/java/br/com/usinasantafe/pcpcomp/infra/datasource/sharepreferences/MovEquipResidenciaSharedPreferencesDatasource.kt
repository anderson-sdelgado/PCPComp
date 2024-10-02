package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel

interface MovEquipResidenciaSharedPreferencesDatasource {
    suspend fun clear(): Result<Boolean>
    suspend fun get(): Result<MovEquipResidenciaSharedPreferencesModel>
    suspend fun setMotorista(motorista: String): Result<Boolean>
    suspend fun setObserv(observ: String?): Result<Boolean>
    suspend fun setPlaca(placa: String): Result<Boolean>
    suspend fun setVeiculo(veiculo: String): Result<Boolean>
    suspend fun start(
        movEquipResidenciaSharedPreferencesModel: MovEquipResidenciaSharedPreferencesModel =
            MovEquipResidenciaSharedPreferencesModel()
    ): Result<Boolean>
}