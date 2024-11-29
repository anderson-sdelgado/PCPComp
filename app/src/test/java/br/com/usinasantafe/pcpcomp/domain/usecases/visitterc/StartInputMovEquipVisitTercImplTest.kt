package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class StartInputMovEquipVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository start`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.start",
                        cause = Exception()
                    )
                )
            )
            val usecase = IStartInputMovEquipVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.start"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository clear`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.clear()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.clear",
                        cause = Exception()
                    )
                )
            )
            val usecase = IStartInputMovEquipVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.clear"
            )
        }

    @Test
    fun `Check return true if StartMovEquipVisitTercImpl execute successfully`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.clear()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = IStartInputMovEquipVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

}