package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.config.RecoverConfigInternalImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RecoverNomeVigiaImplTest {

    @Test
    fun `Check return failure if have failure in getConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val colarepository = mock<ColabRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(cause = Exception("Failure Datasource"))
            )
        )
        val usecase = RecoverNomeVigiaImpl(configRepository, colarepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    @Test
    fun `Check return failure if have failure in getNome`() = runTest {
        val config = Config(
            matricVigia = 19759
        )
        val configRepository = mock<ConfigRepository>()
        val colarepository = mock<ColabRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(config)
        )
        whenever(colarepository.getNome(19759)).thenReturn(
            Result.failure(
                DatasourceException(cause = Exception())
            )
        )
        val usecase = RecoverNomeVigiaImpl(configRepository, colarepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
    }

    @Test
    fun `Check return success`() = runTest {
        val config = Config(
            matricVigia = 19759
        )
        val configRepository = mock<ConfigRepository>()
        val colarepository = mock<ColabRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(config)
        )
        whenever(colarepository.getNome(19759)).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val usecase = RecoverNomeVigiaImpl(configRepository, colarepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "ANDERSON DA SILVA DELGADO")
    }
}