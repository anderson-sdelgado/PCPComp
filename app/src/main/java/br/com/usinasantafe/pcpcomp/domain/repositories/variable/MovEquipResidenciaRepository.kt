package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia

interface MovEquipResidenciaRepository {
    suspend fun listOpen(): Result<List<MovEquipResidencia>>
    suspend fun setClose(movEquipResidencia: MovEquipResidencia): Result<Boolean>
}