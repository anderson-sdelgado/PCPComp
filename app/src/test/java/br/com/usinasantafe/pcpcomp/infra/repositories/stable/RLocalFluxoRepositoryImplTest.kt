package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.RLocalFluxoRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.RLocalFluxoRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.RLocalFluxoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class RLocalFluxoRepositoryImplTest {

    private val rLocalFluxoRoomDatasource = mock<RLocalFluxoRoomDatasource>()
    private val rLocalFluxoRetrofitDatasource = mock<RLocalFluxoRetrofitDatasource>()
    private fun getRepository() = RLocalFluxoRepositoryImpl(
        rLocalFluxoRoomDatasource,
        rLocalFluxoRetrofitDatasource
    )

    @Test
    fun `AddAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                RLocalFluxoRoomModel(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            whenever(
                rLocalFluxoRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "RLocalFluxoRepositoryImpl.addAll",
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
                "Failure Repository -> RLocalFluxoRepositoryImpl.addAll"
            )
        }

    @Test
    fun `AddAll - Check return true if RLocalFluxoRepositoryImpl addAll execute successfully`() =
        runTest {
            val roomModelList = listOf(
                RLocalFluxoRoomModel(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            whenever(
                rLocalFluxoRoomDatasource.addAll(roomModelList)
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
                rLocalFluxoRoomDatasource.deleteAll()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "RLocalFluxoRepositoryImpl.deleteAll",
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
                "Failure Repository -> RLocalFluxoRepositoryImpl.deleteAll"
            )
        }

    @Test
    fun `DeleteAll - Check return true if RLocalFluxoRepositoryImpl deleteAll execute successfully`() =
        runTest {
            whenever(
                rLocalFluxoRoomDatasource.deleteAll()
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
    fun `RecoverAll - Check return failure if have error in RLocalFluxoRetrofitDatasource recoverAll`() =
        runTest {
            whenever(
                rLocalFluxoRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "RLocalFluxoRepositoryImpl.recoverAll",
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
                "Failure Repository -> RLocalFluxoRepositoryImpl.recoverAll"
            )
        }

    @Test
    fun `RecoverAll - Check return true if RLocalFluxoRepositoryImpl RecoverAll execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                RLocalFluxoRetrofitModel(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            whenever(
                rLocalFluxoRetrofitDatasource.recoverAll("token")
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

}