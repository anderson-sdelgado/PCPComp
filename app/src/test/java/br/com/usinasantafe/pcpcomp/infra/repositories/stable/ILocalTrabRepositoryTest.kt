package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.LocalTrabRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalTrabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.ChaveRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.LocalTrabRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalTrabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ILocalTrabRepositoryTest {

    private val localTrabRoomDatasource = mock<LocalTrabRoomDatasource>()
    private val localTrabRetrofitDatasource = mock<LocalTrabRetrofitDatasource>()
    private fun getRepository() = ILocalTrabRepository(
        localTrabRoomDatasource = localTrabRoomDatasource,
        localTrabRetrofitDatasource = localTrabRetrofitDatasource
    )

    @Test
    fun `AddAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                LocalTrabRoomModel(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            val entityList = listOf(
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            whenever(
                localTrabRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ILocalTrabRepository.addAll",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ILocalTrabRepository.addAll"
            )
        }

    @Test
    fun `AddAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                LocalTrabRoomModel(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            val entityList = listOf(
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            whenever(
                localTrabRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.addAll(entityList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
        }

    @Test
    fun `DeleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                localTrabRoomDatasource.deleteAll()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ILocalTrabRepository.deleteAll",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ILocalTrabRepository.deleteAll"
            )
        }

    @Test
    fun `DeleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                localTrabRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
        }

    @Test
    fun `RecoverAll - Check return failure if have error`() =
        runTest {
            whenever(
                localTrabRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ILocalTrabRepository.recoverAll",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.recoverAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ILocalTrabRepository.recoverAll"
            )
        }

    @Test
    fun `RecoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                LocalTrabRetrofitModel(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            val entityList = listOf(
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI"
                )
            )
            whenever(
                localTrabRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val repository = getRepository()
            val result = repository.recoverAll("token")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(entityList)
            )
        }

    @Test
    fun `Check return failure if have error in ILocalTrabRepository getDescr`() =
        runTest {
            whenever(
                localTrabRoomDatasource.getDescr(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ILocalTrabRepository.getDescr",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getDescr(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ILocalTrabRepository.getDescr"
            )
        }

    @Test
    fun `Check return description if ILocalTrabRepository getDescr execute successfully`() =
        runTest {
            whenever(
                localTrabRoomDatasource.getDescr(1)
            ).thenReturn(
                Result.success("TI")
            )
            val repository = getRepository()
            val result = repository.getDescr(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TI"
            )
        }

}