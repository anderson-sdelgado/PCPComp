package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class IGetDetalheResidenciaTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository Get`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.get",
                        cause = Exception()
                    )
                )
            )
            val usecase = IGetDetalheResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.get"
            )
        }

    @Test
    fun `Check return model if GetDetalheResidenciaImpl execute successfully Observ null`() =
        runTest {
            val model = MovEquipResidencia(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(model)
            )
            val usecase = IGetDetalheResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(1)
            assertTrue(result.isSuccess)
            val detalhe = result.getOrNull()!!
            assertEquals(detalhe.dthr, "DATA/HORA: 09/08/2024 11:21")
            assertEquals(detalhe.tipoMov, "ENTRADA")
            assertEquals(detalhe.veiculo, "VEÍCULO: VEICULO TESTE")
            assertEquals(detalhe.placa, "PLACA: PLACA TESTE")
            assertEquals(detalhe.motorista, "MOTORISTA: MOTORISTA TESTE")
            assertEquals(detalhe.observ, "OBSERVAÇÕES: ")
        }

    @Test
    fun `Check return model if GetDetalheResidenciaImpl execute successfully`() =
        runTest {
            val model = MovEquipResidencia(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERVACAO TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(model)
            )
            val usecase = IGetDetalheResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(1)
            assertTrue(result.isSuccess)
            val detalhe = result.getOrNull()!!
            assertEquals(detalhe.dthr, "DATA/HORA: 09/08/2024 11:21")
            assertEquals(detalhe.tipoMov, "ENTRADA")
            assertEquals(detalhe.veiculo, "VEÍCULO: VEICULO TESTE")
            assertEquals(detalhe.placa, "PLACA: PLACA TESTE")
            assertEquals(detalhe.motorista, "MOTORISTA: MOTORISTA TESTE")
            assertEquals(detalhe.observ, "OBSERVAÇÕES: OBSERVACAO TESTE")
        }
}