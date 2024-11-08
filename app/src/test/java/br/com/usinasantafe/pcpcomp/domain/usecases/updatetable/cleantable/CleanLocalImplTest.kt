package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CleanLocalImplTest {

    @Test
    fun `Check execution correct`() = runTest {
        val localRepository = mock<LocalRepository>()
        whenever(localRepository.deleteAll()).thenReturn(Result.success(true))
        val usecase = CleanLocalImpl(localRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val localRepository = mock<LocalRepository>()
        whenever(localRepository.deleteAll()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRepository.deleteAll",
                    cause = Exception()
                )
            )
        )
        val usecase = CleanLocalImpl(localRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRepository.deleteAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.Exception")
    }
}