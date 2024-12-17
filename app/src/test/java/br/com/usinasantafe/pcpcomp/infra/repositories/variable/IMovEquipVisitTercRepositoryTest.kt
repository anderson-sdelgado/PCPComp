package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.MovEquipVisitTercRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelOutput
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IMovEquipVisitTercRepositoryTest {

    private val movEquipVisitTercSharedPreferencesDatasource =
        mock<MovEquipVisitTercSharedPreferencesDatasource>()
    private val movEquipVisitTercRoomDatasource =
        mock<MovEquipVisitTercRoomDatasource>()
    private val movEquipVisitTercRetrofitDatasource =
        mock<MovEquipVisitTercRetrofitDatasource>()

    private fun getRepository() = IMovEquipVisitTercRepository(
        movEquipVisitTercSharedPreferencesDatasource,
        movEquipVisitTercRoomDatasource,
        movEquipVisitTercRetrofitDatasource
    )

    @Test
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource listOpen`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listOpen()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.listOpen",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listOpen()
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.listOpen"
            )
        }

    @Test
    fun `Check return list if have success in MovEquipVisitTercRoomDatasource listOpen`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
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
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTercRoomModel
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listOpen()
            assertTrue(result.isSuccess)
            val resultList = result.getOrNull()!!
            assertEquals(
                resultList[0],
                movEquipVisitTerc
            )
        }

    @Test
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource setClose`() = runTest {
        whenever(
            movEquipVisitTercRoomDatasource.setClose(1)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasource.setClose",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.setClose(1)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRoomDatasource.setClose"
        )
    }

    @Test
    fun `Check return true if have success in MovEquipVisitTercRoomDatasource setClose`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setClose(1)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Get - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.get(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.get"
            )
        }

    @Test
    fun `Get - Check return entity if Get execute successfully`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            idMovEquipVisitTerc = 1,
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
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
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = Date(1723213270250),
            veiculoMovEquipVisitTerc = "TESTE",
            placaMovEquipVisitTerc = "TESTE",
            destinoMovEquipVisitTerc = "TESTE",
            observMovEquipVisitTerc = "TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
        )
        whenever(
            movEquipVisitTercRoomDatasource.get(1)
        ).thenReturn(
            Result.success(
                movEquipVisitTercRoomModel
            )
        )
        val repository = getRepository()
        val result = repository.get(1)
        assertTrue(result.isSuccess)
        val resultEntity = result.getOrNull()!!
        assertEquals(
            resultEntity,
            movEquipVisitTerc
        )
    }

    @Test
    fun `GetDestino - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getDestino(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.get"
            )
        }

    @Test
    fun `GetDestino - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = getRepository()
            val result = repository.getDestino(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "TESTE DESTINO")
        }

    @Test
    fun `GetIdVisitTerc - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getIdVisitTerc(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.get"
            )
        }

    @Test
    fun `GetIdVisitTerc - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = getRepository()
            val result = repository.getIdVisitTerc(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, 1000)
        }

    @Test
    fun `GetObserv - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getObserv(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.get"
            )
        }

    @Test
    fun `GetObserv - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = getRepository()
            val result = repository.getObserv(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull(), "TESTE OBSERV")
        }

    @Test
    fun `GetPlaca - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getPlaca(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.get"
            )
        }

    @Test
    fun `GetPlaca - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = getRepository()
            val result = repository.getPlaca(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "TESTE PLACA")
        }

    @Test
    fun `GetTypeVisitTerc - ADD - Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getTypeVisitTerc(
                FlowApp.ADD,
                1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.get"
            )
        }

    @Test
    fun `GetTypeVisitTerc - ADD - Check return type if MovEquipVisitTercSharedPreferencesDatasource Get execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    MovEquipVisitTercSharedPreferencesModel(
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getTypeVisitTerc(
                FlowApp.ADD,
                1
            )
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, TypeVisitTerc.TERCEIRO)
        }

    @Test
    fun `GetTypeVisitTerc - CHANGE - Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getTypeVisitTerc(
                FlowApp.CHANGE,
                1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.get"
            )
        }

    @Test
    fun `GetTypeVisitTerc - CHANGE - Check return true if MovEquipVisitTercSharedPreferencesDatasource Get execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipVisitTercRoomModel(
                        idMovEquipVisitTerc = 1,
                        nroMatricVigiaMovEquipVisitTerc = 19759,
                        idLocalMovEquipVisitTerc = 1,
                        tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                        idVisitTercMovEquipVisitTerc = 1000,
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                        dthrMovEquipVisitTerc = 1723213270250,
                        veiculoMovEquipVisitTerc = "TESTE",
                        placaMovEquipVisitTerc = "TESTE",
                        destinoMovEquipVisitTerc = "TESTE",
                        observMovEquipVisitTerc = "TESTE",
                        statusMovEquipVisitTerc = StatusData.OPEN,
                        statusSendMovEquipVisitTerc = StatusSend.SEND,
                        statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getTypeVisitTerc(
                FlowApp.CHANGE,
                1
            )
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, TypeVisitTerc.VISITANTE)
        }

    @Test
    fun `GetVeiculo - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getVeiculo(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.get"
            )
        }

    @Test
    fun `GetVeiculo - Check return true if MovEquipVisitTercRoomDatasource Get execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE VEICULO",
                placaMovEquipVisitTerc = "TESTE PLACA",
                destinoMovEquipVisitTerc = "TESTE DESTINO",
                observMovEquipVisitTerc = "TESTE OBSERV",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = getRepository()
            val result = repository.getVeiculo(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "TESTE VEICULO")
        }

    @Test
    fun `Check failure failure if have error in MovEquipVisitTercRoomDatasource listInputOpen`() =
        runTest {
            whenever(movEquipVisitTercRoomDatasource.listInside()).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.listInputOpen",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listInside()
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.listInputOpen"
            )
        }

    @Test
    fun `Check return list empty if listOpenInput execute successfully and list is empty`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listInside()
            ).thenReturn(
                Result.success(listOf())
            )
            val repository = getRepository()
            val result = repository.listInside()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!.isEmpty())
        }

    @Test
    fun `Check return list if listOpenInput execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(movEquipVisitTercRoomDatasource.listInside()).thenReturn(
                Result.success(listOf(movEquipVisitTercRoomModel))
            )
            val repository = getRepository()
            val result = repository.listInside()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!.isNotEmpty())
            assertEquals(result.getOrNull()!!.size, 1)
            val model = result.getOrNull()!![0]
            assertEquals(model.idMovEquipVisitTerc, 1)
            assertEquals(model.veiculoMovEquipVisitTerc, "VEICULO TESTE")
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource save`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.get()",
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
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.get()"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource Save`() =
        runTest {
            val modelSharedPreferences = MovEquipVisitTercSharedPreferencesModel(
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipVisitTercRoomDatasource.save(modelRoom)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.save",
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
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.save"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource Clear`() =
        runTest {
            val modelSharedPreferences = MovEquipVisitTercSharedPreferencesModel(
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipVisitTercRoomDatasource.save(modelRoom)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.clear",
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
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.clear"
            )
        }

    @Test
    fun `Check return failure if have error in id is 0`() =
        runTest {
            val modelSharedPreferences = MovEquipVisitTercSharedPreferencesModel(
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipVisitTercRoomDatasource.save(modelRoom)
            ).thenReturn(
                Result.success(0)
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.save(
                matricVigia = 19759,
                idLocal = 1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepositoryImpl.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: Id is 0"
            )
        }

    @Test
    fun `Check return true if Save execute successfully`() =
        runTest {
            val modelSharedPreferences = MovEquipVisitTercSharedPreferencesModel(
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.entityToSharedPreferencesModel()
                    .entityToRoomModel(
                        matricVigia = 19759,
                        idLocal = 1
                    )
            whenever(
                movEquipVisitTercRoomDatasource.save(modelRoom)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.clear()
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
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setDestino - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setDestino("DESTINO TESTE")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.setDestino",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setDestino(
                destino = "DESTINO TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.setDestino"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setDestino - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setDestino(
                    destino = "DESTINO TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setDestino",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setDestino(
                destino = "DESTINO TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setDestino"
            )
        }

    @Test
    fun `Check return true if SetDestino execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setDestino("DESTINO TESTE")
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setDestino(
                destino = "DESTINO TESTE",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetDestino execute successfully - FlowApp CHANCE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setDestino(
                    destino = "DESTINO TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setDestino(
                destino = "DESTINO TESTE",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setIdVisitTerc - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setIdVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setIdVisitTerc - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setIdVisitTerc(
                    idVisitTerc = 1,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setIdVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setIdVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setIdVisitTerc"
            )
        }

    @Test
    fun `Check return true if SetIdVisitTerc execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(1)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setIdVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetIdVisitTerc execute successfully - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setIdVisitTerc(
                    idVisitTerc = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setIdVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setObserv - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setObserv("Teste Observ")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.setObserv",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setObserv(
                observ = "Teste Observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.setObserv"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setObserv - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setObserv(
                    observ = "Teste Observ",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setObserv",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setObserv(
                observ = "Teste Observ",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setObserv"
            )
        }

    @Test
    fun `Check return true if SetObserv execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setObserv("Teste Observ")
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setObserv(
                observ = "Teste Observ",
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
                movEquipVisitTercRoomDatasource.setObserv(
                    observ = "Teste Observ",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setObserv(
                observ = "Teste Observ",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setPlaca - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setPlaca("Teste Placa")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.setPlaca",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setPlaca(
                placa = "Teste Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.setPlaca"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setPlaca - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setPlaca(
                    placa = "Teste Placa",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setPlaca",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setPlaca(
                placa = "Teste Placa",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setPlaca"
            )
        }

    @Test
    fun `Check return true if SetPlaca execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setPlaca("Teste Placa")
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setPlaca(
                placa = "Teste Placa",
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
                movEquipVisitTercRoomDatasource.setPlaca(
                    placa = "Teste Placa",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setPlaca(
                placa = "Teste Placa",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource SetTipo`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setTipo",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setTipoVisitTerc(TypeVisitTerc.TERCEIRO)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setTipo"
            )
        }

    @Test
    fun `Check return true if SetTipo execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setTipoVisitTerc(TypeVisitTerc.TERCEIRO)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setVeiculo - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setVeiculo("Teste Veiculo")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.setVeiculo",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setVeiculo(
                veiculo = "Teste Veiculo",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.setVeiculo"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setVeiculo - FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setVeiculo(
                    veiculo = "Teste Veiculo",
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setVeiculo",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setVeiculo(
                veiculo = "Teste Veiculo",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setVeiculo"
            )
        }

    @Test
    fun `Check return true if SetVeiculo execute successfully - FlowApp ADD`() =
        runTest {
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setVeiculo("Teste Veiculo")
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setVeiculo(
                veiculo = "Teste Veiculo",
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
                movEquipVisitTercRoomDatasource.setVeiculo(
                    veiculo = "Teste Veiculo",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setVeiculo(
                veiculo = "Teste Veiculo",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource start`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.start(
                    movEquipVisitTerc.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercSharedPreferencesDatasource.start",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.start(movEquipVisitTerc)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasource.start"
            )
        }

    @Test
    fun `Check return true if Start execute successfully`() =
        runTest {
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.OUTPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.start(
                    movEquipVisitTerc.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.start(movEquipVisitTerc)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource setOutside`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setOutside(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setClose",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setOutside(1)
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setClose"
            )
        }

    @Test
    fun `Check return true if have success in MovEquipVisitTercRoomDatasource setOutside`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setOutside(1)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

    @Test
    fun `Check return failure if have errors in MovEquipVisitTercRoomDatasource checkSend`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkSend()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.checkSend",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.checkSend()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.checkSend"
            )
        }

    @Test
    fun `CheckSend - Check return false if not have MovEquipVisitTerc to send`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkSend()
            ).thenReturn(
                Result.success(false)
            )
            val repository = getRepository()
            val result = repository.checkSend()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun `CheckSend - Check return true if have MovEquipVisitTerc to send`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.checkSend()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have errors in MovEquipVisitTercRoomDatasource listSend`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listSend()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.listSend",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listSend()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.listSend"
            )
        }

    @Test
    fun `Check return list if MovEquipVisitTercRepository listSend execute successfully`() =
        runTest {
            val roomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.listSend()
            ).thenReturn(
                Result.success(
                    listOf(
                        roomModel
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listSend()
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 1)
            val entity = list[0]
            assertEquals(entity.idMovEquipVisitTerc, 1)
            assertEquals(entity.placaMovEquipVisitTerc, "PLACA TESTE")
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioRetrofitDatasource send`() =
        runTest {
            val retrofitModel = MovEquipVisitTercRetrofitModelOutput(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT.ordinal + 1,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO.ordinal + 1,
                dthrMovEquipVisitTerc = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                nroAparelhoMovEquipVisitTerc = 16997417840L,
                movEquipVisitTercPassagList = emptyList(),
            )
            val entity = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            val retrofitModelList = listOf(retrofitModel)
            val list = listOf(entity)
            val token = "123456"
            val number = 16997417840L
            whenever(
                movEquipVisitTercRetrofitDatasource.send(
                    token = token,
                    list = retrofitModelList
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRetrofitDatasource.send",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.send(
                list = list,
                number = number,
                token = token
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRetrofitDatasource.send"
            )
        }

    @Test
    fun `Check return list if Send execute successfully`() =
        runTest {
            val retrofitModelOutput = MovEquipVisitTercRetrofitModelOutput(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT.ordinal + 1,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO.ordinal + 1,
                dthrMovEquipVisitTerc = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale("pt", "BR")
                ).format(
                    Date(1723213270250)
                ),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                nroAparelhoMovEquipVisitTerc = 16997417840L,
                movEquipVisitTercPassagList = emptyList(),
            )
            val entity = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeignerVisitTerc = StatusForeigner.INSIDE,
            )
            val retrofitModelInput = MovEquipVisitTercRetrofitModelInput(
                idMovEquipVisitTerc = 1,
            )
            val retrofitModelList = listOf(retrofitModelOutput)
            val entityList = listOf(entity)
            val token = "123456"
            val number = 16997417840L
            whenever(
                movEquipVisitTercRetrofitDatasource.send(
                    token = token,
                    list = retrofitModelList
                )
            ).thenReturn(
                Result.success(
                    listOf(retrofitModelInput)
                )
            )
            val repository = getRepository()
            val result = repository.send(
                list = entityList,
                number = number,
                token = token
            )
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 1)
            val entityRet = list[0]
            assertEquals(entityRet.idMovEquipVisitTerc, 1)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRoomDatasource setSent`() =
        runTest {
            val movEquipVisitTerc =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setSent",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setSent(
                listOf(
                    movEquipVisitTerc
                )
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setSent"
            )
        }

    @Test
    fun `Check return failure with 2 elements if have error in MovEquipVisitTercRoomDatasource setSent`() =
        runTest {
            val movEquipVisitTerc1 =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                )
            val movEquipVisitTerc2 =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 2,
                )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(2)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.setSent",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.setSent(
                listOf(
                    movEquipVisitTerc1,
                    movEquipVisitTerc2
                )
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.setSent"
            )
        }

    @Test
    fun `Check return true with 2 elements if MovEquipVisitTercRoomDatasource setSent execute successfully`() =
        runTest {
            val movEquipVisitTerc1 =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1,
                )
            val movEquipVisitTerc2 =
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 2,
                )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRoomDatasource.setSent(2)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.setSent(
                listOf(
                    movEquipVisitTerc1,
                    movEquipVisitTerc2
                )
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercRoomDatasource get`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                    )
                )
            )
            val repository = getRepository()
            val result = repository.delete(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.get"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipProprioRoomDatasource delete`() =
        runTest {
            val model = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
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
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(model)
            )
            whenever(
                movEquipVisitTercRoomDatasource.delete(model)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.delete",
                    )
                )
            )
            val repository = getRepository()
            val result = repository.delete(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.delete"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercRepository delete execute successfully`() =
        runTest {
            val model = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
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
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(model)
            )
            whenever(
                movEquipVisitTercRoomDatasource.delete(model)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.delete(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have errors in MovEquipVisitTercRoomDatasource listSent`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.listSent()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.listSent",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listSent()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasource.listSent"
            )
        }

    @Test
    fun `Check return list if MovEquipVisitTercRepository listSent execute successfully`() =
        runTest {
            val roomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "TESTE",
                placaMovEquipVisitTerc = "TESTE",
                destinoMovEquipVisitTerc = "TESTE",
                observMovEquipVisitTerc = "TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SENT,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            whenever(
                movEquipVisitTercRoomDatasource.listSent()
            ).thenReturn(
                Result.success(
                    listOf(
                        roomModel
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listSent()
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 1)
            val model = list[0]
            assertEquals(model.idMovEquipVisitTerc, 1)
            assertEquals(model.idVisitTercMovEquipVisitTerc, 1000)
            assertEquals(model.statusSendMovEquipVisitTerc, StatusSend.SENT)
        }

    @Test
    fun `Check return failure if have errors in MovEquipVisitTercRoomDatasource checkOpen`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkOpen()
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
    fun `CheckOpen - Check return false if not have MovEquipVisitTerc to open`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(false)
            )
            val repository = getRepository()
            val result = repository.checkOpen()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun `CheckSend - Check return true if have MovEquipVisitTerc to open`() =
        runTest {
            whenever(
                movEquipVisitTercRoomDatasource.checkOpen()
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.checkOpen()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

}