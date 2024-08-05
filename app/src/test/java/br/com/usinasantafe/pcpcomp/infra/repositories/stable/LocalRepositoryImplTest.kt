package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
        whenever(localRoomDatasource.deleteAll()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRoomDatasource.deleteAll",
                    cause = Exception()
                )
            )
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRoomDatasource.deleteAll")
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRetrofitDatasource.recoverAll(token)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRetrofitDatasource.recoverAll",
                    cause = Exception()
                )
            )
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRetrofitDatasource.recoverAll")
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val local = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRetrofitDatasource.recoverAll(token)).thenReturn(Result.success(local))
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
                DatasourceException(
                    function = "LocalRoomDatasource.addAll",
                    cause = Exception()
                )
            )
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.addAll(localList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRoomDatasource.addAll")
    }

    @Test
    fun `Check failure Datasource in getAll`() = runTest {
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRoomDatasource.getAll()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRoomDatasource.getAll",
                    cause = Exception()
                )
            )
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.getAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRoomDatasource.getAll")
    }

    @Test
    fun `Check execution incorrect getAll`() = runTest {
        val localRoomModels = listOf(
            LocalRoomModel(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val locals = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRoomDatasource.getAll()).thenReturn(
            Result.success(
                localRoomModels
            )
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.getAll()
        assertEquals(result.isSuccess, true)
        val localsResult = result.getOrNull()!!
        assertEquals(localsResult, locals)
        assertEquals(localsResult[0].descrLocal, locals[0].descrLocal)
    }

    @Test
    fun `Check return DescrLocal if have success in getDescr`() = runTest {
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRoomDatasource.getDescr(1)).thenReturn(
            Result.success("USINA")
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.getDescr(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "USINA")
    }

    @Test
    fun `Check return failure if have error in getNome Datasource`() = runTest {
        val localRoomDatasource = mock<LocalRoomDatasource>()
        val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
        whenever(localRoomDatasource.getDescr(1)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRoomDatasource.getDescr",
                    cause = Exception()
                )
            )
        )
        val repository = LocalRepositoryImpl(localRoomDatasource, localRetrofitDatasource)
        val result = repository.getDescr(1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRoomDatasource.getDescr")
    }
}