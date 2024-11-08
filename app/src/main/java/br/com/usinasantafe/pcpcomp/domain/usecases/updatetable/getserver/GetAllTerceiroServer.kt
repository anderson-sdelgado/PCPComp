package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetToken
import br.com.usinasantafe.pcpcomp.utils.token

interface GetAllTerceiroServer {
    suspend operator fun invoke(): Result<List<Terceiro>>
}

class GetAllTerceiroServerImpl(
    private val getToken: GetToken,
    private val terceiroRepository: TerceiroRepository
): GetAllTerceiroServer {

    override suspend fun invoke(): Result<List<Terceiro>> {
        try {
            val resultToken = getToken()
            if(resultToken.isFailure)
                return Result.failure(resultToken.exceptionOrNull()!!)
            val token = resultToken.getOrNull()!!
            val recoverAll = terceiroRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverTerceiroServer",
                    cause = e
                )
            )
        }
    }

}
