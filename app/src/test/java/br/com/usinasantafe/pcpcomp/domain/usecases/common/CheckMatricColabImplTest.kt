package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CheckMatricColabImplTest {

    @Test
    fun `Check return failure if matric is incorrect`() = runTest {
        val colabRepository = mock<ColabRepository>()
        val usecase = CheckMatricColabImpl(colabRepository)
        val result = usecase("19759a")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> CheckMatricColab")
    }

    @Test
    fun `Check return failure if have error in Repository`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(colabRepository.checkMatric(19759)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "ColabRepository.checkMatric",
                    cause = Exception()
                )
            )
        )
        val usecase = CheckMatricColabImpl(colabRepository)
        val result = usecase("19759")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> ColabRepository.checkMatric")
    }

    @Test
    fun `Check return true in executed correctly`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(colabRepository.checkMatric(19759)).thenReturn(
            Result.success(true)
        )
        val usecase = CheckMatricColabImpl(colabRepository)
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Check return false in executed correctly`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(colabRepository.checkMatric(19759)).thenReturn(
            Result.success(false)
        )
        val usecase = CheckMatricColabImpl(colabRepository)
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, false)
    }
}