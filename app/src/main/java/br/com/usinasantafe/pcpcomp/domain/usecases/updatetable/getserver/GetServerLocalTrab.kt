package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalTrabRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetToken

interface GetServerLocalTrab {
    suspend operator fun invoke(): Result<List<LocalTrab>>
}

class IGetServerLocalTrab(
    private val getToken: GetToken,
    private val localTrabRepository: LocalTrabRepository
): GetServerLocalTrab {

    override suspend fun invoke(): Result<List<LocalTrab>> {
        try {
            val resultToken = getToken()
            if(resultToken.isFailure)
                return Result.failure(resultToken.exceptionOrNull()!!)
            val token = resultToken.getOrNull()!!
            val recoverAll = localTrabRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetServerLocalTrab",
                    cause = e
                )
            )
        }
    }

}