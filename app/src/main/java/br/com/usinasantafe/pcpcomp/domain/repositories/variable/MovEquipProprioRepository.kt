package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface MovEquipProprioRepository {
    suspend fun getTipoMov(): Result<TypeMov>
    suspend fun listOpen(): Result<List<MovEquipProprio>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int>
    suspend fun setClose(movEquipProprio: MovEquipProprio): Result<Boolean>
    suspend fun setDestino(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setIdEquip(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setNotaFiscal(
        notaFiscal: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setMatricColab(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setObserv(
        observ: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun start(typeMov: TypeMov): Result<Boolean>
}