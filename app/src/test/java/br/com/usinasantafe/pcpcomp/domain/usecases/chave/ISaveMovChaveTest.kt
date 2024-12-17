package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISaveMovChaveTest {

    private val configRepository = mock<ConfigRepository>()
    private val movChaveRepository = mock<MovChaveRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISaveMovChave(
        configRepository = configRepository,
        movChaveRepository = movChaveRepository,
        startProcessSendData = startProcessSendData,
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig - REMOVE`() =
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
                movChaveRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveRepository.save",
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
                "Failure Repository -> MovChaveRepository.save"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - REMOVE`() =
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
                movChaveRepository.save(
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

    @Test
    fun `Check return failure if have error in SetStatusOutsideMovChave - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveRepository.setStatusOutside",
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
                "Failure Repository -> MovChaveRepository.setStatusOutside"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
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
    fun `Check return failure if have error in MovChaveRepository save - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
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
                movChaveRepository.save(
                    matricVigia = 19759,
                    idLocal = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveRepository.save",
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
                "Failure Repository -> MovChaveRepository.save"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - RECEIPT`() =
        runTest {
            whenever(
                movChaveRepository.setOutside(1)
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
                movChaveRepository.save(
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