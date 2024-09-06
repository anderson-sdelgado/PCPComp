package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetHeaderImplTest {

    @Test
    fun `Chech return failure Datasource if have error in getConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        val localRepository = mock<LocalRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.getConfig",
                    cause = Exception()
                )
            )
        )
        val usecase = GetHeaderImpl(
            configRepository,
            colabRepository,
            localRepository
        )
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getConfig")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Check return failure if have failure in getNome`() = runTest {
        val config = Config(
            matricVigia = 19759
        )
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        val localRepository = mock<LocalRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(config)
        )
        whenever(colabRepository.getNome(19759)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ColabRepository.getNome",
                    cause = Exception()
                )
            )
        )
        val usecase = GetHeaderImpl(
            configRepository,
            colabRepository,
            localRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRepository.getNome")
    }

    @Test
    fun `Check return failure if have failure in getDescr Local`() = runTest {
        val config = Config(
            matricVigia = 19759,
            idLocal = 1
        )
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        val localRepository = mock<LocalRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(config)
        )
        whenever(colabRepository.getNome(19759)).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        whenever(localRepository.getDescr(1)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRepository.getDescr",
                    cause = Exception()
                )
            )
        )
        val usecase = GetHeaderImpl(
            configRepository,
            colabRepository,
            localRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRepository.getDescr")
    }

    @Test
    fun `Check return Header if execute success`() = runTest {
        val config = Config(
            matricVigia = 19759,
            idLocal = 1
        )
        val configRepository = mock<ConfigRepository>()
        val colabRepository = mock<ColabRepository>()
        val localRepository = mock<LocalRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(config)
        )
        whenever(colabRepository.getNome(19759)).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        whenever(localRepository.getDescr(1)).thenReturn(
            Result.success("1 - USINA")
        )
        val usecase = GetHeaderImpl(
            configRepository,
            colabRepository,
            localRepository
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        val header = result.getOrNull()!!
        assertEquals(header.descrVigia, "19759 - ANDERSON DA SILVA DELGADO")
        assertEquals(header.descrLocal, "1 - USINA")
    }
}