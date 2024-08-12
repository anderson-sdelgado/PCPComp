package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class MovEquipProprioRepositoryImplTest {

    val movEquipProprioRoomModel = MovEquipProprioRoomModel(
        idMovEquipProprio = 1,
        nroMatricVigiaMovEquipProprio = 19759,
        idLocalMovEquipProprio = 1,
        tipoMovEquipProprio = TypeMov.INPUT,
        dthrMovEquipProprio = 1723213270250,
        idEquipMovEquipProprio = 1,
        nroMatricColabMovEquipProprio = 19759,
        destinoMovEquipProprio = "TESTE DESTINO",
        nroNotaFiscalMovEquipProprio = 123456789,
        observMovEquipProprio = "TESTE OBSERV",
        statusMovEquipProprio = StatusData.OPEN,
        statusSendMovEquipProprio = StatusSend.SEND
    )

    val movEquipProprio = MovEquipProprio(
        idMovEquipProprio = 1,
        nroMatricVigiaMovEquipProprio = 19759,
        idLocalMovEquipProprio = 1,
        tipoMovEquipProprio = TypeMov.INPUT,
        dthrMovEquipProprio = Date(1723213270250),
        idEquipMovEquipProprio = 1,
        nroMatricColabMovEquipProprio = 19759,
        destinoMovEquipProprio = "TESTE DESTINO",
        nroNotaFiscalMovEquipProprio = 123456789,
        observMovEquipProprio = "TESTE OBSERV",
        statusMovEquipProprio = StatusData.OPEN,
        statusSendMovEquipProprio = StatusSend.SEND
    )

    @Test
    fun `Check failure Datasource in MovEquipProprioRoomDatasource listOpen`() = runTest {
        val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
        val movEquipProprioSharedPreferencesDatasource = mock<MovEquipProprioSharedPreferencesDatasource>()
        whenever(movEquipProprioRoomDatasource.listOpen()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasource.listOpen",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioRepositoryImpl(
            movEquipProprioSharedPreferencesDatasource,
            movEquipProprioRoomDatasource
        )
        val result = repository.listOpen()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioRoomDatasource.listOpen"
        )
    }

    @Test
    fun `Check failure success if have success in MovEquipProprioRoomDatasource listOpen`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource = mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioRoomDatasource.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprioRoomModel
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
            )
            val result = repository.listOpen()
            assertTrue(result.isSuccess)
            val resultList = result.getOrNull()!!
            assertEquals(
                resultList[0],
                movEquipProprio
            )
        }

    @Test
    fun `Check failure Datasource in MovEquipProprioRoomDatasource setClose`() = runTest {
        val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
        val movEquipProprioSharedPreferencesDatasource = mock<MovEquipProprioSharedPreferencesDatasource>()
        whenever(movEquipProprioRoomDatasource.setClose(movEquipProprioRoomModel)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasource.setClose",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioRepositoryImpl(
            movEquipProprioSharedPreferencesDatasource,
            movEquipProprioRoomDatasource
        )
        val result = repository.setClose(movEquipProprio)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioRoomDatasource.setClose"
        )
    }

    @Test
    fun `Check success if have success in MovEquipProprioRoomDatasource setClose`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource = mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioRoomDatasource.setClose(movEquipProprioRoomModel)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
            )
            val result = repository.setClose(movEquipProprio)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource start`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource = mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.start",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
            )
            val result = repository.start(TypeMov.INPUT)
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.start"
            )
        }

    @Test
    fun `Check success if MovEquipProprioSharedPreferencesDatasource start execute correctly`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource = mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
            )
            val result = repository.start(TypeMov.INPUT)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }
}