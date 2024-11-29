package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetNomeColabImplTest {

    @Test
    fun `Check return failure if matric is invalid`() = runTest {
        val colabRepository = mock<ColabRepository>()
        val usecase = IGetNomeColab(
            colabRepository
        )
        val result = usecase("19759")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverNomeColab")
    }

    @Test
    fun `Check return failure if have failure in getNome`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(colabRepository.getNome(19759)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ColabRepository.getNome",
                    cause = Exception()
                )
            )
        )
        val usecase = IGetNomeColab(
            colabRepository
        )
        val result = usecase("19759")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRepository.getNome")
    }

    @Test
    fun `Check return NomeVigia if the process execute success`() = runTest {
        val colabRepository = mock<ColabRepository>()
        whenever(colabRepository.getNome(19759)).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val usecase = IGetNomeColab(
            colabRepository
        )
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "ANDERSON DA SILVA DELGADO")
    }

}