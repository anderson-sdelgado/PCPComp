package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
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
    private fun getRepository() = IMovChaveRepository(
        movChaveRoomDatasource = movChaveRoomDatasource,
        movChaveSharedPreferencesDatasource = movChaveSharedPreferencesDatasource
    )

    @Test
    fun `listRemove - Check return failure if have error in MovChaveRoomDatasource listRemove`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listRemove()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "IMovChaveRepository.listRemove",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listRemove()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveRepository.listRemove"
            )
        }

    @Test
    fun `listRemove - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRoomDatasource.listRemove()
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
            val repository = getRepository()
            val result = repository.listRemove()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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
            val repository = getRepository()
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

}