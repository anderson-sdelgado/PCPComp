package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.MovEquipResidenciaRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipResidenciaRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipResidenciaRetrofitModelOutput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipVisitTercRetrofitModelInput
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovEquipResidenciaRepositoryImplTest {

    private val movEquipResidenciaSharedPreferencesDatasource =
        mock<MovEquipResidenciaSharedPreferencesDatasource>()
    private val movEquipResidenciaRoomDatasource =
        mock<MovEquipResidenciaRoomDatasource>()
    private val movEquipResidenciaRetrofitDatasource =
        mock<MovEquipResidenciaRetrofitDatasource>()
    private fun getRepository() = MovEquipResidenciaRepositoryImpl(
        movEquipResidenciaSharedPreferencesDatasource,
        movEquipResidenciaRoomDatasource,
        movEquipResidenciaRetrofitDatasource
    )

    @Test
    fun `Check failure Datasource in MovEquipResidenciaRoomDatasource listOpen`() = runTest {
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
        val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidenciaRoomModel
                    )
                )
            )
            val repository = getRepository()
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
        val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.setClose(movEquipResidenciaRoomModel)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setClose(movEquipResidencia)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Get - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = getRepository()
            val result = repository.getMotorista(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!, "MOTORISTA TESTE"
            )
        }

    @Test
    fun `GetObserv - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = getRepository()
            val result = repository.getObserv(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!, "OBSERV TESTE"
            )
        }

    @Test
    fun `GetPlaca - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = getRepository()
            val result = repository.getPlaca(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!, "PLACA TESTE"
            )
        }

    @Test
    fun `GetVeiculo - Check return failure if have error in MovEquipResidenciaRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipResidenciaRoomModel)
            )
            val repository = getRepository()
            val result = repository.getVeiculo(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!, "VEICULO TESTE"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource listInputOpen`() =
        runTest {
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
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidenciaRoomModel
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listOpen()
            assertTrue(result.isSuccess)
            val resultList = result.getOrNull()!!
            assertEquals(resultList[0].idMovEquipResidencia, 1)
            assertEquals(resultList[0].nroMatricVigiaMovEquipResidencia, 19759)
            assertEquals(resultList[0].idLocalMovEquipResidencia, 1)
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaSharedPreferencesDatasource Get`() =
        runTest {
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setMotorista(
                    motorista = "MOTORISTA TESTE"
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.setMotorista(
                    motorista = "MOTORISTA TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setObserv(
                    observ = "OBSERV TESTE"
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.setObserv(
                    observ = "OBSERV TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setPlaca(
                    placa = "PLACA TESTE"
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.setPlaca(
                    placa = "PLACA TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.setVeiculo(
                    veiculo = "VEICULO TESTE"
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.setVeiculo(
                    veiculo = "VEICULO TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
            val repository = getRepository()
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
            whenever(
                movEquipResidenciaSharedPreferencesDatasource.start(
                    movEquipResidencia.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
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
        val repository = getRepository()
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
            whenever(
                movEquipResidenciaRoomDatasource.setOutside(movEquipResidenciaRoomModel)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setOutside(movEquipResidencia)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check return failure if have errors in MovEquipResidenciaRoomDatasource checkSend`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.checkSend()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.checkSend",
                    )
                )
            )
            val repository = getRepository()
            val result = repository.checkSend()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.checkSend"
            )
        }

    @Test
    fun `CheckSend - Check return false if not have MovEquipResidencia to send`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val repository = getRepository()
            val result = repository.checkSend()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun `CheckSend - Check return true if have MovEquipResidencia to send`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.checkSend()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have errors in MovEquipResidenciaRoomDatasource listSend`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.listSend()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.listSend",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listSend()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.listSend"
            )
        }

    @Test
    fun `Check return list if MovEquipVisitTercRepository listSend execute successfully`() =
        runTest {
            val roomModel = MovEquipResidenciaRoomModel(
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
            whenever(
                movEquipResidenciaRoomDatasource.listSend()
            ).thenReturn(
                Result.success(
                    listOf(roomModel)
                )
            )
            val repository = getRepository()
            val result = repository.listSend()
            assertTrue(result.isSuccess)
            val resultList = result.getOrNull()!!
            assertEquals(resultList[0].idMovEquipResidencia, 1)
            assertEquals(resultList[0].observMovEquipResidencia, "OBSERV TESTE")
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRetrofitDatasource send`() =
        runTest {
            val retrofitModel = MovEquipResidenciaRetrofitModelOutput(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT.ordinal + 1,
                dthrMovEquipResidencia = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                nroAparelhoMovEquipResidencia = 16997417840L
            )
            val entity = MovEquipResidencia(
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
            val retrofitModelList = listOf(retrofitModel)
            val list = listOf(entity)
            val token = "123456"
            val number = 16997417840L
            whenever(
                movEquipResidenciaRetrofitDatasource.send(
                    list = retrofitModelList,
                    token = token
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRetrofitDatasource.send",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.send(
                list = list,
                token = token,
                number = number
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRetrofitDatasource.send"
            )
        }

    @Test
    fun `Check return list if Send execute successfully`() =
        runTest {
            val retrofitModel = MovEquipResidenciaRetrofitModelOutput(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT.ordinal + 1,
                dthrMovEquipResidencia = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                nroAparelhoMovEquipResidencia = 16997417840L
            )
            val entity = MovEquipResidencia(
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
            val retrofitModelInput = MovEquipResidenciaRetrofitModelInput(
                idMovEquipResidencia = 1,
            )
            val retrofitModelList = listOf(retrofitModel)
            val entityList = listOf(entity)
            val token = "123456"
            val number = 16997417840L
            whenever(
                movEquipResidenciaRetrofitDatasource.send(
                    list = retrofitModelList,
                    token = token
                )
            ).thenReturn(
                Result.success(
                    listOf(retrofitModelInput)
                )
            )
            val repository = getRepository()
            val result = repository.send(
                list = entityList,
                token = token,
                number = number
            )
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 1)
            val entityRet = list[0]
            assertEquals(entityRet.idMovEquipResidencia, 1)
        }

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRoomDatasource setSent`() =
        runTest {
            val entity = MovEquipResidencia(
                idMovEquipResidencia = 1,
            )
            whenever(
                movEquipResidenciaRoomDatasource.setSent(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.setSent",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setSent(
                list = listOf(
                    entity
                )
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.setSent"
            )
        }

    @Test
    fun `Check return failure with 2 elements if have error in MovEquipResidenciaRoomDatasource setSent`() =
        runTest {
            val entity1 = MovEquipResidencia(
                idMovEquipResidencia = 1,
            )
            val entity2 = MovEquipResidencia(
                idMovEquipResidencia = 2,
            )
            whenever(
                movEquipResidenciaRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRoomDatasource.setSent(2)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.setSent",
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setSent(
                list = listOf(
                    entity1,
                    entity2
                )
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.setSent"
            )
        }

    @Test
    fun `Check return true with 2 elements if MovEquipResidenciaRoomDatasource setSent execute successfully`() =
        runTest {
            val entity1 = MovEquipResidencia(
                idMovEquipResidencia = 1,
            )
            val entity2 = MovEquipResidencia(
                idMovEquipResidencia = 2,
            )
            whenever(
                movEquipResidenciaRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipResidenciaRoomDatasource.setSent(2)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setSent(
                list = listOf(
                    entity1,
                    entity2
                )
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in movEquipResidenciaRoomDatasource get`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.get",
                    )
                )
            )
            val repository = getRepository()
            val result = repository.delete(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.get"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipResidenciaRoomDatasource delete`() =
        runTest {
            val roomModel = MovEquipResidenciaRoomModel(
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
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            whenever(
                movEquipResidenciaRoomDatasource.delete(roomModel)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.delete",
                    )
                )
            )
            val repository = getRepository()
            val result = repository.delete(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.delete"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioRepository delete execute successfully`() =
        runTest {
            val roomModel = MovEquipResidenciaRoomModel(
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
            whenever(
                movEquipResidenciaRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            whenever(
                movEquipResidenciaRoomDatasource.delete(roomModel)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.delete(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have errors in MovEquipResidenciaRoomDatasource listSent`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.listSent()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipResidenciaRoomDatasource.listSent",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listSent()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaRoomDatasource.listSent"
            )
        }

    @Test
    fun `Check return list if MovEquipResidenciaRepository listSent execute successfully`() =
        runTest {
            val roomModel = MovEquipResidenciaRoomModel(
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
                statusSendMovEquipResidencia = StatusSend.SENT,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipResidenciaRoomDatasource.listSent()
            ).thenReturn(
                Result.success(
                    listOf(roomModel)
                )
            )
            val repository = getRepository()
            val result = repository.listSent()
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 1)
            val model = list[0]
            assertEquals(
                model.idMovEquipResidencia,
                1
            )
            assertEquals(
                model.placaMovEquipResidencia,
                "PLACA TESTE"
            )
            assertEquals(
                model.statusSendMovEquipResidencia,
                StatusSend.SENT
            )
        }

    @Test
    fun `Check return failure if have errors in MovEquipResidenciaRoomDatasource checkOpen`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.checkOpen()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioRoomDatasource.checkOpen",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.checkOpen()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioRoomDatasource.checkOpen"
            )
        }

    @Test
    fun `CheckOpen - Check return false if not have MovEquipResidencia to open`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            val repository = getRepository()
            val result = repository.checkOpen()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun `CheckSend - Check return true if have MovEquipResidencia to open`() =
        runTest {
            whenever(
                movEquipResidenciaRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.checkOpen()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

}