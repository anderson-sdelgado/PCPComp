package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface SetTipoVisitTerc {
    suspend operator fun invoke(typeVisitTerc: TypeVisitTerc): Result<Boolean>
}

class SetTipoVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository
): SetTipoVisitTerc {

    override suspend fun invoke(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        return movEquipVisitTercRepository.setTipo(typeVisitTerc)
    }

}