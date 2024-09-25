package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class GetMovEquipVisitTercInputOpenListImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository ListOpenInput`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val getMotorista = mock<GetMotorista>()
            whenever(
                movEquipVisitTercRepository.listOpenInput()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.listOpenInput",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetMovEquipVisitTercInputOpenListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                getMotorista = getMotorista
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.listOpenInput"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val getMotorista = mock<GetMotorista>()
            whenever(
                movEquipVisitTercRepository.listOpenInput()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            tipoMovEquipVisitTerc = TypeMov.INPUT,
                            veiculoMovEquipVisitTerc = "GOL",
                            placaMovEquipVisitTerc = "AAA-0000",
                            idVisitTercMovEquipVisitTerc = 1
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.getTypeVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetMovEquipVisitTercInputOpenListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                getMotorista = getMotorista
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.getTypeVisitTerc"
            )
        }

    @Test
    fun `Check return failure if have error in GetMotorista`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val getMotorista = mock<GetMotorista>()
            whenever(
                movEquipVisitTercRepository.listOpenInput()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            tipoMovEquipVisitTerc = TypeMov.INPUT,
                            veiculoMovEquipVisitTerc = "GOL",
                            placaMovEquipVisitTerc = "AAA-0000",
                            idVisitTercMovEquipVisitTerc = 1,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                getMotorista(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTerc = 1
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetMotorista",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetMovEquipVisitTercInputOpenListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                getMotorista = getMotorista
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> GetMotorista"
            )
        }

    @Test
    fun `Check return list model if GetMovEquipVisitTercInputOpenListImpl execute successfully`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val getMotorista = mock<GetMotorista>()
            whenever(
                movEquipVisitTercRepository.listOpenInput()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTerc(
                            idMovEquipVisitTerc = 1,
                            dthrMovEquipVisitTerc = Date(1723213270250),
                            tipoMovEquipVisitTerc = TypeMov.INPUT,
                            veiculoMovEquipVisitTerc = "GOL",
                            placaMovEquipVisitTerc = "AAA-0000",
                            idVisitTercMovEquipVisitTerc = 1,
                            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                        )
                    )
                )
            )
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                getMotorista(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTerc = 1
                )
            ).thenReturn(
                Result.success(
                    "123.456.789-00 - Anderson"
                )
            )
            val usecase = GetMovEquipVisitTercInputOpenListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                getMotorista = getMotorista
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            val mov = result.getOrNull()!![0]
            assertEquals(
                mov.veiculo,
                "GOL"
            )
            assertEquals(
                mov.placa,
                "AAA-0000"
            )
            assertEquals(
                mov.tipoVisitTerc,
                "TERCEIRO"
            )
            assertEquals(
                mov.motorista,
                "123.456.789-00 - Anderson"
            )
        }
}