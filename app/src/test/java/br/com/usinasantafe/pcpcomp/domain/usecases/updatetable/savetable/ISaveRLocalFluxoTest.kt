package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.RLocalFluxoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveRLocalFluxoTest {

    @Test
    fun `Check return failure if have error in RLocalFluxoRepository addAll`() =
        runTest {
            val rLocalFluxoList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idFluxo = 1,
                    idLocal = 1
                )
            )
            val rLocalFluxoRepository = mock<RLocalFluxoRepository>()
            whenever(
                rLocalFluxoRepository.addAll(rLocalFluxoList)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "RLocalFluxoRepository.addAll",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveRLocalFluxo(
                rLocalFluxoRepository
            )
            val result = usecase(rLocalFluxoList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> RLocalFluxoRepository.addAll"
            )
        }

    @Test
    fun `Check return true if SaveAllRLocalFluxoImplTest execute successfully`() =
        runTest {
            val rLocalFluxoList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idFluxo = 1,
                    idLocal = 1
                )
            )
            val rLocalFluxoRepository = mock<RLocalFluxoRepository>()
            whenever(
                rLocalFluxoRepository.addAll(rLocalFluxoList)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISaveRLocalFluxo(
                rLocalFluxoRepository
            )
            val result = usecase(rLocalFluxoList)
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