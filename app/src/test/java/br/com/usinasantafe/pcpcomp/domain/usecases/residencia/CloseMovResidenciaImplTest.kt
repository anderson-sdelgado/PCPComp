package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class CloseMovResidenciaImplTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository Get`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.get",
                        cause = Exception()
                    )
                )
            )
            val usecase = CloseMovResidenciaImpl(
                movEquipResidenciaRepository
            )
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.get"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository SetClose`() =
        runTest {
            val mov = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "TESTE",
                veiculoMovEquipResidencia = "TESTE",
                placaMovEquipResidencia = "TESTE",
                observMovEquipResidencia = "TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(mov)
            )
            whenever(
                movEquipResidenciaRepository.setClose(mov)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.setClose",
                    )
                )
            )
            val usecase = CloseMovResidenciaImpl(
                movEquipResidenciaRepository
            )
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.setClose"
            )
        }

    @Test
    fun `Check return true if CloseMovResidenciaImpl execute successfully`() =
        runTest {
            val mov = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "TESTE",
                veiculoMovEquipResidencia = "TESTE",
                placaMovEquipResidencia = "TESTE",
                observMovEquipResidencia = "TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(mov)
            )
            whenever(
                movEquipResidenciaRepository.setClose(mov)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = CloseMovResidenciaImpl(
                movEquipResidenciaRepository
            )
            val result = usecase(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}