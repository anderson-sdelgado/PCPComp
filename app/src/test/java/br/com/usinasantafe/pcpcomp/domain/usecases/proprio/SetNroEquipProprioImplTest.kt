package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetNroEquipProprioImplTest {

    @Test
    fun `Chech return failure Usecase if matric is numeric invalid in FlowApp ADD and TypeEquip VEICULO`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
        val usecase = SetNroEquipImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            startProcessSendData
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
    fun `Chech return failure Usecase if matric is numeric invalid in FlowApp CHANGE and TypeEquip VEICULO`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
        val usecase = SetNroEquipImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            startProcessSendData
        )
        val result = usecase(
            nroEquip = "10a",
            flowApp = FlowApp.CHANGE,
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
        val startProcessSendData = mock<StartProcessSendData>()
        whenever(equipRepository.getId(100)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "EquipRepository.getId",
                    cause = Exception()
                )
            )
        )
        val usecase = SetNroEquipImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            startProcessSendData
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
    fun `Chech return failure Datasource if have error in MovEquipProprioRepository setNroEquip in FlowApp ADD and TypeEquip VEICULO`() =
        runTest {
            val equipRepository = mock<EquipRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = SetNroEquipImpl(
                equipRepository,
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
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
    fun `Chech return failure Datasource if have error in MovEquipProprioRepository setNroEquip in FlowApp CHANGE and TypeEquip VEICULO`() =
        runTest {
            val equipRepository = mock<EquipRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(equipRepository.getId(100)).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioRepository.setIdEquip(
                    idEquip = 10,
                    flowApp = FlowApp.CHANGE,
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
            val usecase = SetNroEquipImpl(
                equipRepository,
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.CHANGE,
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
    fun `Chech return true if MovEquipProprioRepository setNroEquip execute success in FlowApp ADD and TypeEquip VEICULO`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
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
        val usecase = SetNroEquipImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            startProcessSendData
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
    fun `Chech return true if MovEquipProprioRepository setNroEquip execute success in FlowApp CHANGE and TypeEquip VEICULO`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
        whenever(equipRepository.getId(100)).thenReturn(
            Result.success(10)
        )
        whenever(
            movEquipProprioRepository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = SetNroEquipImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            startProcessSendData
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioEquipSegRepository add in FlowApp ADD and TypeEquip VEICULOSEG`() =
        runTest {
            val equipRepository = mock<EquipRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = SetNroEquipImpl(
                equipRepository,
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
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
    fun `Chech return failure Datasource if have error in MovEquipProprioEquipSegRepository add in FlowApp CHANGE and TypeEquip VEICULOSEG`() =
        runTest {
            val equipRepository = mock<EquipRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(equipRepository.getId(100)).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioEquipSegRepository.add(
                    idEquip = 10,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioEquipSegRepository.add",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetNroEquipImpl(
                equipRepository,
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.CHANGE,
                typeEquip = TypeEquip.VEICULOSEG,
                id = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioEquipSegRepository.add"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return failure if have error in setSend in MovEquipProprioModelRoom in FlowApp CHANGE and TypeEquip VEICULOSEG`() =
        runTest {
            val equipRepository = mock<EquipRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(equipRepository.getId(100)).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioEquipSegRepository.add(
                    idEquip = 10,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipProprioRepository.setSend(1)).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.setSend",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetNroEquipImpl(
                equipRepository,
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.CHANGE,
                typeEquip = TypeEquip.VEICULOSEG,
                id = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.setSend"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return true if MovEquipProprioEquipSegRepository add execute success in FlowApp ADD and TypeEquip VEICULOSEG`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
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
        val usecase = SetNroEquipImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            startProcessSendData
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

    @Test
    fun `Chech return true if MovEquipProprioEquipSegRepository add execute success in FlowApp CHANGE and TypeEquip VEICULOSEG`() = runTest {
        val equipRepository = mock<EquipRepository>()
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
        whenever(equipRepository.getId(100)).thenReturn(
            Result.success(10)
        )
        whenever(
            movEquipProprioEquipSegRepository.add(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(movEquipProprioRepository.setSend(1)).thenReturn(
            Result.success(true)
        )
        val usecase = SetNroEquipImpl(
            equipRepository,
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            startProcessSendData
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULOSEG,
            id = 1
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

}