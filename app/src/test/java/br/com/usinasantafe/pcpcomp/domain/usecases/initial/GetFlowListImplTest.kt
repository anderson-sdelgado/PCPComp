package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.RLocalFluxoRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class GetFlowListImplTest {

    private val configRepository = mock<ConfigRepository>()
    private val rLocalFluxoRepository = mock<RLocalFluxoRepository>()
    private val fluxoRepository = mock<FluxoRepository>()
    private fun getUsecase() = IGetFlowList(
        configRepository = configRepository,
        rLocalFluxoRepository = rLocalFluxoRepository,
        fluxoRepository = fluxoRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
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
            val usecase = getUsecase()
            val result = usecase()
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
    fun `Check return failure if have error in RLocalFluxoRepository list`() =
        runTest {
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        idLocal = 1
                    )
                )
            )
            whenever(
                rLocalFluxoRepository.list(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "RLocalFluxoRepository.list",
                        cause = Exception()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> RLocalFluxoRepository.list"
            )
        }

    @Test
    fun `Check return failure if have error in FluxoRepository get`() =
        runTest {
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        idLocal = 1
                    )
                )
            )
            whenever(
                rLocalFluxoRepository.list(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        RLocalFluxo(
                            idRLocalFluxo = 1,
                            idLocal = 1,
                            idFluxo = 1
                        )
                    )
                )
            )
            whenever(
                fluxoRepository.get(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "FluxoRepository.get",
                        cause = Exception()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> FluxoRepository.get"
            )
        }

    @Test
    fun `Check return list if GetFlowListImplTest execute successfully`() =
        runTest {
            whenever(
                configRepository.getConfig()
            ).thenReturn(
                Result.success(
                    Config(
                        idLocal = 1
                    )
                )
            )
            whenever(
                rLocalFluxoRepository.list(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        RLocalFluxo(
                            idRLocalFluxo = 1,
                            idLocal = 1,
                            idFluxo = 1
                        )
                    )
                )
            )
            whenever(
                fluxoRepository.get(1)
            ).thenReturn(
                Result.success(
                    Fluxo(
                        idFluxo = 1,
                        descrFluxo = "MOV. EQUIP. PRÓPRIO"
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            assertEquals(
                list[0].descrFluxo,
                "MOV. EQUIP. PRÓPRIO"
            )
        }
}