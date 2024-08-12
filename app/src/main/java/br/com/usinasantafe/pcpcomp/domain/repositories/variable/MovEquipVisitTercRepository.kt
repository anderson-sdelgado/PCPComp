package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc

interface MovEquipVisitTercRepository {
    suspend fun listOpen(): Result<List<MovEquipVisitTerc>>
    suspend fun setClose(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean>
}