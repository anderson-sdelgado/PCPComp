package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetVeiculoVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository getVeiculo`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            whenever(
                movEquipVisitTercRepository.getVeiculo(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.getVeiculo",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetVeiculoVisitTercImpl(
                movEquipVisitTercRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.getVeiculo"
            )
        }

    @Test
    fun `Check return veiculo if GetVeiculoVisitTercImpl execute successfully`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        whenever(
            movEquipVisitTercRepository.getVeiculo(
                id = 1
            )
        ).thenReturn(
            Result.success(
                "Veiculo"
            )
        )
        val usecase = GetVeiculoVisitTercImpl(
            movEquipVisitTercRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "Veiculo")
    }
}