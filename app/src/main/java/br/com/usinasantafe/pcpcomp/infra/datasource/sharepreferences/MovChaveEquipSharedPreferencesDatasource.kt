package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel

interface MovChaveEquipSharedPreferencesDatasource {
    suspend fun clear(): Result<Boolean>
    suspend fun get(): Result<MovChaveEquipSharedPreferencesModel>
    suspend fun setIdEquip(idEquip: Int): Result<Boolean>
    suspend fun setMatricColab(matricColab: Int): Result<Boolean>
    suspend fun setObserv(observ: String?): Result<Boolean>
    suspend fun start(
        movChaveSharedPreferencesModel: MovChaveEquipSharedPreferencesModel =
            MovChaveEquipSharedPreferencesModel()
    ): Result<Boolean>
}