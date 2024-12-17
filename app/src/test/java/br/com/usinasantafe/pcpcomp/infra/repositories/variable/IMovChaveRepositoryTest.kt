package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class IMovChaveRepositoryTest {

    private val movChaveRoomDatasource = mock<MovChaveRoomDatasource>()
    private val movChaveSharedPreferencesDatasource = mock<MovChaveSharedPreferencesDatasource>()
    private val repository = IMovChaveRepository(
        movChaveRoomDatasource = movChaveRoomDatasource,
        movChaveSharedPreferencesDatasource = movChaveSharedPreferencesDatasource
    )

    @Test
    fun `listInside - Check return failure if have error in MovChaveRoomDatasource listInside`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listInside()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "IMovChaveRepository.listInside",
                        cause = Exception()
                    )
                )
            )
            val result = repository.listInside()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRepository.listInside"
            )
        }

    @Test
    fun `listInside - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listInside()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChaveRoomModel(
                            idMovChave = 1,
                            matricVigiaMovChave = 19035,
                            idLocalMovChave = 1,
                            dthrMovChave = Date().time,
                            tipoMovChave = TypeMovKey.REMOVE,
                            idChaveMovChave = 1,
                            matricColabMovChave = 19759,
                            observMovChave = "OBSERV",
                            statusMovChave = StatusData.OPEN,
                            statusSendMovChave = StatusSend.SEND,
                            statusForeignerMovChave = StatusForeigner.INSIDE
                        )
                    )
                )
            )
            val result = repository.listInside()
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.idMovChave,
                1
            )
            assertEquals(
                entity.matricVigiaMovChave,
                19035
            )
            assertEquals(
                entity.idLocalMovChave,
                1
            )
        }

    @Test
    fun `setIdChave - Check return failure if have error in MovChaveSharePreferenceDatasource setIdChave - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setIdChave(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "IMovChaveRepository.setIdChave",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setIdChave(
                idChave = 1,
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRepository.setIdChave"
            )
        }

    @Test
    fun `setIdChave - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setIdChave(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdChave(
                idChave = 1,
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in MovChaveSharePreferenceDatasource setMatricColab - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setMatricColab(19759)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveSharePreferenceDatasource.setMatricColab",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveSharePreferenceDatasource.setMatricColab"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setMatricColab(19759)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `setObserv - Check return failure if have error in MovChaveSharePreferenceDatasource setObserv - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setObserv("OBSERV")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveSharePreferenceDatasource.setObserv",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setObserv(
                observ = "OBSERV",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveSharePreferenceDatasource.setObserv"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully - ADD`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.setObserv("OBSERV")
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = "OBSERV",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `save - Check return failure if have error in MovChaveSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveSharePreferenceDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveSharePreferenceDatasource.get"
            )
        }

    @Test
    fun `save - Check return failure if have error in MovChaveRoomDatasource save`() =
        runTest {
            val sharedPreferencesModel = MovChaveSharedPreferencesModel(
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
            )
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveRoomDatasource.save(
                    sharedPreferencesModel
                        .entityToSharedPreferencesModel()
                        .entityToRoomModel(
                            matricVigia = 19035,
                            idLocal = 1
                        )
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveRoomDatasource.save",
                        cause = Exception()
                    )
                )
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveRoomDatasource.save"
            )
        }

    @Test
    fun `save - Check return failure if MovChaveRoomDatasource save execute successfully but return zero`() =
        runTest {
            val sharedPreferencesModel = MovChaveSharedPreferencesModel(
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
            )
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveRoomDatasource.save(
                    sharedPreferencesModel
                        .entityToSharedPreferencesModel()
                        .entityToRoomModel(
                            matricVigia = 19035,
                            idLocal = 1
                        )
                )
            ).thenReturn(
                Result.success(0)
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> IMovChaveRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.message,
                "Id is 0"
            )
        }

    @Test
    fun `save - Check return failure if have error in MovChaveSharedPreferencesDatasource clear`() =
        runTest {
            val sharedPreferencesModel = MovChaveSharedPreferencesModel(
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
            )
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveRoomDatasource.save(
                    sharedPreferencesModel
                        .entityToSharedPreferencesModel()
                        .entityToRoomModel(
                            matricVigia = 19035,
                            idLocal = 1
                        )
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movChaveSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveSharePreferenceDatasource.clear",
                        cause = Exception()
                    )
                )
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveSharePreferenceDatasource.clear"
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            val sharedPreferencesModel = MovChaveSharedPreferencesModel(
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
            )
            whenever(
                movChaveSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                movChaveRoomDatasource.save(
                    sharedPreferencesModel
                        .entityToSharedPreferencesModel()
                        .entityToRoomModel(
                            matricVigia = 19035,
                            idLocal = 1
                        )
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movChaveSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(
                matricVigia = 19035,
                idLocal = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `get - Check return failure if have error in MovChaveRoomDatasource get`() =
        runTest {
            whenever(
                movChaveRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val result = repository.get(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveRoomDatasource.get"
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.get(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity,
                roomModel.roomModelToEntity()
            )
            assertEquals(
                entity.idMovChave,
                1
            )
            assertEquals(
                entity.matricVigiaMovChave,
                19035
            )
        }

    @Test
    fun `start - Check return failure if have error in MovChaveSharedPreferencesDatasource start`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.RECEIPT,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveSharedPreferencesDatasource.start(entity.entityToSharedPreferencesModel())
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveSharePreferenceDatasource.start",
                        cause = Exception()
                    )
                )
            )
            val result = repository.start(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveSharePreferenceDatasource.start"
            )
        }

    @Test
    fun `start - Check return correct if function execute successfully`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.RECEIPT,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveSharedPreferencesDatasource.start(entity.entityToSharedPreferencesModel())
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.start(entity)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `setOutside - Check return failure if have error in MovChaveRoomDatasource setOutside`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setOutside(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveRoomDatasource.setStatusOutside",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setOutside(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveRoomDatasource.setStatusOutside"
            )
        }

    @Test
    fun `setOutside - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setOutside(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setOutside(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `listOpen - Check return failure if have error in MovChaveRoomDatasource listOpen`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listOpen()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "IMovChaveRepository.listOpen",
                        cause = Exception()
                    )
                )
            )
            val result = repository.listOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRepository.listOpen"
            )
        }

    @Test
    fun `listOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChaveRoomModel(
                            idMovChave = 1,
                            matricVigiaMovChave = 19035,
                            idLocalMovChave = 1,
                            dthrMovChave = Date().time,
                            tipoMovChave = TypeMovKey.REMOVE,
                            idChaveMovChave = 1,
                            matricColabMovChave = 19759,
                            observMovChave = "OBSERV",
                            statusMovChave = StatusData.OPEN,
                            statusSendMovChave = StatusSend.SEND,
                            statusForeignerMovChave = StatusForeigner.INSIDE
                        )
                    )
                )
            )
            val result = repository.listOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.idMovChave,
                1
            )
            assertEquals(
                entity.matricVigiaMovChave,
                19035
            )
            assertEquals(
                entity.idLocalMovChave,
                1
            )
        }

    @Test
    fun `setClose - Check return failure if have error in MovChaveRoomDatasource get`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setClose(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveRoomDatasource.setClose",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setClose(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveRoomDatasource.setClose"
            )
        }

    @Test
    fun `setClose - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setClose(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `setIdChave - Check return failure if have error in MovChaveSharePreferenceDatasource setIdChave - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setIdChave(
                    idChave = 1,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveRoomDatasource.setIdChave",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setIdChave(
                idChave = 1,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveRoomDatasource.setIdChave"
            )
        }

    @Test
    fun `setIdChave - Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setIdChave(
                    idChave = 1,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdChave(
                idChave = 1,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `getMatricColab - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getMatricColab(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                19759
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in MovChaveSharePreferenceDatasource setMatricColab - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setMatricColab(
                    matric = 19759,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveRoomDatasource.setMatricColab",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveRoomDatasource.setMatricColab"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setMatricColab(
                    matric = 19759,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setMatricColab(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `setObserv - Check return failure if have error in MovChaveSharePreferenceDatasource setObserv - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setObserv("OBSERV", 1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovChaveRoomDatasource.setObserv",
                        cause = Exception()
                    )
                )
            )
            val result = repository.setObserv(
                observ = "OBSERV",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveRoomDatasource.setObserv"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully - CHANGE`() =
        runTest {
            whenever(
                movChaveRoomDatasource.setObserv("OBSERV", 1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObserv(
                observ = "OBSERV",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `getObserv - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                matricVigiaMovChave = 19035,
                idLocalMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                matricColabMovChave = 19759,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            whenever(
                movChaveRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val result = repository.getObserv(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "OBSERV"
            )
        }

}