package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CloseAllMovVisitTercImplTest {

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository listOpen`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "MovEquipVisitTercRepository.listOpen",
                    cause = Exception()
                )
            )
        )
        val usecase = ICloseAllMovVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> MovEquipVisitTercRepository.listOpen"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository setClose`() = runTest {
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1
        )
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipVisitTerc
                )
            )
        )
        whenever(
            movEquipVisitTercRepository.setClose(
                movEquipVisitTerc
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "MovEquipVisitTercRepository.setClose",
                    cause = Exception()
                )
            )
        )
        val usecase = ICloseAllMovVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> MovEquipVisitTercRepository.setClose"
        )
    }

    @Test
    fun `Check return true if CloseAllMovVisitTercImpl execute successfully`() = runTest {
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1
        )
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipVisitTerc
                )
            )
        )
        whenever(
            movEquipVisitTercRepository.setClose(
                movEquipVisitTerc
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICloseAllMovVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}