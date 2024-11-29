package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import br.com.usinasantafe.pcpcomp.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class SendMovVisitTercListImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository listSend`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.listSend",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISendMovVisitTercList(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.listSend"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository list`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            nroMatricVigiaMovEquipVisitTerc = 19759,
                            idLocalMovEquipVisitTerc = 1,
                            tipoMovEquipVisitTerc = TypeMov.INPUT,
                            idVisitTercMovEquipVisitTerc = 1000,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            veiculoMovEquipVisitTerc = "VEICULO TESTE",
                            placaMovEquipVisitTerc = "PLACA TESTE",
                            destinoMovEquipVisitTerc = "DESTINO TESTE",
                            observMovEquipVisitTerc = "OBSERV TESTE",
                            statusMovEquipVisitTerc = StatusData.OPEN,
                            statusSendMovEquipVisitTerc = StatusSend.SEND,
                            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.list",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISendMovVisitTercList(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.list"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            nroMatricVigiaMovEquipVisitTerc = 19759,
                            idLocalMovEquipVisitTerc = 1,
                            tipoMovEquipVisitTerc = TypeMov.INPUT,
                            idVisitTercMovEquipVisitTerc = 1000,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            veiculoMovEquipVisitTerc = "VEICULO TESTE",
                            placaMovEquipVisitTerc = "PLACA TESTE",
                            destinoMovEquipVisitTerc = "DESTINO TESTE",
                            observMovEquipVisitTerc = "OBSERV TESTE",
                            statusMovEquipVisitTerc = StatusData.OPEN,
                            statusSendMovEquipVisitTerc = StatusSend.SEND,
                            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idMovEquipVisitTercPassag = 1,
                            idMovEquipVisitTerc = 1,
                            idVisitTerc = 100
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
            val usecase = ISendMovVisitTercList(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
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
    fun `Check return failure if have error in MovEquipVisitTercRepository send`() =
        runTest {
            val listMov =
                listOf(
                    MovEquipVisitTerc(
                        idMovEquipVisitTerc = 1,
                        nroMatricVigiaMovEquipVisitTerc = 19759,
                        idLocalMovEquipVisitTerc = 1,
                        tipoMovEquipVisitTerc = TypeMov.INPUT,
                        idVisitTercMovEquipVisitTerc = 1000,
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                        dthrMovEquipVisitTerc = Date(1723213270250),
                        veiculoMovEquipVisitTerc = "VEICULO TESTE",
                        placaMovEquipVisitTerc = "PLACA TESTE",
                        destinoMovEquipVisitTerc = "DESTINO TESTE",
                        observMovEquipVisitTerc = "OBSERV TESTE",
                        statusMovEquipVisitTerc = StatusData.OPEN,
                        statusSendMovEquipVisitTerc = StatusSend.SEND,
                        statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                    )
                )
            val listPassag =
                listOf(
                    MovEquipVisitTercPassag(
                        idMovEquipVisitTercPassag = 1,
                        idMovEquipVisitTerc = 1,
                        idVisitTerc = 100
                    )
                )
            val entityConfig = Config(
                idBD = 1,
                number = 16997417840,
                version = "1.00"
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.success(listMov)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(listPassag)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(entityConfig)
            )
            val listSendFull = listMov.map { entity ->
                entity.movEquipVisitTercPassagList = listPassag
                return@map entity
            }
            val token = token(
                number = entityConfig.number!!,
                version = entityConfig.version!!,
                idBD = entityConfig.idBD!!
            )
            whenever(
                movEquipVisitTercRepository.send(
                    list = listSendFull,
                    number = 16997417840,
                    token = token
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.send",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISendMovVisitTercList(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.send"
            )
        }

    @Test
    fun `Check return list if SendMovVisitTercListImpl execute successfully`() =
        runTest {
            val listMov =
                listOf(
                    MovEquipVisitTerc(
                        idMovEquipVisitTerc = 1,
                        nroMatricVigiaMovEquipVisitTerc = 19759,
                        idLocalMovEquipVisitTerc = 1,
                        tipoMovEquipVisitTerc = TypeMov.INPUT,
                        idVisitTercMovEquipVisitTerc = 1000,
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                        dthrMovEquipVisitTerc = Date(1723213270250),
                        veiculoMovEquipVisitTerc = "VEICULO TESTE",
                        placaMovEquipVisitTerc = "PLACA TESTE",
                        destinoMovEquipVisitTerc = "DESTINO TESTE",
                        observMovEquipVisitTerc = "OBSERV TESTE",
                        statusMovEquipVisitTerc = StatusData.OPEN,
                        statusSendMovEquipVisitTerc = StatusSend.SEND,
                        statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                    )
                )
            val listPassag =
                listOf(
                    MovEquipVisitTercPassag(
                        idMovEquipVisitTercPassag = 1,
                        idMovEquipVisitTerc = 1,
                        idVisitTerc = 100
                    )
                )
            val entityConfig = Config(
                idBD = 1,
                number = 16997417840,
                version = "1.00"
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(
                movEquipVisitTercRepository.listSend()
            ).thenReturn(
                Result.success(listMov)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(listPassag)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(entityConfig)
            )
            val listSendFull = listMov.map { entity ->
                entity.movEquipVisitTercPassagList = listPassag
                return@map entity
            }
            val token = token(
                number = entityConfig.number!!,
                version = entityConfig.version!!,
                idBD = entityConfig.idBD!!
            )
            whenever(
                movEquipVisitTercRepository.send(
                    list = listSendFull,
                    number = 16997417840,
                    token = token
                )
            ).thenReturn(
                Result.success(listSendFull)
            )
            val usecase = ISendMovVisitTercList(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 1)
            val entity = list[0]
            assertEquals(entity.idMovEquipVisitTerc, 1)
            assertEquals(entity.nroMatricVigiaMovEquipVisitTerc, 19759)
        }
}