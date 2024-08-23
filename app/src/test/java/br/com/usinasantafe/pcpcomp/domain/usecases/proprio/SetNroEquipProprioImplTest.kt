package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetNroEquipProprioImplTest {

    @Test
    fun `Chech return failure Usecase if matric is numeric invalid`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val usecase = SetNroEquipProprioImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository
        )
        val result = usecase(
            nroEquip = "10a",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> SetNroEquipProprio")
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"10a\""
        )
    }

    @Test
    fun `Chech return failure Datasource if have error in EquipRepository getNro`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        whenever(equipRepository.getId(100)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "EquipRepository.getId",
                    cause = Exception()
                )
            )
        )
        val usecase = SetNroEquipProprioImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> EquipRepository.getId"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioRepository setNroEquip`() =
        runTest {
            val equipRepository = mock<EquipRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            whenever(equipRepository.getId(100)).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioRepository.setIdEquip(
                    idEquip = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.setNroEquip",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetNroEquipProprioImpl(
                equipRepository,
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.setNroEquip"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return true if MovEquipProprioRepository setNroEquip execute success`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        whenever(equipRepository.getId(100)).thenReturn(
            Result.success(10)
        )
        whenever(
            movEquipProprioRepository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = SetNroEquipProprioImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioEquipSegRepository add`() =
        runTest {
            val equipRepository = mock<EquipRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            whenever(equipRepository.getId(100)).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioEquipSegRepository.add(
                    idEquip = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioEquipSegRepository.add",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetNroEquipProprioImpl(
                equipRepository,
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULOSEG,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioEquipSegRepository.add"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return true if MovEquipProprioEquipSegRepository add execute success`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        whenever(equipRepository.getId(100)).thenReturn(
            Result.success(10)
        )
        whenever(
            movEquipProprioEquipSegRepository.add(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = SetNroEquipProprioImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULOSEG,
            id = 0
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

}