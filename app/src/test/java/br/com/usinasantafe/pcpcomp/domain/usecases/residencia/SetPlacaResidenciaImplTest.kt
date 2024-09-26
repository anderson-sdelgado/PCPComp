package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetPlacaResidenciaImplTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository SetPlaca`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.setPlaca(
                    placa = "Placa",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.SetPlaca",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetPlacaResidenciaImpl(
                movEquipResidenciaRepository,
                startProcessSendData
            )
            val result = usecase(
                placa = "Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.SetPlaca"
            )
        }

    @Test
    fun `Check return true if SetPlacaResidenciaImpl execute successfully`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.setPlaca(
                    placa = "Placa",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetPlacaResidenciaImpl(
                movEquipResidenciaRepository,
                startProcessSendData
            )
            val result = usecase(
                placa = "Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}