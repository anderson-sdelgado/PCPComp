package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetIdVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.getTypeVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetIdVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.getTypeVisitTerc"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetId`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                visitanteRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "VisitanteRepository.getId",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetIdVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepository.getId"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetId`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                terceiroRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "TerceiroRepository.getId",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetIdVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepository.getId"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository SetIdVisitTerc`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                terceiroRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercRepository.setIdVisitTerc(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.setIdVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetIdVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.setIdVisitTerc"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository Add`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                visitanteRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.add",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetIdVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.add"
            )
        }

    @Test
    fun `Check return true if SetIdVisitTercImpl execute successfully MOTORISTA`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                terceiroRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercRepository.setIdVisitTerc(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetIdVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetIdVisitTercImpl execute successfully PASSAGEIRO`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                visitanteRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetIdVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}