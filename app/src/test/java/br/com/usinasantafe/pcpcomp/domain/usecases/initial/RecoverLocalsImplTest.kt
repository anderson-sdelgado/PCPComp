package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
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

class RecoverLocalsImplTest {

    @Test
    fun `Check return failure if have failure in getAll`() = runTest {
        val localRepository = mock<LocalRepository>()
        whenever(localRepository.getAll()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRepository.getAll",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverLocalsImpl(localRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRepository.getAll")
    }

    @Test
    fun `Check return success`() = runTest {
        val localRepository = mock<LocalRepository>()
        whenever(localRepository.getAll()).thenReturn(
            Result.success(
                listOf(
                    Local(
                        idLocal = 1,
                        descrLocal = "USINA"
                    )
                )
            )
        )
        val usecase = RecoverLocalsImpl(localRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        val locals = result.getOrNull()!!
        assertEquals(locals[0].descrLocal, "USINA")
    }
}