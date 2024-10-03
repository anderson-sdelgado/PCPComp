package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SaveMovEquipResidenciaImplTest {

    @Test
    fun `Chech return failure if have error in ConfigRepository OutsideMovResidencia`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val outsideMovResidencia = mock<OutsideMovResidencia>()
            whenever(
                outsideMovResidencia(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CloseMovResidencia",
                        cause = Exception()
                    )
                )
            )
            val usecase = SaveMovEquipResidenciaImpl(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                outsideMovResidencia
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> CloseMovResidencia"
            )
        }

    @Test
    fun `Chech return failure if have error in ConfigRepository GetConfig`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val outsideMovResidencia = mock<OutsideMovResidencia>()
            whenever(
                outsideMovResidencia(1)
            ).thenReturn(
                Result.success(true)
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
            val usecase = SaveMovEquipResidenciaImpl(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                outsideMovResidencia
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Chech return failure if have error in MovEquipResidenciaRepository Save`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val outsideMovResidencia = mock<OutsideMovResidencia>()
            whenever(
                outsideMovResidencia(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.save(19759, 1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.save",
                        cause = Exception()
                    )
                )
            )
            val usecase = SaveMovEquipResidenciaImpl(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                outsideMovResidencia
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.save"
            )
        }

    @Test
    fun `Chech return failure if have error in ConfigRepository SetStatusSend`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val outsideMovResidencia = mock<OutsideMovResidencia>()
            whenever(
                outsideMovResidencia(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.save(19759, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.setStatusSend(StatusSend.SEND)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ConfigRepository.setStatusSend",
                        cause = Exception()
                    )
                )
            )
            val usecase = SaveMovEquipResidenciaImpl(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                outsideMovResidencia
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.setStatusSend"
            )
        }

    @Test
    fun `Chech return true if SaveMovEquipResidenciaImpl execute successfully`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val closeMovResidencia = mock<CloseMovResidencia>()
            val outsideMovResidencia = mock<OutsideMovResidencia>()
            whenever(
                closeMovResidencia(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(
                movEquipResidenciaRepository.save(19759, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.setStatusSend(StatusSend.SEND)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SaveMovEquipResidenciaImpl(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                outsideMovResidencia
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}