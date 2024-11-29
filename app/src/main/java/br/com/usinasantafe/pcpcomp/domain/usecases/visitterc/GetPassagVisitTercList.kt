package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.presenter.visitterc.passaglist.PassagVisitTercModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

interface GetPassagVisitTercList {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<PassagVisitTercModel>>
}

class IGetPassagVisitTercList(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val terceiroRepository: TerceiroRepository,
    private val visitanteRepository: VisitanteRepository
) : GetPassagVisitTercList {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<PassagVisitTercModel>> {
        try {
            val resultList = movEquipVisitTercPassagRepository.list(flowApp, id)
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val passagList = resultList.getOrNull()!!
            if (passagList.isEmpty())
                return Result.success(emptyList())
            val resultTypeVisitTerc = movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = flowApp,
                id = id
            )
            if (resultTypeVisitTerc.isFailure)
                return Result.failure(resultTypeVisitTerc.exceptionOrNull()!!)
            val typeVisitTerc = resultTypeVisitTerc.getOrNull()!!
            val passagColabList = passagList.map {
                when (typeVisitTerc) {
                    TypeVisitTerc.VISITANTE -> {
                        val resultCPF =
                            visitanteRepository.getCpf(it.idVisitTerc!!)
                        if (resultCPF.isFailure)
                            return Result.failure(resultCPF.exceptionOrNull()!!)
                        val cpf = resultCPF.getOrNull()!!
                        val resultNome = visitanteRepository.getNome(cpf)
                        if (resultNome.isFailure)
                            return Result.failure(resultNome.exceptionOrNull()!!)
                        val nome = resultNome.getOrNull()!!
                        PassagVisitTercModel(
                            id = it.idVisitTerc!!,
                            cpf = cpf,
                            nome = nome
                        )
                    }

                    TypeVisitTerc.TERCEIRO -> {
                        val resultCPF =
                            terceiroRepository.getCpf(it.idVisitTerc!!)
                        if (resultCPF.isFailure)
                            return Result.failure(resultCPF.exceptionOrNull()!!)
                        val cpf = resultCPF.getOrNull()!!
                        val resultNome = terceiroRepository.getNome(cpf)
                        if (resultNome.isFailure)
                            return Result.failure(resultNome.exceptionOrNull()!!)
                        val nome = resultNome.getOrNull()!!
                        PassagVisitTercModel(
                            id = it.idVisitTerc!!,
                            cpf = cpf,
                            nome = nome
                        )
                    }
                }
            }
            return Result.success(passagColabList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetPassagVisitTercListImpl",
                    cause = e
                )
            )
        }
    }

}