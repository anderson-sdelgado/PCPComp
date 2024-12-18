package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISaveMovChaveEquipTest {

    private val configRepository = mock<ConfigRepository>()
    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISaveMovChaveEquip(
        configRepository = configRepository,
        movChaveEquipRepository = movChaveEquipRepository,
        startProcessSendData = startProcessSendData,
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig - RECEIPT`() =
        runTest {
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
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository save - RECEIPT`() =
        runTest {
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
                movChaveEquipRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.save",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.save"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - RECEIPT`() =
        runTest {
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
                movChaveEquipRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.success(1)
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return failure if have error in SetStatusOutsideMovChave - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.setOutside",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.setOutside"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
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
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getConfig"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository save - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
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
                movChaveEquipRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.save",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.save"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - REMOVE`() =
        runTest {
            whenever(
                movChaveEquipRepository.setOutside(1)
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
                movChaveEquipRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.success(1)
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}