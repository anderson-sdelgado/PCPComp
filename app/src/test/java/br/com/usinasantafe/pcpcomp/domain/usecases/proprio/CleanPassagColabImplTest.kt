package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CleanPassagColabImplTest {

    @Test
    fun `Chech return failure Datasource if have error in cleanPassag`() = runTest {
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        whenever(movEquipProprioPassagRepository.clear()).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioPassagRepository.clear",
                    cause = Exception()
                )
            )
        )
        val usecase = ICleanPassagColab(movEquipProprioPassagRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioPassagRepository.clear")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Chech return true Datasource if cleanPassag execute success`() = runTest {
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        whenever(movEquipProprioPassagRepository.clear()).thenReturn(
            Result.success(true)
        )
        val usecase = ICleanPassagColab(movEquipProprioPassagRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

}