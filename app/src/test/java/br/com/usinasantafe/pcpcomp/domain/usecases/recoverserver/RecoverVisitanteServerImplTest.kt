package br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RecoverVisitanteServerImplTest {

    @Test
    fun `Check return Failure Datasouce if have failure in Config Repository`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.getConfig",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverVisitanteServerImpl(configRepository, visitanteRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getConfig")
    }

    @Test
    fun `Check return Failure Usecase if have empty fields in object Config return `() = runTest {
        val configRepository = mock<ConfigRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00"
                )
            )
        )
        val usecase = RecoverVisitanteServerImpl(configRepository, visitanteRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverVisitanteServer")
    }

    @Test
    fun `Check return Failure Datasource if have success getConfig and failure Datasource in recoverAll`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00",
                    idBD = 1L
                )
            )
        )
        whenever(visitanteRepository.recoverAll("Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "VisitanteRepository.recoverAll",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverVisitanteServerImpl(configRepository, visitanteRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> VisitanteRepository.recoverAll")
    }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1L,
                cpfVisitante = "123.456.789-00",
                nomeVisitante = "Visitante",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val configRepository = mock<ConfigRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00",
                    idBD = 1L
                )
            )
        )
        whenever(visitanteRepository.recoverAll("Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")).thenReturn(
            Result.success(visitanteList))
        val usecase = RecoverVisitanteServerImpl(configRepository, visitanteRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(visitanteList))
    }

}