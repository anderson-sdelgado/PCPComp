package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcpcomp.presenter.chave.chavelist.ChaveModel

interface GetChaveList {
    suspend operator fun invoke(): Result<List<ChaveModel>>
}

class IGetChaveList(
    private val chaveRepository: ChaveRepository,
    private val getDescrFullChave: GetDescrFullChave
): GetChaveList {

    override suspend fun invoke(): Result<List<ChaveModel>> {
        try {
            val resultChaveList = chaveRepository.listAll()
            if(resultChaveList.isFailure){
                return Result.failure(
                    resultChaveList.exceptionOrNull()!!
                )
            }
            val entityList = resultChaveList.getOrNull()!!
            val modelScreenList = entityList.map {
                val resultDescr = getDescrFullChave(it.idChave)
                if(resultDescr.isFailure){
                    return Result.failure(
                        resultDescr.exceptionOrNull()!!
                    )
                }
                val descr = resultDescr.getOrNull()!!
                ChaveModel(
                    id = it.idChave,
                    descr = descr
                )
            }
            return Result.success(modelScreenList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetChaveList",
                    cause = e
                )
            )
        }
    }

}