package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class SaveAllVisitanteImplTest {

    @Test
    fun `Check execution correct`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                cpfVisitante = "123.456.789-00",
                nomeVisitante = "Visitante",
                empresaVisitante = "Empresa Visitante",
            )
        )
        val visitanteRepository = Mockito.mock<VisitanteRepository>()
        whenever(visitanteRepository.addAll(visitanteList)).thenReturn(Result.success(true))
        val usecase = SaveAllVisitanteImpl(visitanteRepository)
        val result = usecase(visitanteList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                cpfVisitante = "123.456.789-00",
                nomeVisitante = "Visitante",
                empresaVisitante = "Empresa Visitante",
            )
        )
        val visitanteRepository = Mockito.mock<VisitanteRepository>()
        whenever(visitanteRepository.addAll(visitanteList)).thenReturn(Result.failure(
            DatasourceException()
        ))
        val usecase = SaveAllVisitanteImpl(visitanteRepository)
        val result = usecase(visitanteList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }
}