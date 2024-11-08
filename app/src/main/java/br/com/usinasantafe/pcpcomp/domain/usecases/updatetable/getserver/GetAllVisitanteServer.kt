package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetToken
import br.com.usinasantafe.pcpcomp.utils.token

interface GetAllVisitanteServer {
    suspend operator fun invoke(): Result<List<Visitante>>
}

class GetAllVisitanteServerImpl(
    private val getToken: GetToken,
    private val visitanteRepository: VisitanteRepository
): GetAllVisitanteServer {

    override suspend fun invoke(): Result<List<Visitante>> {
        try {
            val resultToken = getToken()
            if(resultToken.isFailure)
                return Result.failure(resultToken.exceptionOrNull()!!)
            val token = resultToken.getOrNull()!!
            val recoverAll = visitanteRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverVisitanteServer",
                    cause = e
                )
            )
        }
    }

}
