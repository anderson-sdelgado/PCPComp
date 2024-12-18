package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetObservMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetObservMovChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository,
        startProcessSendData = startProcessSendData
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository SetObserv`() =
        runTest {
            whenever(
                movChaveEquipRepository.setObserv(
                    observ = "teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.setObserv",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(
                observ = "teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.setObserv"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRepository.setObserv(
                    observ = "teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                observ = "teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}