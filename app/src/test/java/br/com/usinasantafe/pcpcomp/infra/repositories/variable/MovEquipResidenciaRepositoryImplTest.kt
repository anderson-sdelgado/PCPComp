package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
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

class MovEquipResidenciaRepositoryImplTest {

    val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
        idMovEquipResidencia = 1,
        nroMatricVigiaMovEquipResidencia = 1000,
        idLocalMovEquipResidencia = 1000,
        tipoMovEquipResidencia = TypeMov.INPUT,
        dthrMovEquipResidencia = 1723213270250,
        motoristaMovEquipResidencia = "TESTE",
        veiculoMovEquipResidencia = "TESTE",
        placaMovEquipResidencia = "TESTE",
        observMovEquipResidencia = "TESTE",
        statusMovEquipResidencia = StatusData.OPEN,
        statusSendMovEquipResidencia = StatusSend.SEND,
        statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
    )

    val movEquipResidencia = MovEquipResidencia(
        idMovEquipResidencia = 1,
        nroMatricVigiaMovEquipResidencia = 1000,
        idLocalMovEquipResidencia = 1000,
        tipoMovEquipResidencia = TypeMov.INPUT,
        dthrMovEquipResidencia = Date(1723213270250),
        motoristaMovEquipResidencia = "TESTE",
        veiculoMovEquipResidencia = "TESTE",
        placaMovEquipResidencia = "TESTE",
        observMovEquipResidencia = "TESTE",
        statusMovEquipResidencia = StatusData.OPEN,
        statusSendMovEquipResidencia = StatusSend.SEND,
        statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
    )

    @Test
    fun `Check failure Datasource in MovEquipResidenciaRoomDatasource listOpen`() = runTest {
        val movEquipResidenciaRoomDatasource = mock<MovEquipResidenciaRoomDatasource>()
        whenever(movEquipResidenciaRoomDatasource.listOpen()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasource.listOpen",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipResidenciaRepositoryImpl(movEquipResidenciaRoomDatasource)
        val result = repository.listOpen()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRoomDatasource.listOpen"
        )
    }

    @Test
    fun `Check failure success if have success in MovEquipResidenciaRoomDatasource listOpen`() =
        runTest {
            val movEquipResidenciaRoomDatasource = mock<MovEquipResidenciaRoomDatasource>()
            whenever(movEquipResidenciaRoomDatasource.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidenciaRoomModel
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(movEquipResidenciaRoomDatasource)
            val result = repository.listOpen()
            assertTrue(result.isSuccess)
            val resultList = result.getOrNull()!!
            assertEquals(
                resultList[0],
                movEquipResidencia
            )
        }

    @Test
    fun `Check failure Datasource in MovEquipResidenciaRoomDatasource setClose`() = runTest {
        val movEquipResidenciaRoomDatasource = mock<MovEquipResidenciaRoomDatasource>()
        whenever(movEquipResidenciaRoomDatasource.setClose(movEquipResidenciaRoomModel)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasource.setClose",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipResidenciaRepositoryImpl(movEquipResidenciaRoomDatasource)
        val result = repository.setClose(movEquipResidencia)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRoomDatasource.setClose"
        )
    }

    @Test
    fun `Check failure success if have success in MovEquipResidenciaRoomDatasource setClose`() =
        runTest {
            val movEquipResidenciaRoomDatasource = mock<MovEquipResidenciaRoomDatasource>()
            whenever(movEquipResidenciaRoomDatasource.setClose(movEquipResidenciaRoomModel)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(movEquipResidenciaRoomDatasource)
            val result = repository.setClose(movEquipResidencia)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }
}