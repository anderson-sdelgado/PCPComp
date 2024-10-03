package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcpcomp.utils.FlowApp
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

    @Test
    fun `Check failure Datasource in MovEquipResidenciaRoomDatasource listOpen`() = runTest {
        val movEquipResidenciaSharedPreferencesDatasource =
            mock<MovEquipResidenciaSharedPreferencesDatasource>()
        val movEquipResidenciaRoomDatasource =
            mock<MovEquipResidenciaRoomDatasource>()
        whenever(
            movEquipResidenciaRoomDatasource.listOpen()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasource.listOpen",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipResidenciaRepositoryImpl(
            movEquipResidenciaSharedPreferencesDatasource,
            movEquipResidenciaRoomDatasource
        )
        val result = repository.listOpen()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRoomDatasource.listOpen"
        )
    }

    @Test
    fun `Check return true if have success in MovEquipResidenciaRoomDatasource listOpen`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidenciaRoomModel
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
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
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            idMovEquipResidencia = 1,
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val movEquipResidencia = MovEquipResidencia(
            idMovEquipResidencia = 1,
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = Date(1723213270250),
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val movEquipResidenciaSharedPreferencesDatasource =
            mock<MovEquipResidenciaSharedPreferencesDatasource>()
        val movEquipResidenciaRoomDatasource =
            mock<MovEquipResidenciaRoomDatasource>()
        whenever(
            movEquipResidenciaRoomDatasource.setClose(movEquipResidenciaRoomModel)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasource.setClose",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipResidenciaRepositoryImpl(
            movEquipResidenciaSharedPreferencesDatasource,
            movEquipResidenciaRoomDatasource
        )
        val result = repository.setClose(movEquipResidencia)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRoomDatasource.setClose"
        )
    }

    @Test
    fun `Check return true if have success in MovEquipResidenciaRoomDatasource setClose`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setClose(movEquipResidenciaRoomModel)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setClose(movEquipResidencia)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Get - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.get(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.get"
            )
        }

    @Test
    fun `Check return entity if Get execute successfully`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.get(1)
            assertTrue(result.isSuccess)
            val entity = result.getOrNull()!!
            assertEquals(entity.placaMovEquipResidencia, "PLACA TESTE")
            assertEquals(entity.motoristaMovEquipResidencia, "MOTORISTA TESTE")
            assertEquals(entity.veiculoMovEquipResidencia, "VEICULO TESTE")
        }

    @Test
    fun `GetMotorista - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.getMotorista(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.get"
            )
        }

    @Test
    fun `Check return motorista if GetMotorista execute successfully`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.getMotorista(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!, "MOTORISTA TESTE"
            )
        }

    @Test
    fun `GetObserv - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.getObserv(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.get"
            )
        }

    @Test
    fun `Check return observacao if GetObserv execute successfully`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.getObserv(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!, "OBSERV TESTE"
            )
        }

    @Test
    fun `GetPlaca - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.getPlaca(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.get"
            )
        }

    @Test
    fun `Check return placa if GetPlaca execute successfully`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.getPlaca(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!, "PLACA TESTE"
            )
        }

    @Test
    fun `GetVeiculo - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.getVeiculo(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.get"
            )
        }

    @Test
    fun `Check return veiculo if GetVeiculo execute successfully`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.getVeiculo(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!, "VEICULO TESTE"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource listInputOpen`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.listInside()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.listInputOpen",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.listInside()
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.listInputOpen"
            )
        }

    @Test
    fun `Check return true if have success in MovEquipResidenciaRoomDatasource listInputOpen`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidenciaRoomModel
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.listOpen()
            assertTrue(result.isSuccess)
            val resultList = result.getOrNull()!!
            assertEquals(resultList[0].idMovEquipResidencia, 1)
            assertEquals(resultList[0].nroMatricVigiaMovEquipResidencia, 1000)
            assertEquals(resultList[0].idLocalMovEquipResidencia, 1)
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaSharedPreferencesDatasource Get`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaSharedPreferencesDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val usecase = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(usecase.isFailure, true)
            assertEquals(
                usecase.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasource.get"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource Save`() =
        runTest {
            val movEquipResidenciaSharedPreferencesModel = MovEquipResidenciaSharedPreferencesModel(
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(movEquipResidenciaSharedPreferencesModel)
            )
            val movEquipResidenciaRoomModel =
                movEquipResidenciaSharedPreferencesModel.sharedPreferencesModelToEntity()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipResidenciaRoomDatasource.save(movEquipResidenciaRoomModel)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.save",
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val usecase = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(usecase.isFailure, true)
            assertEquals(
                usecase.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.save"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource Save return is 0`() =
        runTest {
            val movEquipResidenciaSharedPreferencesModel = MovEquipResidenciaSharedPreferencesModel(
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(movEquipResidenciaSharedPreferencesModel)
            )
            val movEquipResidenciaRoomModel =
                movEquipResidenciaSharedPreferencesModel.sharedPreferencesModelToEntity()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipResidenciaRoomDatasource.save(movEquipResidenciaRoomModel)
            ).thenReturn(
                Result.success(0)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepositoryImpl.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: Id is 0"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaSharedPreferencesDatasource Clear`() =
        runTest {
            val movEquipResidenciaSharedPreferencesModel = MovEquipResidenciaSharedPreferencesModel(
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(movEquipResidenciaSharedPreferencesModel)
            )
            val movEquipResidenciaRoomModel =
                movEquipResidenciaSharedPreferencesModel.sharedPreferencesModelToEntity()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipResidenciaRoomDatasource.save(movEquipResidenciaRoomModel)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaSharedPreferencesDatasource.clear",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasource.clear"
            )
        }

    @Test
    fun `Check return true if MovEquipResidenciaRepositoryImpl save execute successfully`() =
        runTest {
            val movEquipResidenciaSharedPreferencesModel = MovEquipResidenciaSharedPreferencesModel(
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(movEquipResidenciaSharedPreferencesModel)
            )
            val movEquipResidenciaRoomModel =
                movEquipResidenciaSharedPreferencesModel.sharedPreferencesModelToEntity()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipResidenciaRoomDatasource.save(movEquipResidenciaRoomModel)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, 1)
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaSharedPreferencesDatasource setMotorista - FlowApp ADD`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setMotorista(
                    motorista = "MOTORISTA TESTE"
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaSharedPreferencesDatasource.setMotorista",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setMotorista(
                motorista = "MOTORISTA TESTE",
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasource.setMotorista"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource setMotorista - FlowApp CHANGE`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setMotorista(
                    motorista = "MOTORISTA TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.setMotorista",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setMotorista(
                motorista = "MOTORISTA TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.setMotorista"
            )
        }

    @Test
    fun `Check return true if SetMotorista execute successfully - FlowApp ADD`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setMotorista(
                    motorista = "MOTORISTA TESTE"
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setMotorista(
                motorista = "MOTORISTA TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetMotorista execute successfully - FlowApp CHANGE`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setMotorista(
                    motorista = "MOTORISTA TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setMotorista(
                motorista = "MOTORISTA TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaSharedPreferencesDatasource setObserv - FlowApp ADD`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setObserv(
                    observ = "OBSERV TESTE"
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaSharedPreferencesDatasource.setObserv",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setObserv(
                observ = "OBSERV TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasource.setObserv"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource setObserv - FlowApp CHANGE`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setObserv(
                    observ = "OBSERV TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.setObserv",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setObserv(
                observ = "OBSERV TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.setObserv"
            )
        }

    @Test
    fun `Check return true if SetObserv execute successfully - FlowApp ADD`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setObserv(
                    observ = "OBSERV TESTE"
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setObserv(
                observ = "OBSERV TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetObserv execute successfully - FlowApp CHANGE`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setObserv(
                    observ = "OBSERV TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setObserv(
                observ = "OBSERV TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaSharedPreferencesDatasource setPlaca - FlowApp ADD`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setPlaca(
                    placa = "PLACA TESTE"
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaSharedPreferencesDatasource.setPlaca",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setPlaca(
                placa = "PLACA TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasource.setPlaca"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource setPlaca - FlowApp CHANGE`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setPlaca(
                    placa = "PLACA TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.setPlaca",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setPlaca(
                placa = "PLACA TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.setPlaca"
            )
        }

    @Test
    fun `Check return true if SetPlaca execute successfully - FlowApp ADD`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setPlaca(
                    placa = "PLACA TESTE"
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setPlaca(
                placa = "PLACA TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetPlaca execute successfully - FlowApp CHANGE`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setPlaca(
                    placa = "PLACA TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setPlaca(
                placa = "PLACA TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaSharedPreferencesDatasource setVeiculo - FlowApp ADD`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setVeiculo(
                    veiculo = "VEICULO TESTE"
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaSharedPreferencesDatasource.setVeiculo",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setVeiculo(
                veiculo = "VEICULO TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasource.setVeiculo"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource setVeiculo - FlowApp CHANGE`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setVeiculo(
                    veiculo = "VEICULO TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.setVeiculo",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setVeiculo(
                veiculo = "VEICULO TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.setVeiculo"
            )
        }

    @Test
    fun `Check return true if SetVeiculo execute successfully - FlowApp ADD`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setVeiculo(
                    veiculo = "VEICULO TESTE"
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setVeiculo(
                veiculo = "VEICULO TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetVeiculo execute successfully - FlowApp CHANGE`() =
        runTest {
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setVeiculo(
                    veiculo = "VEICULO TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setVeiculo(
                veiculo = "VEICULO TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource start`() =
        runTest {
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.start(
                    movEquipResidencia.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaSharedPreferencesDatasource.start",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.start(movEquipResidencia)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasource.start"
            )
        }

    @Test
    fun `Check return true if Start execute successfully`() =
        runTest {
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.start(
                    movEquipResidencia.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.start(movEquipResidencia)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check failure Datasource in MovEquipResidenciaRoomDatasource setOutside`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            idMovEquipResidencia = 1,
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val movEquipResidencia = MovEquipResidencia(
            idMovEquipResidencia = 1,
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = Date(1723213270250),
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val movEquipResidenciaSharedPreferencesDatasource =
            mock<MovEquipResidenciaSharedPreferencesDatasource>()
        val movEquipResidenciaRoomDatasource =
            mock<MovEquipResidenciaRoomDatasource>()
        whenever(
            movEquipResidenciaRoomDatasource.setOutside(movEquipResidenciaRoomModel)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasource.setOutside",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipResidenciaRepositoryImpl(
            movEquipResidenciaSharedPreferencesDatasource,
            movEquipResidenciaRoomDatasource
        )
        val result = repository.setOutside(movEquipResidencia)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRoomDatasource.setOutside"
        )
    }

    @Test
    fun `Check return true if have success in MovEquipResidenciaRoomDatasource setOutside`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaSharedPreferencesDatasource =
                mock<MovEquipResidenciaSharedPreferencesDatasource>()
            val movEquipResidenciaRoomDatasource =
                mock<MovEquipResidenciaRoomDatasource>()
            whenever(
                movEquipResidenciaRoomDatasource.setOutside(movEquipResidenciaRoomModel)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipResidenciaRepositoryImpl(
                movEquipResidenciaSharedPreferencesDatasource,
                movEquipResidenciaRoomDatasource
            )
            val result = repository.setOutside(movEquipResidencia)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

}