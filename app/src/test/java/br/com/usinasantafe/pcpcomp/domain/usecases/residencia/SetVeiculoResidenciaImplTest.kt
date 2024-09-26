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

class SetVeiculoResidenciaImplTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository SetVeiculo`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.setVeiculo(
                    veiculo = "Veiculo",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.SetVeiculo",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetVeiculoResidenciaImpl(
                movEquipResidenciaRepository,
                startProcessSendData
            )
            val result = usecase(
                veiculo = "Veiculo",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.SetVeiculo"
            )
        }

    @Test
    fun `Check return true if SetVeiculoResidenciaImpl execute successfully`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.setVeiculo(
                    veiculo = "Veiculo",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetVeiculoResidenciaImpl(
                movEquipResidenciaRepository,
                startProcessSendData
            )
            val result = usecase(
                veiculo = "Veiculo",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}