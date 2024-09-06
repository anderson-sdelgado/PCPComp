package br.com.usinasantafe.pcpcomp.domain.usecases.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllColabServerImplTest {

    @Test
    fun `Check return Failure Datasouce if have failure in Config Repository`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.getConfig",
                    cause = Exception()
                )
            )
        )
        val usecase = GetAllColabServerImpl(configRepository, colabRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getConfig")
    }

    @Test
    fun `Check return Failure Usecase if have empty fields in object Config return `() = runTest {
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00"
                )
            )
        )
        val usecase = GetAllColabServerImpl(configRepository, colabRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverColabServer")
    }

    @Test
    fun `Check return Failure Datasource if have success getConfig and failure Datasource in recoverAll`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00",
                    idBD = 1
                )
            )
        )
        whenever(colabRepository.recoverAll("Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ColabRepository.recoverAll",
                    cause = Exception()
                )
            )
        )
        val usecase = GetAllColabServerImpl(configRepository, colabRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRepository.recoverAll")
    }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "Anderson"
            )
        )
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00",
                    idBD = 1
                )
            )
        )
        whenever(colabRepository.recoverAll("Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")).thenReturn(
            Result.success(colabList))
        val usecase = GetAllColabServerImpl(configRepository, colabRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(colabList))
    }

}