package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalTrabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanLocalTrabTest {

    @Test
    fun `Check execution correct`() = runTest {
        val localTrabRepository = mock<LocalTrabRepository>()
        whenever(
            localTrabRepository.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val usecase =
            ICleanLocalTrab(
                localTrabRepository
            )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull(),
            true
        )
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val colabRepository = mock<LocalTrabRepository>()
        whenever(
            colabRepository.deleteAll()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalTrabRepository.deleteAll",
                    cause = Exception()
                )
            )
        )
        val usecase =
            ICleanLocalTrab(
                colabRepository
            )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> LocalTrabRepository.deleteAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.Exception"
        )
    }

}