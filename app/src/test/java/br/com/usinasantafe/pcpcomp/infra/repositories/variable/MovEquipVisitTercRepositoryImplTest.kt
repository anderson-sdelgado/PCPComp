package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class MovEquipVisitTercRepositoryImplTest {

    val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
        idMovEquipVisitTerc = 1,
        nroMatricVigiaMovEquipVisitTerc = 1000,
        idLocalMovEquipVisitTerc = 1000,
        tipoMovEquipVisitTerc = TypeMov.INPUT,
        idVisitTercMovEquipVisitTerc = 1000,
        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
        dthrMovEquipVisitTerc = 1723213270250,
        veiculoMovEquipVisitTerc = "TESTE",
        placaMovEquipVisitTerc = "TESTE",
        destinoMovEquipVisitTerc = "TESTE",
        observMovEquipVisitTerc = "TESTE",
        statusMovEquipVisitTerc = StatusData.OPEN,
        statusSendMovEquipVisitTerc = StatusSend.SEND,
        statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
    )
    val movEquipVisitTerc = MovEquipVisitTerc(
        idMovEquipVisitTerc = 1,
        nroMatricVigiaMovEquipVisitTerc = 1000,
        idLocalMovEquipVisitTerc = 1000,
        tipoMovEquipVisitTerc = TypeMov.INPUT,
        idVisitTercMovEquipVisitTerc = 1000,
        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
        dthrMovEquipVisitTerc = Date(1723213270250),
        veiculoMovEquipVisitTerc = "TESTE",
        placaMovEquipVisitTerc = "TESTE",
        destinoMovEquipVisitTerc = "TESTE",
        observMovEquipVisitTerc = "TESTE",
        statusMovEquipVisitTerc = StatusData.OPEN,
        statusSendMovEquipVisitTerc = StatusSend.SEND,
        statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
    )

    @Test
    fun `Check failure Datasource in MovEquipVisitTercRoomDatasource listOpen`() = runTest {
        val movEquipVisitTercSharedPreferencesDatasource = mock<MovEquipVisitTercSharedPreferencesDatasource>()
        val movEquipVisitTercRoomDatasource = mock<MovEquipVisitTercRoomDatasource>()
        whenever(movEquipVisitTercRoomDatasource.listOpen()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasource.listOpen",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipVisitTercRepositoryImpl(
            movEquipVisitTercSharedPreferencesDatasource,
            movEquipVisitTercRoomDatasource
        )
        val result = repository.listOpen()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRoomDatasource.listOpen"
        )
    }

    @Test
    fun `Check failure success if have success in MovEquipVisitTercRoomDatasource listOpen`() =
        runTest {
            val movEquipVisitTercSharedPreferencesDatasource = mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource = mock<MovEquipVisitTercRoomDatasource>()
            whenever(movEquipVisitTercRoomDatasource.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTercRoomModel
                    )
                )
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.listOpen()
            assertTrue(result.isSuccess)
            val resultList = result.getOrNull()!!
            assertEquals(
                resultList[0],
                movEquipVisitTerc
            )
        }

    @Test
    fun `Check failure Datasource in MovEquipVisitTercRoomDatasource setClose`() = runTest {
        val movEquipVisitTercSharedPreferencesDatasource = mock<MovEquipVisitTercSharedPreferencesDatasource>()
        val movEquipVisitTercRoomDatasource = mock<MovEquipVisitTercRoomDatasource>()
        whenever(movEquipVisitTercRoomDatasource.setClose(movEquipVisitTercRoomModel)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasource.setClose",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipVisitTercRepositoryImpl(
            movEquipVisitTercSharedPreferencesDatasource,
            movEquipVisitTercRoomDatasource
        )
        val result = repository.setClose(movEquipVisitTerc)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRoomDatasource.setClose"
        )
    }

    @Test
    fun `Check failure success if have success in MovEquipVisitTercRoomDatasource setClose`() =
        runTest {
            val movEquipVisitTercSharedPreferencesDatasource = mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource = mock<MovEquipVisitTercRoomDatasource>()
            whenever(movEquipVisitTercRoomDatasource.setClose(movEquipVisitTercRoomModel)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.setClose(movEquipVisitTerc)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }
}