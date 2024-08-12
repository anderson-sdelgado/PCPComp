package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class SaveAllColabImplTest {

    @Test
    fun `Check execution correct`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        val colabRepository = Mockito.mock<ColabRepository>()
        whenever(colabRepository.addAll(colabList)).thenReturn(Result.success(true))
        val usecase = SaveAllColabImpl(colabRepository)
        val result = usecase(colabList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        val colabRepository = Mockito.mock<ColabRepository>()
        whenever(colabRepository.addAll(colabList)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ColabRepository.addAll",
                    cause = Exception()
                )
            )
        )
        val usecase = SaveAllColabImpl(colabRepository)
        val result = usecase(colabList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRepository.addAll")
    }
}