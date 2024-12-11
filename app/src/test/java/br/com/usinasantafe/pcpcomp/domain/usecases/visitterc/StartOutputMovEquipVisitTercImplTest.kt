package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class StartOutputMovEquipVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()

    private fun getUsecase() = IStartOutputMovEquipVisitTerc(
        movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository get`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.get",
                        cause = Exception()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.get"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository start`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.start",
                        cause = Exception()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.start"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercPassagRepository list`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.list",
                        cause = Exception()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.list"
            )
        }

    @Test
    fun `Check return true if StartOutputMovEquipVisitTerc execute successfully and list empty`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    1
                )
            ).thenReturn(
                Result.success(
                    emptyList()
                )
            )
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercPassagRepository add`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idMovEquipVisitTercPassag = 1,
                            idVisitTerc = 10,
                            idMovEquipVisitTerc = 1
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.add",
                        cause = Exception()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.add"
            )
        }

    @Test
    fun `Check return true if StartOutputMovEquipVisitTerc execute successfully`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(),
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTercMovEquipVisitTerc = 1,
                destinoMovEquipVisitTerc = "Teste Destino",
                observMovEquipVisitTerc = "Teste Observ"
            )
            whenever(
                movEquipVisitTercRepository.get(1)
            ).thenReturn(
                Result.success(
                    movEquipVisitTerc
                )
            )
            movEquipVisitTerc.tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT
            movEquipVisitTerc.dthrMovEquipVisitTerc = Date()
            movEquipVisitTerc.destinoMovEquipVisitTerc = null
            movEquipVisitTerc.statusMovEquipForeignerVisitTerc = StatusForeigner.OUTSIDE
            whenever(
                movEquipVisitTercRepository.start(movEquipVisitTerc)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    1
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idMovEquipVisitTercPassag = 1,
                            idVisitTerc = 10,
                            idMovEquipVisitTerc = 1
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}