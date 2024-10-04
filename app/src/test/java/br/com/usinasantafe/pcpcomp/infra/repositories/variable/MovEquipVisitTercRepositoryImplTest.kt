package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcpcomp.utils.FlowApp
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

    @Test
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource listOpen`() = runTest {
        val movEquipVisitTercSharedPreferencesDatasource =
            mock<MovEquipVisitTercSharedPreferencesDatasource>()
        val movEquipVisitTercRoomDatasource =
            mock<MovEquipVisitTercRoomDatasource>()
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
    fun `Check return list if have success in MovEquipVisitTercRoomDatasource listOpen`() =
        runTest {
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource setClose`() = runTest {
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
        val movEquipVisitTercSharedPreferencesDatasource =
            mock<MovEquipVisitTercSharedPreferencesDatasource>()
        val movEquipVisitTercRoomDatasource =
            mock<MovEquipVisitTercRoomDatasource>()
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
    fun `Check return true if have success in MovEquipVisitTercRoomDatasource setClose`() =
        runTest {
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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

    @Test
    fun `Get - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
        val movEquipVisitTercSharedPreferencesDatasource =
            mock<MovEquipVisitTercSharedPreferencesDatasource>()
        val movEquipVisitTercRoomDatasource =
            mock<MovEquipVisitTercRoomDatasource>()
        whenever(
            movEquipVisitTercRoomDatasource.get(1)
        ).thenReturn(
            Result.success(
                movEquipVisitTercRoomModel
            )
        )
        val repository = MovEquipVisitTercRepositoryImpl(
            movEquipVisitTercSharedPreferencesDatasource,
            movEquipVisitTercRoomDatasource
        )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                nroMatricVigiaMovEquipVisitTerc = 1000,
                idLocalMovEquipVisitTerc = 1000,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.getDestino(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "TESTE DESTINO")
        }

    @Test
    fun `GetIdVisitTerc - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                nroMatricVigiaMovEquipVisitTerc = 1000,
                idLocalMovEquipVisitTerc = 1000,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.getIdVisitTerc(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, 1000)
        }

    @Test
    fun `GetObserv - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                nroMatricVigiaMovEquipVisitTerc = 1000,
                idLocalMovEquipVisitTerc = 1000,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.getObserv(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull(), "TESTE OBSERV")
        }

    @Test
    fun `GetPlaca - Check return failure if have error in MovEquipVisitTercRoomDatasource Get`() =
        runTest {
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                nroMatricVigiaMovEquipVisitTerc = 1000,
                idLocalMovEquipVisitTerc = 1000,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.getPlaca(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "TESTE PLACA")
        }

    @Test
    fun `GetTypeVisitTerc - ADD - Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource Get`() =
        runTest {
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    MovEquipVisitTercSharedPreferencesModel(
                        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                    )
                )
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercRoomDatasource.get",
                    )
                )
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(
                    MovEquipVisitTercRoomModel(
                        idMovEquipVisitTerc = 1,
                        nroMatricVigiaMovEquipVisitTerc = 1000,
                        idLocalMovEquipVisitTerc = 1000,
                        tipoMovEquipVisitTerc = TypeMov.INPUT,
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                nroMatricVigiaMovEquipVisitTerc = 1000,
                idLocalMovEquipVisitTerc = 1000,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.get(1)
            ).thenReturn(
                Result.success(movEquipVisitTercRoomModel)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.getVeiculo(1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "TESTE VEICULO")
        }

    @Test
    fun `Check failure failure if have error in MovEquipVisitTercRoomDatasource listInputOpen`() = runTest {
        val movEquipVisitTercSharedPreferencesDatasource =
            mock<MovEquipVisitTercSharedPreferencesDatasource>()
        val movEquipVisitTercRoomDatasource =
            mock<MovEquipVisitTercRoomDatasource>()
        whenever(movEquipVisitTercRoomDatasource.listInside()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasource.listInputOpen",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipVisitTercRepositoryImpl(
            movEquipVisitTercSharedPreferencesDatasource,
            movEquipVisitTercRoomDatasource
        )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(movEquipVisitTercRoomDatasource.listInside()).thenReturn(
                Result.success(listOf())
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.listInside()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!.isEmpty())
        }

    @Test
    fun `Check return list if listOpenInput execute successfully`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 1000,
                idLocalMovEquipVisitTerc = 1000,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(movEquipVisitTercRoomDatasource.listInside()).thenReturn(
                Result.success(listOf(movEquipVisitTercRoomModel))
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.sharedPreferencesModelToEntity()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.sharedPreferencesModelToEntity()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.sharedPreferencesModelToEntity()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = Date(1723213270250),
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
            )
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelRoom =
                modelSharedPreferences.sharedPreferencesModelToEntity()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setDestino("DESTINO TESTE")
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.setDestino(
                    destino = "DESTINO TESTE",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(1)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.setIdVisitTerc(
                    idVisitTerc = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setObserv("Teste Observ")
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.setObserv(
                    observ = "Teste Observ",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setPlaca("Teste Placa")
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.setPlaca(
                    placa = "Teste Placa",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.setTipoVisitTerc(TypeVisitTerc.TERCEIRO)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercSharedPreferencesDatasource setVeiculo - FlowApp ADD`() =
        runTest {
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.setVeiculo("Teste Veiculo")
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercRoomDatasource.setVeiculo(
                    veiculo = "Teste Veiculo",
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                nroMatricVigiaMovEquipVisitTerc = 1000,
                idLocalMovEquipVisitTerc = 1000,
                tipoMovEquipVisitTerc = TypeMov.OUTPUT,
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
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
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
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
                nroMatricVigiaMovEquipVisitTerc = 1000,
                idLocalMovEquipVisitTerc = 1000,
                tipoMovEquipVisitTerc = TypeMov.OUTPUT,
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(
                movEquipVisitTercSharedPreferencesDatasource.start(
                    movEquipVisitTerc.entityToSharedPreferencesModel()
                )
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.start(movEquipVisitTerc)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure Datasource in MovEquipVisitTercRoomDatasource setOutside`() = runTest {
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
        val movEquipVisitTercSharedPreferencesDatasource =
            mock<MovEquipVisitTercSharedPreferencesDatasource>()
        val movEquipVisitTercRoomDatasource =
            mock<MovEquipVisitTercRoomDatasource>()
        whenever(
            movEquipVisitTercRoomDatasource.setOutside(movEquipVisitTercRoomModel)
        ).thenReturn(
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
        val result = repository.setOutside(movEquipVisitTerc)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRoomDatasource.setClose"
        )
    }

    @Test
    fun `Check return true if have success in MovEquipVisitTercRoomDatasource setOutside`() =
        runTest {
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
            val movEquipVisitTercSharedPreferencesDatasource =
                mock<MovEquipVisitTercSharedPreferencesDatasource>()
            val movEquipVisitTercRoomDatasource =
                mock<MovEquipVisitTercRoomDatasource>()
            whenever(movEquipVisitTercRoomDatasource.setOutside(movEquipVisitTercRoomModel)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercRepositoryImpl(
                movEquipVisitTercSharedPreferencesDatasource,
                movEquipVisitTercRoomDatasource
            )
            val result = repository.setOutside(movEquipVisitTerc)
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!, true)
        }

}