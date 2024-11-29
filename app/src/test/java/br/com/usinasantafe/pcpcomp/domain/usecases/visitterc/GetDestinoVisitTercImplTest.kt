package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetDestinoVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getDestino`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getDestino(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepository.getDestino",
                    cause = Exception()
                )
            )
        )
        val usecase = IGetDestinoVisitTerc(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercRepository.getDestino"
        )
    }

    @Test
    fun `Check return destino if GetDestino execute success`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getDestino(
                id = 1
            )
        ).thenReturn(
            Result.success("Destino")
        )
        val usecase = IGetDestinoVisitTerc(
            movEquipVisitTercRepository,
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "Destino")
    }
}