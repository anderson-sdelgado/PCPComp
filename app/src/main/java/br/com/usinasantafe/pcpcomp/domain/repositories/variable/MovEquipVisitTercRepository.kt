package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface MovEquipVisitTercRepository {
    suspend fun get(id: Int): Result<MovEquipVisitTerc>
    suspend fun getDestino(id: Int): Result<String>
    suspend fun getIdVisitTerc(id: Int): Result<Int>
    suspend fun getObserv(id: Int): Result<String?>
    suspend fun getPlaca(id: Int): Result<String>
    suspend fun getTypeVisitTerc(
        flowApp: FlowApp,
        id: Int
    ): Result<TypeVisitTerc>

    suspend fun getVeiculo(id: Int): Result<String>
    suspend fun listOpen(): Result<List<MovEquipVisitTerc>>
    suspend fun listInside(): Result<List<MovEquipVisitTerc>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int>

    suspend fun setClose(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean>
    suspend fun setDestino(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setIdVisitTerc(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setOutside(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean>
    suspend fun setPlaca(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setTipoVisitTerc(
        typeVisitTerc: TypeVisitTerc
    ): Result<Boolean>

    suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun start(): Result<Boolean>
    suspend fun start(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean>
}