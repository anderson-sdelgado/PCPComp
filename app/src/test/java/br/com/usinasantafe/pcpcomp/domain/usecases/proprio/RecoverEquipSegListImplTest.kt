package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RecoverEquipSegListImplTest {

    @Test
    fun `Chech return failure Usecase if have failure in MovEquipProprioEquipSegRepository`() = runTest {
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(movEquipProprioEquipSegRepository.list()).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioPassagRepository.list",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverEquipSegListImpl(
            movEquipProprioEquipSegRepository,
            equipRepository
        )
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioPassagRepository.list")
    }

    @Test
    fun `Chech return failure Usecase if not have data in table equip`() = runTest {
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(movEquipProprioEquipSegRepository.list()).thenReturn(
            Result.success(
                listOf(10)
            )
        )
        val usecase = RecoverEquipSegListImpl(
            movEquipProprioEquipSegRepository,
            equipRepository
        )
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverEquipSeg")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }

    @Test
    fun `Chech return failure if colabRepository getNome return failure`() = runTest {
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(movEquipProprioEquipSegRepository.list()).thenReturn(
            Result.success(
                listOf(10)
            )
        )
        whenever(equipRepository.getNro(10)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "EquipRepository.getNro",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverEquipSegListImpl(
            movEquipProprioEquipSegRepository,
            equipRepository
        )
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> EquipRepository.getNro")
    }

    @Test
    fun `Chech return list Equip if process execute successfully`() = runTest {
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(movEquipProprioEquipSegRepository.list()).thenReturn(
            Result.success(
                listOf(10)
            )
        )
        whenever(equipRepository.getNro(10)).thenReturn(
            Result.success(100)
        )
        val usecase = RecoverEquipSegListImpl(
            movEquipProprioEquipSegRepository,
            equipRepository
        )
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!.size, 1)
        assertEquals(result.getOrNull()!![0].nroEquip, 100)
        assertEquals(result.getOrNull()!![0].idEquip, 10)
    }
}