package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class SendMovResidenciaListImplTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository listSend`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipResidenciaRepository.listSend()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.listSend",
                        cause = Exception()
                    )
                )
            )
            val usecase = SendMovResidenciaListImpl(
                movEquipResidenciaRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.listSend"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository GetConfig`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipResidenciaRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipResidencia(
                            idMovEquipResidencia = 1,
                            nroMatricVigiaMovEquipResidencia = 19759,
                            idLocalMovEquipResidencia = 1,
                            tipoMovEquipResidencia = TypeMov.INPUT,
                            dthrMovEquipResidencia = Date(1723213270250),
                            motoristaMovEquipResidencia = "MOTORISTA TESTE",
                            veiculoMovEquipResidencia = "VEICULO TESTE",
                            placaMovEquipResidencia = "PLACA TESTE",
                            observMovEquipResidencia = "OBSERV TESTE",
                            statusMovEquipResidencia = StatusData.OPEN,
                            statusSendMovEquipResidencia = StatusSend.SEND,
                            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
                        )
                    )
                )
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ConfigRepository.getConfig",
                        cause = Exception()
                    )
                )
            )
            val usecase = SendMovResidenciaListImpl(
                movEquipResidenciaRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository send`() =
        runTest {
            val entityList = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1,
                    nroMatricVigiaMovEquipResidencia = 19759,
                    idLocalMovEquipResidencia = 1,
                    tipoMovEquipResidencia = TypeMov.INPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
                )
            )
            val entityConfig = Config(
                idBD = 1,
                number = 16997417840,
                version = "1.00"
            )
            val token = token(
                number = entityConfig.number!!,
                version = entityConfig.version!!,
                idBD = entityConfig.idBD!!
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipResidenciaRepository.listSend()
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(entityConfig)
            )
            whenever(
                movEquipResidenciaRepository.send(
                    list = entityList,
                    number = 16997417840,
                    token = token
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.send",
                        cause = Exception()
                    )
                )
            )
            val usecase = SendMovResidenciaListImpl(
                movEquipResidenciaRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.send"
            )
        }

    @Test
    fun `Check return true if MovEquipResidenciaRepository send execute successfully`() =
        runTest {
            val entityList = listOf(
                MovEquipResidencia(
                    idMovEquipResidencia = 1,
                    nroMatricVigiaMovEquipResidencia = 19759,
                    idLocalMovEquipResidencia = 1,
                    tipoMovEquipResidencia = TypeMov.INPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
                )
            )
            val entityConfig = Config(
                idBD = 1,
                number = 16997417840,
                version = "1.00"
            )
            val token = token(
                number = entityConfig.number!!,
                version = entityConfig.version!!,
                idBD = entityConfig.idBD!!
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipResidenciaRepository.listSend()
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(entityConfig)
            )
            whenever(
                movEquipResidenciaRepository.send(
                    list = entityList,
                    number = 16997417840,
                    token = token
                )
            ).thenReturn(
                Result.success(entityList)
            )
            val usecase = SendMovResidenciaListImpl(
                movEquipResidenciaRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 1)
            val entity = list[0]
            assertEquals(entity.idMovEquipResidencia, 1)
            assertEquals(entity.placaMovEquipResidencia, "PLACA TESTE")
        }
}