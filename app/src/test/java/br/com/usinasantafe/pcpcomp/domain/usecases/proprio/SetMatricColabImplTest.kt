package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetMatricColabImplTest {

    @Test
    fun `Chech return failure Usecase if matric is numeric invalid`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
        val usecase = SetMatricColabImpl(
            movEquipProprioRepository,
            movEquipProprioPassagRepository,
            startProcessSendData
        )
        val result = usecase(
            matricColab = "19759a",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.MOTORISTA,
            id = 0
        )
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> SetMatricMotorista")
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"19759a\""
        )
    }

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioRepository setMatricMotorista in FlowApp ADD and TypeOcupante MOTORISTA`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.setMatricMotorista",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetMatricColabImpl(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.setMatricMotorista"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioRepository setMatricMotorista in FlowApp CHANGE and TypeOcupante MOTORISTA`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.setMatricMotorista",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetMatricColabImpl(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.setMatricMotorista"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return true if MovEquipProprioRepository setMatricMotorista execute success in FlowApp ADD and TypeOcupante MOTORISTA`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetMatricColabImpl(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Chech return true if MovEquipProprioRepository setMatricMotorista execute success in FlowApp CHANGE and TypeOcupante MOTORISTA`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetMatricColabImpl(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioPassagRepository add in FlowApp ADD and TypeOcupante PASSAGEIRO`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioPassagRepository.add(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioPassagRepository.add",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetMatricColabImpl(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioPassagRepository.add"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioPassagRepository add in FlowApp CHANGE and TypeOcupante PASSAGEIRO`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioPassagRepository.add(
                    matricColab = 19759,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioPassagRepository.add",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetMatricColabImpl(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioPassagRepository.add"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return failure if have error in setSend in MovEquipProprioModelRoom in in FlowApp CHANGE and TypeOcupante PASSAGEIRO`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioPassagRepository.add(
                    matricColab = 19759,
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
            val usecase = SetMatricColabImpl(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
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
    fun `Chech return true if MovEquipProprioPassagRepository add execute success in FlowApp ADD and TypeOcupante PASSAGEIRO`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
        whenever(
            movEquipProprioPassagRepository.add(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = SetMatricColabImpl(
            movEquipProprioRepository,
            movEquipProprioPassagRepository,
            startProcessSendData
        )
        val result = usecase(
            matricColab = "19759",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.PASSAGEIRO,
            id = 0
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Chech return true if MovEquipProprioPassagRepository add execute success in FlowApp CHANGE and TypeOcupante PASSAGEIRO`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val startProcessSendData = mock<StartProcessSendData>()
        whenever(
            movEquipProprioPassagRepository.add(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            movEquipProprioRepository.setSend(1)
        ).thenReturn(
            Result.success(true)
        )
        val usecase = SetMatricColabImpl(
            movEquipProprioRepository,
            movEquipProprioPassagRepository,
            startProcessSendData
        )
        val result = usecase(
            matricColab = "19759",
            flowApp = FlowApp.CHANGE,
            typeOcupante = TypeOcupante.PASSAGEIRO,
            id = 0
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

}