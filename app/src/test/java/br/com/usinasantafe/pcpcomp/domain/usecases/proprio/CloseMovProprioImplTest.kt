package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CloseMovProprioImplTest {

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository get`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.get(1)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.get",
                    cause = Exception()
                )
            )
        )
        val usecase = CloseMovProprioImpl(
            movEquipProprioRepository,
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.get"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository setClose`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.get(1)).thenReturn(
            Result.success(movEquipProprio)
        )
        whenever(
            movEquipProprioRepository.setClose(
                movEquipProprio
            )
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.setClose",
                    cause = Exception()
                )
            )
        )
        val usecase = CloseMovProprioImpl(
            movEquipProprioRepository
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.setClose"
        )
    }

    @Test
    fun `Check return true if CloseMovProprioOpenImpl execute successfully`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.get(1)).thenReturn(
            Result.success(movEquipProprio)
        )
        whenever(
            movEquipProprioRepository.setClose(
                movEquipProprio
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = CloseMovProprioImpl(
            movEquipProprioRepository
        )
        val result = usecase(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}