package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ColabRepositoryImplTest {

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.deleteAll()).thenReturn(Result.success(true))
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.deleteAll()).thenReturn(Result.failure(DatasourceException()))
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRetrofitDatasource.recoverAll(token)).thenReturn(Result.failure(
            DatasourceException()
        ))
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val colabRoomModelList = listOf(
            ColabRoomModel(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRetrofitDatasource.recoverAll(token)).thenReturn(Result.success(colabRoomModelList))
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(colabList))
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabRoomModelList = listOf(
            ColabRoomModel(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.addAll(colabRoomModelList)).thenReturn(
            Result.success(
                true
            )
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.addAll(colabList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect addAll`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabRoomModelList = listOf(
            ColabRoomModel(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.addAll(colabRoomModelList)).thenReturn(
            Result.failure(
                DatasourceException()
            )
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.addAll(colabList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

}