package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckSendMovVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository checkSend`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            whenever(
                movEquipVisitTercRepository.checkSend()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.checkSend",
                        cause = Exception()
                    )
                )
            )
            val usecase = CheckSendMovVisitTercImpl(movEquipVisitTercRepository)
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.checkSend"
            )
        }

    @Test
    fun `Check return true if have mov to send`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            whenever(
                movEquipVisitTercRepository.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = CheckSendMovVisitTercImpl(movEquipVisitTercRepository)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return false if not have mov to send`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            whenever(
                movEquipVisitTercRepository.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val usecase = CheckSendMovVisitTercImpl(movEquipVisitTercRepository)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}