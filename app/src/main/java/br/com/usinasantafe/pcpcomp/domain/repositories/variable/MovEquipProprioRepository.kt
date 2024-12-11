package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip

interface MovEquipProprioRepository {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(id: Int): Result<Boolean>
    suspend fun get(id: Int): Result<MovEquipProprio>
    suspend fun getDestino(id: Int): Result<String>
    suspend fun getIdEquip(id: Int): Result<Int>
    suspend fun getMatricColab(id: Int): Result<Int>
    suspend fun getNotaFiscal(id: Int): Result<Int?>
    suspend fun getObserv(id: Int): Result<String?>
    suspend fun getTipoMov(): Result<TypeMovEquip>
    suspend fun listOpen(): Result<List<MovEquipProprio>>
    suspend fun listSend(): Result<List<MovEquipProprio>>
    suspend fun listSent(): Result<List<MovEquipProprio>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int>

    suspend fun send(
        list: List<MovEquipProprio>,
        number: Long,
        token: String
    ): Result<List<MovEquipProprio>>

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
        notaFiscal: Int?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setMatricColab(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setSent(
        list: List<MovEquipProprio>
    ): Result<Boolean>

    suspend fun setSend(id: Int): Result<Boolean>
    suspend fun start(typeMov: TypeMovEquip): Result<Boolean>
}