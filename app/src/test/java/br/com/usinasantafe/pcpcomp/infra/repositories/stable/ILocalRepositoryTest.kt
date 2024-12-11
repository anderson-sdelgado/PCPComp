package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.LocalRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ILocalRepositoryTest {

    private val localRoomDatasource = mock<LocalRoomDatasource>()
    private val localRetrofitDatasource = mock<LocalRetrofitDatasource>()
    private fun getRepository() = ILocalRepository(
        localRoomDatasource,
        localRetrofitDatasource
    )

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        whenever(
            localRoomDatasource.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val repository = getRepository()
        val result = repository.deleteAll()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        whenever(
            localRoomDatasource.deleteAll()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRoomDatasource.deleteAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRoomDatasource.deleteAll")
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        whenever(
            localRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRetrofitDatasource.recoverAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRetrofitDatasource.recoverAll")
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val entityList = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val retrofitModelList = listOf(
            LocalRetrofitModel(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        whenever(
            localRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.success(retrofitModelList)
        )
        val repository = getRepository()
        val result = repository.recoverAll(token)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(entityList))
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
        whenever(
            localRoomDatasource.addAll(localRoomModelList)
        ).thenReturn(
            Result.success(
                true
            )
        )
        val repository = getRepository()
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
        whenever(
            localRoomDatasource.addAll(localRoomModelList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRoomDatasource.addAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.addAll(localList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRoomDatasource.addAll")
    }

    @Test
    fun `Check failure Datasource in getAll`() = runTest {
        whenever(
            localRoomDatasource.listAll()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRoomDatasource.getAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.list()
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
        whenever(
            localRoomDatasource.listAll()
        ).thenReturn(
            Result.success(
                localRoomModels
            )
        )
        val repository = getRepository()
        val result = repository.list()
        assertEquals(result.isSuccess, true)
        val localsResult = result.getOrNull()!!
        assertEquals(localsResult, locals)
        assertEquals(localsResult[0].descrLocal, locals[0].descrLocal)
    }

    @Test
    fun `Check return DescrLocal if have success in getDescr`() = runTest {
        whenever(
            localRoomDatasource.getDescr(1)
        ).thenReturn(
            Result.success("USINA")
        )
        val repository = getRepository()
        val result = repository.getDescr(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "USINA")
    }

    @Test
    fun `Check return failure if have error in getNome Datasource`() = runTest {
        whenever(
            localRoomDatasource.getDescr(1)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRoomDatasource.getDescr",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.getDescr(1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRoomDatasource.getDescr")
    }
}