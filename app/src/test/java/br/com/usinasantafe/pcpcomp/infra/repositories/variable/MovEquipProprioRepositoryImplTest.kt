package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
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
import java.util.Date

class MovEquipProprioRepositoryImplTest {

    val movEquipProprioRoomModel = MovEquipProprioRoomModel(
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

    val movEquipProprioSharedPreferencesModel = MovEquipProprioSharedPreferencesModel(
        tipoMovEquipProprio = TypeMov.INPUT,
        dthrMovEquipProprio = Date(1723213270250),
        idEquipMovEquipProprio = 1,
        nroMatricColabMovEquipProprio = 19759,
        destinoMovEquipProprio = "TESTE DESTINO",
        nroNotaFiscalMovEquipProprio = 123456789,
        observMovEquipProprio = "TESTE OBSERV",
    )

    @Test
    fun `Check failure Datasource in MovEquipProprioRoomDatasource listOpen`() = runTest {
        val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
        val movEquipProprioSharedPreferencesDatasource =
            mock<MovEquipProprioSharedPreferencesDatasource>()
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
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
        val movEquipProprioSharedPreferencesDatasource =
            mock<MovEquipProprioSharedPreferencesDatasource>()
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
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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

    @Test
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setMatricColab`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
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
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setMatricColab execute correctly`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setMatricColab(19759)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
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
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setIdEquip`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
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
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setIdEquip execute correctly`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setIdEquip(10)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
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
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource setDestino`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
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
    fun `Check return success if have MovEquipProprioSharedPreferencesDatasource setDestino execute correctly`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setDestino("Teste")).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
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
    fun `Check failure if have failure in MovEquipProprioSharedPreferencesDatasource getTipoMov`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
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
            whenever(movEquipProprioSharedPreferencesDatasource.get()).thenReturn(
                Result.success(
                    MovEquipProprioSharedPreferencesModel(
                        tipoMovEquipProprio = TypeMov.INPUT
                    )
                )
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
            )
            val result = repository.getTipoMov()
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!,
                TypeMov.INPUT
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setNotaFiscal`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
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
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setNotaFiscal execute correctly`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setNotaFiscal(123456)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
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
    fun `Check return failure if have errors in MovEquipProprioSharedPreferencesDatasource setObserv`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
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
    fun `Check return true if MovEquipProprioSharedPreferencesDatasource setObserv execute correctly`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
            whenever(movEquipProprioSharedPreferencesDatasource.setObserv("TESTE OBSERV")).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioRepositoryImpl(
                movEquipProprioSharedPreferencesDatasource,
                movEquipProprioRoomDatasource
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
    fun `Check return failure if have erros in MovEquipProprioSharedPreferencesDatasource get()`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
            )
            val result = repository.save(19759, 1)
            assertTrue(result.isFailure)
            assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.get")
        }

    @Test
    fun `Check return failure if have erros in MovEquipProprioSharedPreferencesDatasource save`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
            )
            val result = repository.save(19759, 1)
            assertTrue(result.isFailure)
            assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> MovEquipProprioSharedPreferencesDatasource.clear")
        }

    @Test
    fun `Check return id if MovEquipProprioRepositoryImpl save execute successfully`() =
        runTest {
            val movEquipProprioRoomDatasource = mock<MovEquipProprioRoomDatasource>()
            val movEquipProprioSharedPreferencesDatasource =
                mock<MovEquipProprioSharedPreferencesDatasource>()
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
                movEquipProprioRoomDatasource
            )
            val result = repository.save(19759, 1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, 1)
        }
}