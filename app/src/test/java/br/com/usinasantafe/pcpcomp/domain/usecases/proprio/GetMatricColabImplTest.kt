package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class GetMatricColabImplTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository get`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.getMatricColab(id = 1)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.get",
                    cause = Exception()
                )
            )
        )
        val usecase = GetMatricColabImpl(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.get")
    }

    @Test
    fun `Check return matricColab if GetMatricColabImpl execute success`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.getMatricColab(id = 1)).thenReturn(
            Result.success(19759)
        )
        val usecase = GetMatricColabImpl(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "19759")
    }
}