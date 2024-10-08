package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckSendMovResidenciaImplTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository checkSend`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.checkSend()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.checkSend",
                        cause = Exception()
                    )
                )
            )
            val usecase = CheckSendMovResidenciaImpl(movEquipResidenciaRepository)
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.checkSend"
            )
        }

    @Test
    fun `Check return true if have mov to send`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = CheckSendMovResidenciaImpl(movEquipResidenciaRepository)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return false if not have mov to send`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val usecase = CheckSendMovResidenciaImpl(movEquipResidenciaRepository)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}