package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CleanPassagVisitTercImplTest {

    @Test
    fun `Chech return failure Datasource if have error in cleanPassag`() = runTest {
        val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
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
        val usecase = ICleanPassagVisitTerc(movEquipVisitTercPassagRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipVisitTercPassagRepository.clear")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }
    
    @Test
    fun `Chech return true Datasource if cleanPassag execute success`() = runTest {
        val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
        whenever(
            movEquipVisitTercPassagRepository.clear()
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICleanPassagVisitTerc(movEquipVisitTercPassagRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

}