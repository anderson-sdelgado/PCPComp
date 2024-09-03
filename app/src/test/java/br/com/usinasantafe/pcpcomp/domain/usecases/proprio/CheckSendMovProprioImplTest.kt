package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckSendMovProprioImplTest {

    @Test
    fun `Check return failure if have error in Repository`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.checkSend()).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.checkSend",
                    cause = Exception()
                )
            )
        )
        val usecase = CheckSendMovProprioImpl(movEquipProprioRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.checkSend")
    }

    @Test
    fun `Check return true if have mov to send`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.checkSend()).thenReturn(
            Result.success(true)
        )
        val usecase = CheckSendMovProprioImpl(movEquipProprioRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return false if not have mov to send`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.checkSend()).thenReturn(
            Result.success(false)
        )
        val usecase = CheckSendMovProprioImpl(movEquipProprioRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

}