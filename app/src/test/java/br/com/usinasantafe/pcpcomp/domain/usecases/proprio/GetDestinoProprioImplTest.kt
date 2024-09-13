package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
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

class GetDestinoProprioImplTest{

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository getDestino`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.getDestino(id = 1)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.getDestino",
                    cause = Exception()
                )
            )
        )
        val usecase = GetDestinoProprioImpl(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.getDestino")
    }

    @Test
    fun `Check return destino if GetNroEquipImpl execute success`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.getDestino(id = 1)).thenReturn(
            Result.success("Destino")
        )
        val usecase = GetDestinoProprioImpl(
            movEquipProprioRepository,
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "Destino")
    }
}