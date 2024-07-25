package br.com.usinasantafe.pcpcomp.domain.usecases.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CleanVisitanteImplTest {

    @Test
    fun `Check execution correct`() = runTest {
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(visitanteRepository.deleteAll()).thenReturn(Result.success(true))
        val usecase = CleanVisitanteImpl(visitanteRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(visitanteRepository.deleteAll()).thenReturn(Result.failure(DatasourceException()))
        val usecase = CleanVisitanteImpl(visitanteRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }
}