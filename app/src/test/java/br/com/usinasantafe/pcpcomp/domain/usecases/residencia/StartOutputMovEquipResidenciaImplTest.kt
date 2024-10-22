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

class StartOutputMovEquipResidenciaImplTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()

    private fun getUsecase() = StartOutputMovEquipResidenciaImpl(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository get`() =
        runTest {
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
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.get"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository start`() =
        runTest {
            val entity = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            entity.tipoMovEquipResidencia = TypeMov.OUTPUT
            entity.dthrMovEquipResidencia = Date()
            entity.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
            entity.observMovEquipResidencia = null
            whenever(
                movEquipResidenciaRepository.start(entity)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.start",
                        cause = Exception()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.start"
            )
        }

    @Test
    fun `Check return true if StartOutputMovEquipResidencia execute successfully`() =
        runTest {
            val entity = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            entity.tipoMovEquipResidencia = TypeMov.OUTPUT
            entity.dthrMovEquipResidencia = Date()
            entity.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
            entity.observMovEquipResidencia = null
            whenever(
                movEquipResidenciaRepository.start(entity)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = getUsecase()
            val result = usecase(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

}