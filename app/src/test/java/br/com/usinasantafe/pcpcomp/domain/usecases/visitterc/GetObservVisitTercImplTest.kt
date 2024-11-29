package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetObservVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getObserv`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepository.getObserv",
                    cause = Exception()
                )
            )
        )
        val usecase = IGetObservVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercRepository.getObserv"
        )
    }

    @Test
    fun `Check return observ if GetObservImpl execute success`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val usecase = IGetObservVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(
            result.getOrNull()!!,
            "Observação"
        )
    }

    @Test
    fun `Check return observ if GetObservImpl execute success and field is null`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getObserv(
                id = 1
            )
        ).thenReturn(
            Result.success(null)
        )
        val usecase = IGetObservVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(
            result.getOrNull(),
            null
        )
    }
}