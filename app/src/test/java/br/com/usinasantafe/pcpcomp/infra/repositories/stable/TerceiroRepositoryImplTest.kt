package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class TerceiroRepositoryImplTest {

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val terceiroRoomDatasource = mock<TerceiroRoomDatasource>()
        val terceiroRetrofitDatasource = mock<TerceiroRetrofitDatasource>()
        whenever(terceiroRoomDatasource.deleteAll()).thenReturn(Result.success(true))
        val repository = TerceiroRepositoryImpl(terceiroRoomDatasource, terceiroRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        val terceiroRoomDatasource = mock<TerceiroRoomDatasource>()
        val terceiroRetrofitDatasource = mock<TerceiroRetrofitDatasource>()
        whenever(terceiroRoomDatasource.deleteAll()).thenReturn(Result.failure(DatasourceException()))
        val repository = TerceiroRepositoryImpl(terceiroRoomDatasource, terceiroRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        val terceiroRoomDatasource = mock<TerceiroRoomDatasource>()
        val terceiroRetrofitDatasource = mock<TerceiroRetrofitDatasource>()
        whenever(terceiroRetrofitDatasource.recoverAll(token)).thenReturn(Result.failure(
            DatasourceException()
        ))
        val repository = TerceiroRepositoryImpl(terceiroRoomDatasource, terceiroRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val terceiroRoomModel = listOf(
            TerceiroRoomModel(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val terceiro = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val terceiroRoomDatasource = mock<TerceiroRoomDatasource>()
        val terceiroRetrofitDatasource = mock<TerceiroRetrofitDatasource>()
        whenever(terceiroRetrofitDatasource.recoverAll(token)).thenReturn(Result.success(terceiroRoomModel))
        val repository = TerceiroRepositoryImpl(terceiroRoomDatasource, terceiroRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(terceiro))
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val terceiroRoomModelList = listOf(
            TerceiroRoomModel(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val terceiroRoomDatasource = mock<TerceiroRoomDatasource>()
        val terceiroRetrofitDatasource = mock<TerceiroRetrofitDatasource>()
        whenever(terceiroRoomDatasource.addAll(terceiroRoomModelList)).thenReturn(
            Result.success(
                true
            )
        )
        val repository = TerceiroRepositoryImpl(terceiroRoomDatasource, terceiroRetrofitDatasource)
        val result = repository.addAll(terceiroList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect addAll`() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val terceiroRoomModelList = listOf(
            TerceiroRoomModel(
                idTerceiro = 1,
                idBDTerceiro = 1,
                nomeTerceiro = "Terceiro",
                cpfTerceiro = "123.456.789-00",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val terceiroRoomDatasource = mock<TerceiroRoomDatasource>()
        val terceiroRetrofitDatasource = mock<TerceiroRetrofitDatasource>()
        whenever(terceiroRoomDatasource.addAll(terceiroRoomModelList)).thenReturn(
            Result.failure(
                DatasourceException()
            )
        )
        val repository = TerceiroRepositoryImpl(terceiroRoomDatasource, terceiroRetrofitDatasource)
        val result = repository.addAll(terceiroList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

}