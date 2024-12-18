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

class ISetMatricColabMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetMatricColabMovChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository,
        startProcessSendData = startProcessSendData
    )

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository setMatricColab - ADD`() =
        runTest {
            whenever(
                movChaveEquipRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.setMatricColab",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.setMatricColab"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveEquipRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                matricColab = "19759",
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