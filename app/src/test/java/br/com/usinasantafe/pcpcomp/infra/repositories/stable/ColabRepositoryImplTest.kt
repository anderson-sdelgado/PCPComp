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
        whenever(colabRoomDatasource.deleteAll()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ColabRoomDatasource.deleteAll",
                    cause = Exception()
                )
            )
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRoomDatasource.deleteAll")
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRetrofitDatasource.recoverAll(token)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ColabRetrofitDatasource.recoverAll",
                    cause = Exception()
                )
            )
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRetrofitDatasource.recoverAll")
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRetrofitDatasource.recoverAll(token)).thenReturn(Result.success(colabList))
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
                DatasourceException(
                    function = "ColabRoomDatasource.addAll",
                    cause = Exception()
                )
            )
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.addAll(colabList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRoomDatasource.addAll")
    }

    @Test
    fun `Check return false if not exist Colab`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.checkMatric(19759)).thenReturn(
            Result.success(false)
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.checkMatric(19759)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun `Check return true if exist Colab`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.checkMatric(19759)).thenReturn(
            Result.success(true)
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.checkMatric(19759)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Check return failure if have error in checkMatric Datasource`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.checkMatric(19759)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ColabRoomDatasource.checkMatric",
                    cause = Exception()
                )
            )
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.checkMatric(19759)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRoomDatasource.checkMatric")
    }

    @Test
    fun `Check return NomeVigia if have success in getNome`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.getNome(19759)).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.getNome(19759)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "ANDERSON DA SILVA DELGADO")
    }

    @Test
    fun `Check return failure if have error in getNome Datasource`() = runTest {
        val colabRoomDatasource = mock<ColabRoomDatasource>()
        val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
        whenever(colabRoomDatasource.getNome(19759)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ColabRoomDatasource.getNome",
                    cause = Exception()
                )
            )
        )
        val repository = ColabRepositoryImpl(colabRoomDatasource, colabRetrofitDatasource)
        val result = repository.getNome(19759)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRoomDatasource.getNome")
    }

}