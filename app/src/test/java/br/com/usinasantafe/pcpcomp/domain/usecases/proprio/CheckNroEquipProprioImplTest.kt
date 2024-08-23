package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CheckNroEquipProprioImplTest {

    @Test
    fun `Check return failure if matric is incorrect`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val usecase = CheckNroEquipProprioImpl(equipRepository)
        val result = usecase("100a")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> CheckEquipProprio")
    }

    @Test
    fun `Check return failure if have error in Repository`() = runTest {
        val equipRepository = mock<EquipRepository>()
        whenever(equipRepository.checkNro(100)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "EquipRepository.checkNro",
                    cause = Exception()
                )
            )
        )
        val usecase = CheckNroEquipProprioImpl(equipRepository)
        val result = usecase("100")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> EquipRepository.checkNro")
    }

    @Test
    fun `Check return true in executed correctly`() = runTest {
        val equipRepository = mock<EquipRepository>()
        whenever(equipRepository.checkNro(19759)).thenReturn(
            Result.success(true)
        )
        val usecase = CheckNroEquipProprioImpl(equipRepository)
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Check return false in executed correctly`() = runTest {
        val equipRepository = mock<EquipRepository>()
        whenever(equipRepository.checkNro(19759)).thenReturn(
            Result.success(false)
        )
        val usecase = CheckNroEquipProprioImpl(equipRepository)
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, false)
    }
}