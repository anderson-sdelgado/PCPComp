package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveMovEquipResidenciaTest {

    @Test
    fun `Chech return failure if have error in ConfigRepository OutsideMovResidencia`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val setStatusOutsideMovResidencia = mock<SetStatusOutsideMovResidencia>()
            whenever(
                setStatusOutsideMovResidencia(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CloseMovResidencia",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveMovEquipResidencia(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                setStatusOutsideMovResidencia
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
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
            val setStatusOutsideMovResidencia = mock<SetStatusOutsideMovResidencia>()
            whenever(
                setStatusOutsideMovResidencia(1)
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
            val usecase = ISaveMovEquipResidencia(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                setStatusOutsideMovResidencia
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
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
            val setStatusOutsideMovResidencia = mock<SetStatusOutsideMovResidencia>()
            whenever(
                setStatusOutsideMovResidencia(1)
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
            val usecase = ISaveMovEquipResidencia(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                setStatusOutsideMovResidencia
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.save"
            )
        }

    @Test
    fun `Chech return true if SaveMovEquipResidenciaImpl execute successfully`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val closeMovResidencia = mock<CloseMovResidencia>()
            val setStatusOutsideMovResidencia = mock<SetStatusOutsideMovResidencia>()
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
            val usecase = ISaveMovEquipResidencia(
                configRepository,
                movEquipResidenciaRepository,
                startProcessSendData,
                setStatusOutsideMovResidencia
            )
            val result = usecase(
                typeMov = TypeMovEquip.OUTPUT,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}