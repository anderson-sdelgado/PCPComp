package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveLocalTest {

    @Test
    fun `Check execution correct`() = runTest {
        val localList = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRepository = Mockito.mock<LocalRepository>()
        whenever(localRepository.addAll(localList)).thenReturn(Result.success(true))
        val usecase = ISaveLocal(localRepository)
        val result = usecase(localList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val localList = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val localRepository = Mockito.mock<LocalRepository>()
        whenever(localRepository.addAll(localList)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "LocalRepository.addAll",
                    cause = Exception()
                )
            )
        )
        val usecase = ISaveLocal(localRepository)
        val result = usecase(localList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRepository.addAll")
    }
}