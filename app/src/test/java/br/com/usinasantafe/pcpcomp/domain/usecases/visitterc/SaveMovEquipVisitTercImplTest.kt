package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SaveMovEquipVisitTercImplTest {

    @Test
    fun `Chech return failure if have error in ConfigRepository CloseMovVisitTerc`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val setStatusOutsideMovVisitTerc = mock<SetStatusOutsideMovVisitTerc>()
            whenever(
                setStatusOutsideMovVisitTerc(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CloseMovVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData,
                setStatusOutsideMovVisitTerc
            )
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> CloseMovVisitTerc"
            )
        }

    @Test
    fun `Chech return failure if have error in ConfigRepository getConfig`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val setStatusOutsideMovVisitTerc = mock<SetStatusOutsideMovVisitTerc>()
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
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData,
                setStatusOutsideMovVisitTerc
            )
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercRepository Save`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val setStatusOutsideMovVisitTerc = mock<SetStatusOutsideMovVisitTerc>()
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
                movEquipVisitTercRepository.save(19759, 1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.save",
                    )
                )
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData,
                setStatusOutsideMovVisitTerc
            )
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.save"
            )
        }

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercPassagRepository Save`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val setStatusOutsideMovVisitTerc = mock<SetStatusOutsideMovVisitTerc>()
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
                movEquipVisitTercRepository.save(19759, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.save(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.save",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData,
                setStatusOutsideMovVisitTerc
            )
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.save"
            )
        }

    @Test
    fun `Chech return true if SaveMovEquipVisitTercImpl execute successfully`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            val setStatusOutsideMovVisitTerc = mock<SetStatusOutsideMovVisitTerc>()
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
                movEquipVisitTercRepository.save(19759, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.save(1)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISaveMovEquipVisitTerc(
                configRepository,
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startProcessSendData,
                setStatusOutsideMovVisitTerc
            )
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}