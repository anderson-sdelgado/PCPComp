package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository

interface CloseAllMovChave {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovChave(
    private val movChaveRepository: MovChaveRepository
): CloseAllMovChave {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultList = movChaveRepository.listOpen()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val entityList = resultList.getOrNull()!!
            for(entity in entityList){
                val resulClose = movChaveRepository.setClose(entity.idMovChave!!)
                if(resulClose.isFailure)
                    return Result.failure(resulClose.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "ICloseAllMovChave",
                    cause = e
                )
            )
        }
    }

}