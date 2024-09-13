package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface SetTipoVisitTerc {
    suspend operator fun invoke(typeVisitTerc: TypeVisitTerc): Result<Boolean>
}

class SetTipoVisitTercImpl(): SetTipoVisitTerc {

    override suspend fun invoke(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        TODO("Not yet implemented")
    }

}