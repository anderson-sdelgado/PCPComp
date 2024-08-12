package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface MovEquipProprioRepository {
    suspend fun start(typeMov: TypeMov): Result<Boolean>
    suspend fun listOpen(): Result<List<MovEquipProprio>>
    suspend fun setClose(movEquipProprio: MovEquipProprio): Result<Boolean>
}