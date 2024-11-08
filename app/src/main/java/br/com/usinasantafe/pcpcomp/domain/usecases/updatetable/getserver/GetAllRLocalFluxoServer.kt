package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.RLocalFluxoRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetToken

interface GetAllRLocalFluxoServer {
    suspend operator fun invoke(): Result<List<RLocalFluxo>>
}

class GetAllRLocalFluxoServerImpl(
    private val getToken: GetToken,
    private val rLocalFluxoRepository: RLocalFluxoRepository
): GetAllRLocalFluxoServer {

    override suspend fun invoke(): Result<List<RLocalFluxo>> {
        try {
            val resultToken = getToken()
            if(resultToken.isFailure)
                return Result.failure(resultToken.exceptionOrNull()!!)
            val token = resultToken.getOrNull()!!
            val recoverAll = rLocalFluxoRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetAllRLocalFluxo",
                    cause = e
                )
            )
        }
    }

}