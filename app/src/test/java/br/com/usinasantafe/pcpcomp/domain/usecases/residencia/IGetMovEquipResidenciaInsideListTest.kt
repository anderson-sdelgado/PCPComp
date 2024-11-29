package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class IGetMovEquipResidenciaInsideListTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository ListOpenInput`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.listInside()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.ListOpenInput",
                        cause = Exception()
                    )
                )
            )
            val usecase = IGetMovEquipResidenciaInsideList(
                movEquipResidenciaRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.ListOpenInput"
            )
        }

    @Test
    fun `Check return model list if GetMovEquipResidenciaInputOpenListImpl execute successfully`() =
        runTest {
            val model = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERVACAO TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val list = listOf(model)
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.listInside()
            ).thenReturn(
                Result.success(list)
            )
            val usecase = IGetMovEquipResidenciaInsideList(
                movEquipResidenciaRepository
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            val modelList = result.getOrNull()!!
            assertEquals(modelList.size, 1)
            assertEquals(modelList[0].id, 1)
            assertEquals(modelList[0].dthr, "DATA/HORA: 09/08/2024 11:21")
            assertEquals(modelList[0].veiculo, "VEICULO: VEICULO TESTE")
            assertEquals(modelList[0].placa, "PLACA: PLACA TESTE")
            assertEquals(modelList[0].motorista, "MOTORISTA: MOTORISTA TESTE")
        }

}