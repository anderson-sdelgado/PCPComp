package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.RLocalFluxoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanRLocalFluxoTest {

    @Test
    fun `Check return failure if have error in RLocalFluxoRepository deleteAll`() =
        runTest {
            val rLocalFluxoRepository = mock<RLocalFluxoRepository>()
            whenever(
                rLocalFluxoRepository.deleteAll()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "RLocalFluxoRepository.deleteAll",
                        cause = Exception()
                    )
                )
            )
            val usecase = ICleanRLocalFluxo(rLocalFluxoRepository)
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> RLocalFluxoRepository.deleteAll"
            )
        }

    @Test
    fun `Check return true if CleanRLocalFluxoImplTest execute successfully`() =
        runTest {
            val rLocalFluxoRepository = mock<RLocalFluxoRepository>()
            whenever(
                rLocalFluxoRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ICleanRLocalFluxo(rLocalFluxoRepository)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}