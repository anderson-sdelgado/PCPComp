package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetToken

interface GetAllFluxoServer {
    suspend operator fun invoke(): Result<List<Fluxo>>
}

class GetAllFluxoServerImpl(
    private val getToken: GetToken,
    private val fluxoRepository: FluxoRepository
): GetAllFluxoServer {

    override suspend fun invoke(): Result<List<Fluxo>> {
        try {
            val resultToken = getToken()
            if(resultToken.isFailure)
                return Result.failure(resultToken.exceptionOrNull()!!)
            val token = resultToken.getOrNull()!!
            val recoverAll = fluxoRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetAllFluxo",
                    cause = e
                )
            )
        }
    }

}