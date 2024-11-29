package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.FluxoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveFluxoTest {

    @Test
    fun `Check return failure if have error in FluxoRepository addAll`() =
        runTest {
            val colabList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "MOV. EQUIP. PRÓPRIO"
                )
            )
            val fluxoRepository = mock<FluxoRepository>()
            whenever(
                fluxoRepository.addAll(colabList)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "FluxoRepository.addAll",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveFluxo(
                fluxoRepository
            )
            val result = usecase(colabList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> FluxoRepository.addAll"
            )
        }

    @Test
    fun `Check return true if SaveAllFluxoImplTest execute successfully`() =
        runTest {
            val colabList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "MOV. EQUIP. PRÓPRIO"
                )
            )
            val fluxoRepository = mock<FluxoRepository>()
            whenever(
                fluxoRepository.addAll(colabList)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISaveFluxo(
                fluxoRepository
            )
            val result = usecase(colabList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
        }

}