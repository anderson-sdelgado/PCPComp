package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface GetTypeMov {
    suspend operator fun invoke(): Result<TypeMov>
}

class GetTypeMovImpl(): GetTypeMov {

    override suspend fun invoke(): Result<TypeMov> {
        TODO("Not yet implemented")
    }

}