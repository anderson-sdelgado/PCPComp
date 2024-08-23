package br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RecoverTerceiroServerImplTest {

    @Test
    fun `Check return Failure Datasouce if have failure in Config Repository`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.getConfig",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverTerceiroServerImpl(configRepository, terceiroRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getConfig")
    }

    @Test
    fun `Check return Failure Usecase if have empty fields in object Config return `() = runTest {
        val configRepository = mock<ConfigRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00"
                )
            )
        )
        val usecase = RecoverTerceiroServerImpl(configRepository, terceiroRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverTerceiroServer")
    }

    @Test
    fun `Check return Failure Datasource if have success getConfig and failure Datasource in recoverAll`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00",
                    idBD = 1
                )
            )
        )
        whenever(terceiroRepository.recoverAll("Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "TerceiroRepository.recoverAll",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverTerceiroServerImpl(configRepository, terceiroRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> TerceiroRepository.recoverAll")
    }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro"
            )
        )
        val configRepository = mock<ConfigRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00",
                    idBD = 1
                )
            )
        )
        whenever(terceiroRepository.recoverAll("Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")).thenReturn(
            Result.success(terceiroList))
        val usecase = RecoverTerceiroServerImpl(configRepository, terceiroRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(terceiroList))
    }

}