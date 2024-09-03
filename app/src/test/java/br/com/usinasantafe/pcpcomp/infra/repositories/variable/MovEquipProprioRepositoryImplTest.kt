package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.MovEquipProprioRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipProprioRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipProprioRetrofitModelOutput
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovEquipProprioRepositoryImplTest {

    @Test
    fun `Check failure Datasource in MovEquipProprioRoomDatasource listOpen`() = runTest {
        val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
        val movEquipProprioSharedPreferencesDatasource =
            mock<MovEquipProprioSharedPreferencesDatasource>()
        val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
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
            movEquipProprioRoomDatasource,
            movEquipProprioRetrofitDatasource
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
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprio = MovEquipProprio(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprioRoomModel
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
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
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMov.INPUT,
            dthrMovEquipProprio = 1723213270250,
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val movEquipProprio = MovEquipProprio(
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMov.INPUT,
            dthrMovEquipProprio = Date(1723213270250),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
        val movEquipProprioSharedPreferencesDatasource =
            mock<MovEquipProprioSharedPreferencesDatasource>()
        val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
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
            movEquipProprioRoomDatasource,
            movEquipProprioRetrofitDatasource
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
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprio = MovEquipProprio(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setClose(movEquipProprioRoomModel)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setClose(movEquipProprio)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource start`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
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
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
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
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.start(TypeMov.INPUT)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setMatricColab FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setMatricColab(19759)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.setMatricColab",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.setMatricColab"
            )
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setMatricColab FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setMatricColab(19759, 1)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.setMatricColab",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.setMatricColab"
            )
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setMatricColab execute correctly FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setMatricColab(19759)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setMatricColab execute correctly FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setMatricColab(19759, 1)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }
    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setIdEquip FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setIdEquip(10)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.setIdEquip",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.setIdEquip"
            )
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setIdEquip FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setIdEquip(10, 1)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.setIdEquip",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.setIdEquip"
            )
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setIdEquip execute correctly FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setIdEquip(10)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setIdEquip execute correctly FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setIdEquip(10, 1)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setDestino FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setDestino("Teste")).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.setDestino",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setDestino(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.setDestino"
            )
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setDestino FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setDestino("Teste", 1)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.setDestino",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setDestino(
                destino = "Teste",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.setDestino"
            )
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setDestino execute correctly FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setDestino("Teste")).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setDestino(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setDestino execute correctly FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setDestino("Teste", 1)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setDestino(
                destino = "Teste",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource getTipoMov`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.get()).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.getTipoMov()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.get"
            )
        }

    @Test
    fun `Check return tipoMov if have MovEquipProprioSharedPreferencesDatasource getTipoMov execute correctly`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.get()).thenReturn(
                Result.success(
                    MovEquipProprioSharedPreferencesModel(
                        tipoMovEquipProprio = TypeMov.INPUT
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.getTipoMov()
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!,
                TypeMov.INPUT
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setNotaFiscal FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setNotaFiscal(123456)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.setNotaFiscal",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setNotaFiscal(
                notaFiscal = 123456,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.setNotaFiscal"
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setNotaFiscal FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setNotaFiscal(123456, 1)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.setNotaFiscal",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setNotaFiscal(
                notaFiscal = 123456,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.setNotaFiscal"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setNotaFiscal execute correctly FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setNotaFiscal(123456)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setNotaFiscal(
                notaFiscal = 123456,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setNotaFiscal execute correctly FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setNotaFiscal(123456, 1)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setNotaFiscal(
                notaFiscal = 123456,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setObserv FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setObserv("TESTE OBSERV")).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.setObserv",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setObserv(
                observ = "TESTE OBSERV",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.setObserv"
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setObserv FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setObserv("TESTE OBSERV", 1)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.setObserv",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setObserv(
                observ = "TESTE OBSERV",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.setObserv"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setObserv execute correctly FlowApp ADD`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setObserv("TESTE OBSERV")).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setObserv(
                observ = "TESTE OBSERV",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setObserv execute correctly FlowApp CHANGE`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.setObserv("TESTE OBSERV", 1)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setObserv(
                observ = "TESTE OBSERV",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have erros in MovEquipProprioSharedPreferencesDatasource get()`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.get()).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.save(19759, 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.get"
            )
        }

    @Test
    fun `Check return failure if have erros in MovEquipProprioSharedPreferencesDatasource save`() =
        runTest {
            val movEquipProprioSharedPreferencesModel = MovEquipProprioSharedPreferencesModel(
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
            )
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.get()).thenReturn(
                Result.success(
                    movEquipProprioSharedPreferencesModel
                )
            )
            whenever(movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)).thenReturn(
                Result.success(1L)
            )
            whenever(movEquipProprioSharedPreferencesDatasource.clear()).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioSharedPreferencesDatasource.clear",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.save(19759, 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.clear"
            )
        }

    @Test
    fun `Chech return failure if MovEquipProprioRepository save return zero`() =
        runTest {
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioSharedPreferencesModel = MovEquipProprioSharedPreferencesModel(
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.get()).thenReturn(
                Result.success(
                    movEquipProprioSharedPreferencesModel
                )
            )
            whenever(movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)).thenReturn(
                Result.success(0L)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.save(19759, 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepositoryImpl.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: Id is 0"
            )
        }

    @Test
    fun `Check return id if MovEquipProprioRepositoryImpl save execute successfully`() =
        runTest {
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioSharedPreferencesModel = MovEquipProprioSharedPreferencesModel(
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.get()).thenReturn(
                Result.success(
                    movEquipProprioSharedPreferencesModel
                )
            )
            whenever(movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)).thenReturn(
                Result.success(1L)
            )
            whenever(movEquipProprioSharedPreferencesDatasource.clear()).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.save(19759, 1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, 1)
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource checkSend`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.checkSend()).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.checkSend",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.checkSend()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.checkSend"
            )
        }

    @Test
    fun `Check return false if not have MovEquipProprio to send`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.checkSend()).thenReturn(
                Result.success(false)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.checkSend()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if have MovEquipProprio to send`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.checkSend()).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.checkSend()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioRoomDatasource listSend`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.listSend()).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.listSend",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.listSend()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.listSend"
            )
        }
    
    @Test
    fun `Check return failure if MovEquipProprioRepository listSend execute successfully`() =
        runTest {
            val movEquipProprioRoomModel = MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.listSend()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprioRoomModel
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.listSend()
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!![0].idMovEquipProprio,
                1
            )
            assertEquals(
                result.getOrNull()!![0].matricColabMovEquipProprio,
                19759
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRetrofitDatasource send`() =
        runTest {
            val movEquipProprioRetrofitModelOutput = MovEquipProprioRetrofitModelOutput(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = 1,
                dthrMovEquipProprio = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(Date(1723213270250)),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                nroAparelhoMovEquipProprio = 16997417840L,
                movEquipProprioEquipSegList = emptyList(),
                movEquipProprioPassagList = emptyList(),
            )

            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
            val listRetrofitModel = listOf(movEquipProprioRetrofitModelOutput)
            val list = listOf(movEquipProprio)
            val token = "123456"
            val number = 16997417840L
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRetrofitDatasource.send(listRetrofitModel, token)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRetrofitDatasource.send",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.send(list, number, token)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRetrofitDatasource.send"
            )
        }

    @Test
    fun `Check return list if MovEquipProprioRepository send execute successfully`() =
        runTest {
            val movEquipProprioRetrofitModelOutput = MovEquipProprioRetrofitModelOutput(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = 1,
                dthrMovEquipProprio = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(Date(1723213270250)),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                nroAparelhoMovEquipProprio = 16997417840L,
                movEquipProprioEquipSegList = emptyList(),
                movEquipProprioPassagList = emptyList(),
            )

            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )

            val movEquipProprioRetrofitModelInput = MovEquipProprioRetrofitModelInput(
                idMovEquipProprio = 1
            )

            val listRetrofitModelOutput = listOf(movEquipProprioRetrofitModelOutput)
            val listRetrofitModelInput = listOf(movEquipProprioRetrofitModelInput)
            val list = listOf(movEquipProprio)
            val token = "123456"
            val number = 16997417840L
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRetrofitDatasource.send(listRetrofitModelOutput, token)).thenReturn(
                Result.success(listRetrofitModelInput)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.send(list, number, token)
            assertTrue(result.isSuccess)
            val listResult = result.getOrNull()!!
            assertEquals(listResult[0].idMovEquipProprio, 1)
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRoomDatasource setSent`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1,
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(
                movEquipProprioRoomDatasource.setSent(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.setSent",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setSent(
                listOf(
                    movEquipProprio
                )
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.setSent"
            )
        }

    @Test
    fun `Check return failure with 2 elements if have error in MovEquipProprioRoomDatasource setSent`() =
        runTest {
            val movEquipProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
            )
            val movEquipProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(
                movEquipProprioRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioRoomDatasource.setSent(2)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.setSent",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setSent(
                listOf(
                    movEquipProprio1,
                    movEquipProprio2
                )
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.setSent"
            )
        }

    @Test
    fun `Check return true with 2 elements if MovEquipProprioRoomDatasource setSent execute successfully`() =
        runTest {
            val movEquipProprio1 = MovEquipProprio(
                idMovEquipProprio = 1,
            )
            val movEquipProprio2 = MovEquipProprio(
                idMovEquipProprio = 2,
            )
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(
                movEquipProprioRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioRoomDatasource.setSent(2)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.setSent(
                listOf(
                    movEquipProprio1,
                    movEquipProprio2
                )
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRoomDatasource get`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.get(1)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.get(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.get"
            )
        }

    @Test
    fun `Check return model if MovEquipProprioRepository get execute successfully`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            val movEquipProprioRetrofitDatasource = mock<MovEquipProprioRetrofitDatasource>()
            whenever(movEquipProprioRoomDatasource.get(1)).thenReturn(
                Result.success(
                    MovEquipProprioRoomModel(
                        idMovEquipProprio = 1,
                        matricVigiaMovEquipProprio = 19759,
                        idLocalMovEquipProprio = 1,
                        tipoMovEquipProprio = TypeMov.INPUT,
                        dthrMovEquipProprio = 1723213270250,
                        idEquipMovEquipProprio = 1,
                        matricColabMovEquipProprio = 19759,
                        destinoMovEquipProprio = "TESTE DESTINO",
                        notaFiscalMovEquipProprio = 123456789,
                        observMovEquipProprio = "TESTE OBSERV",
                        statusMovEquipProprio = StatusData.OPEN,
                        statusSendMovEquipProprio = StatusSend.SEND
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource,
                movEquipProprioRetrofitDatasource
            )
            val result = repository.get(1)
            assertTrue(result.isSuccess)
            val model = result.getOrNull()!!
            assertEquals(model.idMovEquipProprio, 1)
            assertEquals(model.destinoMovEquipProprio, "TESTE DESTINO")
        }
}