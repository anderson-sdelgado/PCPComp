package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class VisitanteRepositoryImplTest {

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val visitanteRoomDatasource = mock<VisitanteRoomDatasource>()
        val visitanteRetrofitDatasource = mock<VisitanteRetrofitDatasource>()
        whenever(visitanteRoomDatasource.deleteAll()).thenReturn(Result.success(true))
        val repository = VisitanteRepositoryImpl(visitanteRoomDatasource, visitanteRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        val visitanteRoomDatasource = mock<VisitanteRoomDatasource>()
        val visitanteRetrofitDatasource = mock<VisitanteRetrofitDatasource>()
        whenever(visitanteRoomDatasource.deleteAll()).thenReturn(Result.failure(DatasourceException()))
        val repository = VisitanteRepositoryImpl(visitanteRoomDatasource, visitanteRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        val visitanteRoomDatasource = mock<VisitanteRoomDatasource>()
        val visitanteRetrofitDatasource = mock<VisitanteRetrofitDatasource>()
        whenever(visitanteRetrofitDatasource.recoverAll(token)).thenReturn(Result.failure(
            DatasourceException()
        ))
        val repository = VisitanteRepositoryImpl(visitanteRoomDatasource, visitanteRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val visitanteRoomModel = listOf(
            VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitante = listOf(
            Visitante(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomDatasource = mock<VisitanteRoomDatasource>()
        val visitanteRetrofitDatasource = mock<VisitanteRetrofitDatasource>()
        whenever(visitanteRetrofitDatasource.recoverAll(token)).thenReturn(Result.success(visitanteRoomModel))
        val repository = VisitanteRepositoryImpl(visitanteRoomDatasource, visitanteRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(visitante))
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomModelList = listOf(
            VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomDatasource = mock<VisitanteRoomDatasource>()
        val visitanteRetrofitDatasource = mock<VisitanteRetrofitDatasource>()
        whenever(visitanteRoomDatasource.addAll(visitanteRoomModelList)).thenReturn(
            Result.success(
                true
            )
        )
        val repository = VisitanteRepositoryImpl(visitanteRoomDatasource, visitanteRetrofitDatasource)
        val result = repository.addAll(visitanteList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect addAll`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomModelList = listOf(
            VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomDatasource = mock<VisitanteRoomDatasource>()
        val visitanteRetrofitDatasource = mock<VisitanteRetrofitDatasource>()
        whenever(visitanteRoomDatasource.addAll(visitanteRoomModelList)).thenReturn(
            Result.failure(
                DatasourceException()
            )
        )
        val repository = VisitanteRepositoryImpl(visitanteRoomDatasource, visitanteRetrofitDatasource)
        val result = repository.addAll(visitanteList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

}