package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SaveMovEquipProprioImplTest {

    @Test
    fun `Chech return failure if have error in ConfigRepository getConfig`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(configRepository.getConfig()).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "ConfigRepository.getConfig",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveMovEquipProprio(
                configRepository,
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Chech return failure if ConfigRepository getConfig return object Config null`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(configRepository.getConfig()).thenReturn(
                Result.success(
                    Config()
                )
            )
            val usecase = ISaveMovEquipProprio(
                configRepository,
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SaveMovEquipProprioImpl"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `Chech return failure if have error in process save in MovEquipProprioRepository`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(configRepository.getConfig()).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(movEquipProprioRepository.save(19759, 1)).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.save",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveMovEquipProprio(
                configRepository,
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.save"
            )
        }

    @Test
    fun `Chech return failure if  have error in MovEquipProprioPassagRepository save`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(configRepository.getConfig()).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(movEquipProprioRepository.save(19759, 1)).thenReturn(
                Result.success(1)
            )
            whenever(movEquipProprioPassagRepository.save(1)).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioPassagRepository.save",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveMovEquipProprio(
                configRepository,
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioPassagRepository.save"
            )
        }

    @Test
    fun `Chech return failure if  have error in MovEquipProprioEquipSegRepository save`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(configRepository.getConfig()).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(movEquipProprioRepository.save(19759, 1)).thenReturn(
                Result.success(1)
            )
            whenever(movEquipProprioPassagRepository.save(1)).thenReturn(
                Result.success(true)
            )
            whenever(movEquipProprioEquipSegRepository.save(1)).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioEquipSegRepository.save",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISaveMovEquipProprio(
                configRepository,
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioEquipSegRepository.save"
            )
        }

    @Test
    fun `Chech return true if SaveMovEquipProprio execute successfully`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(configRepository.getConfig()).thenReturn(
                Result.success(
                    Config(
                        matricVigia = 19759,
                        idLocal = 1
                    )
                )
            )
            whenever(movEquipProprioRepository.save(19759, 1)).thenReturn(
                Result.success(1)
            )
            whenever(movEquipProprioPassagRepository.save(1)).thenReturn(
                Result.success(true)
            )
            whenever(movEquipProprioEquipSegRepository.save(1)).thenReturn(
                Result.success(true)
            )
            val usecase = ISaveMovEquipProprio(
                configRepository,
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                movEquipProprioEquipSegRepository,
                startProcessSendData
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}