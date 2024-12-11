package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class StartMovEquipProprioImplTest {

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository Start`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        whenever(movEquipProprioRepository.start(TypeMovEquip.INPUT)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRepository.start",
                    cause = Exception()
                )
            )
        )
        val usecase = IStartMovEquipProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository
        )
        val result = usecase(TypeMovEquip.INPUT)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> MovEquipProprioRepository.start")
    }

    @Test
    fun `Check return failure if have failure in MovEquipProprioSegRepository clear`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        whenever(movEquipProprioRepository.start(TypeMovEquip.INPUT)).thenReturn(
            Result.success(true)
        )
        whenever(movEquipProprioEquipSegRepository.clear()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioSegRepository.clear",
                    cause = Exception()
                )
            )
        )
        val usecase = IStartMovEquipProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository
        )
        val result = usecase(TypeMovEquip.INPUT)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> MovEquipProprioSegRepository.clear")
    }

    @Test
    fun `Check return failure if have failure in MovEquipProprioPassagRepository clear`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        whenever(movEquipProprioRepository.start(TypeMovEquip.INPUT)).thenReturn(
            Result.success(true)
        )
        whenever(movEquipProprioEquipSegRepository.clear()).thenReturn(
            Result.success(true)
        )
        whenever(movEquipProprioPassagRepository.clear()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagRepository.clear",
                    cause = Exception()
                )
            )
        )
        val usecase = IStartMovEquipProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository
        )
        val result = usecase(TypeMovEquip.INPUT)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> MovEquipProprioPassagRepository.clear")
    }

    @Test
    fun `Check return true if execute correctly`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        whenever(movEquipProprioRepository.start(TypeMovEquip.INPUT)).thenReturn(
            Result.success(true)
        )
        whenever(movEquipProprioEquipSegRepository.clear()).thenReturn(
            Result.success(true)
        )
        whenever(movEquipProprioPassagRepository.clear()).thenReturn(
            Result.success(true)
        )
        val usecase = IStartMovEquipProprio(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository
        )
        val result = usecase(TypeMovEquip.INPUT)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }
}