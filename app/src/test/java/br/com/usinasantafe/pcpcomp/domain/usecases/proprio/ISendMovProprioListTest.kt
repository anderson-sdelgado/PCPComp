package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import br.com.usinasantafe.pcpcomp.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class ISendMovProprioListTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository listSend`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(movEquipProprioRepository.listSend()).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.listSend",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISendMovProprioList(
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                movEquipProprioPassagRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.listSend"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioEquipSegRepository list`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(movEquipProprioRepository.listSend()).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprio(
                            idMovEquipProprio = 1,
                            matricVigiaMovEquipProprio = 19759,
                            idLocalMovEquipProprio = 1,
                            tipoMovEquipProprio = TypeMovEquip.INPUT,
                            dthrMovEquipProprio = Date(1723213270250),
                            idEquipMovEquipProprio = 1,
                            matricColabMovEquipProprio = 19759,
                            destinoMovEquipProprio = "TESTE DESTINO",
                            notaFiscalMovEquipProprio = 123456789,
                            observMovEquipProprio = "TESTE OBSERV",
                            statusMovEquipProprio = StatusData.OPEN,
                            statusSendMovEquipProprio = StatusSend.SEND
                        )
                    )
                )
            )
            whenever(
                movEquipProprioEquipSegRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioEquipSegRepository.list",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISendMovProprioList(
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                movEquipProprioPassagRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioEquipSegRepository.list"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioPassagRepository list`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(movEquipProprioRepository.listSend()).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprio(
                            idMovEquipProprio = 1,
                            matricVigiaMovEquipProprio = 19759,
                            idLocalMovEquipProprio = 1,
                            tipoMovEquipProprio = TypeMovEquip.INPUT,
                            dthrMovEquipProprio = Date(1723213270250),
                            idEquipMovEquipProprio = 1,
                            matricColabMovEquipProprio = 19759,
                            destinoMovEquipProprio = "TESTE DESTINO",
                            notaFiscalMovEquipProprio = 123456789,
                            observMovEquipProprio = "TESTE OBSERV",
                            statusMovEquipProprio = StatusData.OPEN,
                            statusSendMovEquipProprio = StatusSend.SEND
                        )
                    )
                )
            )
            whenever(
                movEquipProprioEquipSegRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprioEquipSeg(
                            idMovEquipProprioEquipSeg = 1,
                            idMovEquipProprio = 1,
                            idEquip = 1
                        )
                    )
                )
            )
            whenever(
                movEquipProprioPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioPassagRepository.list",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISendMovProprioList(
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                movEquipProprioPassagRepository,
                configRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioPassagRepository.list"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val configRepository = mock<ConfigRepository>()
            whenever(movEquipProprioRepository.listSend()).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprio(
                            idMovEquipProprio = 1,
                            matricVigiaMovEquipProprio = 19759,
                            idLocalMovEquipProprio = 1,
                            tipoMovEquipProprio = TypeMovEquip.INPUT,
                            dthrMovEquipProprio = Date(1723213270250),
                            idEquipMovEquipProprio = 1,
                            matricColabMovEquipProprio = 19759,
                            destinoMovEquipProprio = "TESTE DESTINO",
                            notaFiscalMovEquipProprio = 123456789,
                            observMovEquipProprio = "TESTE OBSERV",
                            statusMovEquipProprio = StatusData.OPEN,
                            statusSendMovEquipProprio = StatusSend.SEND
                        )
                    )
                )
            )
            whenever(
                movEquipProprioEquipSegRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprioEquipSeg(
                            idMovEquipProprioEquipSeg = 1,
                            idMovEquipProprio = 1,
                            idEquip = 1
                        )
                    )
                )
            )
            whenever(
                movEquipProprioPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipProprioPassag(
                            idMovEquipProprioPassag = 1,
                            idMovEquipProprio = 1,
                            matricColab = 19035
                        )
                    )
                )
            )
            whenever(configRepository.getConfig()).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ConfigRepository.getConfig",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISendMovProprioList(
                movEquipProprioRepository,
                movEquipProprioEquipSegRepository,
                movEquipProprioPassagRepository,
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
    fun `Check return failure if have error in MovEquipProprioRepository send`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1,
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMovEquip.INPUT,
            dthrMovEquipProprio = Date(1723213270250),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val movEquipProprioEquipSeg = MovEquipProprioEquipSeg(
            idMovEquipProprioEquipSeg = 1,
            idMovEquipProprio = 1,
            idEquip = 1
        )
        val movEquipProprioPassag = MovEquipProprioPassag(
            idMovEquipProprioPassag = 1,
            idMovEquipProprio = 1,
            matricColab = 19035
        )
        val listSend = listOf(
            movEquipProprio
        )
        val listSendFull = listSend.map {
            it.movEquipProprioEquipSegList = listOf(movEquipProprioEquipSeg)
            it.movEquipProprioPassagList = listOf(movEquipProprioPassag)
            return@map it
        }
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val configRepository = mock<ConfigRepository>()
        whenever(movEquipProprioRepository.listSend()).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioEquipSeg
                )
            )
        )
        whenever(
            movEquipProprioPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioPassag
                )
            )
        )
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "1.00",
                    idBD = 1
                )
            )
        )
        whenever(
            movEquipProprioRepository.send(
                list = listSendFull,
                number = 16997417840,
                token = token(
                    number = 16997417840,
                    version = "1.00",
                    idBD = 1
                )
            )
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.send",
                    cause = Exception()
                )
            )
        )
        val usecase = ISendMovProprioList(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            configRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.send"
        )
    }

    @Test
    fun `Check return list if SendMovProprio execute successfully`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1,
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMovEquip.INPUT,
            dthrMovEquipProprio = Date(1723213270250),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val movEquipProprioEquipSeg = MovEquipProprioEquipSeg(
            idMovEquipProprioEquipSeg = 1,
            idMovEquipProprio = 1,
            idEquip = 1
        )
        val movEquipProprioPassag = MovEquipProprioPassag(
            idMovEquipProprioPassag = 1,
            idMovEquipProprio = 1,
            matricColab = 19035
        )
        val listSend = listOf(
            movEquipProprio
        )
        val listSendFull = listSend.map {
            it.movEquipProprioEquipSegList = listOf(movEquipProprioEquipSeg)
            it.movEquipProprioPassagList = listOf(movEquipProprioPassag)
            return@map it
        }
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        val configRepository = mock<ConfigRepository>()
        whenever(movEquipProprioRepository.listSend()).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioEquipSegRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioEquipSeg
                )
            )
        )
        whenever(
            movEquipProprioPassagRepository.list(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioPassag
                )
            )
        )
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "1.00",
                    idBD = 1
                )
            )
        )
        whenever(
            movEquipProprioRepository.send(
                list = listSendFull,
                number = 16997417840,
                token = token(
                    number = 16997417840,
                    version = "1.00",
                    idBD = 1
                )
            )
        ).thenReturn(
            Result.success(
                listOf(
                    MovEquipProprio(
                        idMovEquipProprio = 1
                    )
                )
            )
        )
        val usecase = ISendMovProprioList(
            movEquipProprioRepository,
            movEquipProprioEquipSegRepository,
            movEquipProprioPassagRepository,
            configRepository
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(
            result.getOrNull()!![0].idMovEquipProprio, 1
        )
    }

}

