package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.FluxoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanFluxoTest {

    @Test
    fun `Check return failure if have error in FluxoRepository DeleteAll`() =
        runTest {
            val fluxoRepository = mock<FluxoRepository>()
            whenever(
                fluxoRepository.deleteAll()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "FluxoRepository.deleteAll",
                        cause = Exception()
                    )
                )
            )
            val usecase = ICleanFluxo(fluxoRepository)
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> FluxoRepository.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if CleanFluxoImplTest execute successfully`() =
        runTest {
            val fluxoRepository = mock<FluxoRepository>()
            whenever(
                fluxoRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ICleanFluxo(fluxoRepository)
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