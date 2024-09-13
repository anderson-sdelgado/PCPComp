package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Date

class GetMovEquipProprioOpenListImplTest {

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository listOpen`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.listOpen",
                    cause = Exception()
                )
            )
        )
        val usecase = GetMovEquipProprioOpenListImpl(
            movEquipProprioRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.listOpen")
    }

    @Test
    fun `Check return failure if not have all fields in MovEquipProprio`() = runTest {
        val movEquipProprioList = listOf(
            MovEquipProprio(
                idMovEquipProprio = 1
            )
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(movEquipProprioList)
        )
        val usecase = GetMovEquipProprioOpenListImpl(
            movEquipProprioRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverMovEquipProprioOpenList")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }

    @Test
    fun `Check return failure if have failure in EquipRepository getNro`() = runTest {
        val movEquipProprioList = listOf(
            MovEquipProprio(
                idMovEquipProprio = 1,
                idEquipMovEquipProprio = 1
            )
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(movEquipProprioList)
        )
        whenever(equipRepository.getNro(1)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "EquipRepository.getNro",
                    cause = Exception()
                )
            )
        )
        val usecase = GetMovEquipProprioOpenListImpl(
            movEquipProprioRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> EquipRepository.getNro")
    }

    @Test
    fun `Check return failure if have failure in ColabRepository getNome`() = runTest {
        val movEquipProprioList = listOf(
            MovEquipProprio(
                idMovEquipProprio = 1,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759
            )
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(movEquipProprioList)
        )
        whenever(equipRepository.getNro(1)).thenReturn(
            Result.success(100)
        )
        whenever(colabRepository.getNome(19759)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "ColabRepository.getNome",
                    cause = Exception()
                )
            )
        )
        val usecase = GetMovEquipProprioOpenListImpl(
            movEquipProprioRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> ColabRepository.getNome")
    }

    @Test
    fun `Check return list if all process execute successfully`() = runTest {
        val movEquipProprioList = listOf(
            MovEquipProprio(
                idMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
            )
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val equipRepository = mock<EquipRepository>()
        val colabRepository = mock<ColabRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(movEquipProprioList)
        )
        whenever(equipRepository.getNro(1)).thenReturn(
            Result.success(100)
        )
        whenever(colabRepository.getNome(19759)).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val usecase = GetMovEquipProprioOpenListImpl(
            movEquipProprioRepository,
            equipRepository,
            colabRepository
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.size, 1)
        assertEquals(result.getOrNull()!![0].typeMov, "ENTRADA")
        assertEquals(result.getOrNull()!![0].dthr, "DATA/HORA: 09/08/2024 11:21")
    }
}