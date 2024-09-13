package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface MovEquipProprioSharedPreferencesDatasource {
    suspend fun clear(): Result<Boolean>
    suspend fun get(): Result<MovEquipProprioSharedPreferencesModel>
    suspend fun setDestino(destino: String): Result<Boolean>
    suspend fun setIdEquip(idEquip: Int): Result<Boolean>
    suspend fun setNotaFiscal(notaFiscal: Int?): Result<Boolean>
    suspend fun setMatricColab(matric: Int): Result<Boolean>
    suspend fun setObserv(observ: String?): Result<Boolean>
    suspend fun start(typeMov: TypeMov): Result<Boolean>
}