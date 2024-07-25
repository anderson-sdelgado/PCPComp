package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class LocalRepositoryImplTest {

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRoomDatasource.deleteAll()).thenReturn(Result.success(true))
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRoomDatasource.deleteAll()).thenReturn(Result.failure(DatasourceException()))
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRetrofitDatasource.recoverAll(token)).thenReturn(Result.failure(
            DatasourceException()
        ))
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val localRoomModel = listOf(
            LocalRoomModel(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val local = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRetrofitDatasource.recoverAll(token)).thenReturn(Result.success(localRoomModel))
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(local))
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val localList = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRoomModelList = listOf(
            LocalRoomModel(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRoomDatasource.addAll(localRoomModelList)).thenReturn(
            Result.success(
                true
            )
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.addAll(localList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect addAll`() = runTest {
        val localList = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRoomModelList = listOf(
            LocalRoomModel(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRoomDatasource.addAll(localRoomModelList)).thenReturn(
            Result.failure(
                DatasourceException()
            )
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.addAll(localList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

}