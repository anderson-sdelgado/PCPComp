package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.FluxoRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.FluxoRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.FluxoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class FluxoRepositoryImplTest {

    private val fluxoRoomDatasource = mock<FluxoRoomDatasource>()
    private val fluxoRetrofitDatasource = mock<FluxoRetrofitDatasource>()
    private fun getRepository() = FluxoRepositoryImpl(
        fluxoRoomDatasource,
        fluxoRetrofitDatasource
    )

    @Test
    fun `AddAll - Check return failure if have error in FluxoRoomDatasource addAll`() =
        runTest {
            val entityList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            val roomModelList = listOf(
                FluxoRoomModel(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            whenever(
                fluxoRoomDatasource.addAll(
                    roomModelList
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "FluxoRoomDatasource.addAll",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.addAll(
                entityList
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> FluxoRoomDatasource.addAll"
            )
        }

    @Test
    fun `AddAll - Check return true if FluxoRepositoryImpl addAll execute successfully`() =
        runTest {
            val entityList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            val roomModelList = listOf(
                FluxoRoomModel(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            whenever(
                fluxoRoomDatasource.addAll(
                    roomModelList
                )
            ).thenReturn(
                Result.success(
                    true
                )
            )
            val repository = getRepository()
            val result = repository.addAll(
                entityList
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    true
                )
            )
        }

    @Test
    fun `Delete All - Check return failure if have error`() =
        runTest {
            whenever(
                fluxoRoomDatasource.deleteAll()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "FluxoRoomDatasource.deleteAll",
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
                "Failure Datasource -> FluxoRoomDatasource.deleteAll"
            )
        }

    @Test
    fun `DeleteAll - Check return true if FluxoRepositoryImpl deleteAll execute successfully`() =
        runTest {
            whenever(
                fluxoRoomDatasource.deleteAll()
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
                fluxoRetrofitDatasource.recoverAll(
                    "token"
                )
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "FluxoRetrofitDatasource.recoverAll",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.recoverAll(
                "token"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> FluxoRetrofitDatasource.recoverAll"
            )
        }

    @Test
    fun `RecoverAll - Check return true if FluxoRepositoryImpl recoverAll execute successfully`() =
        runTest {
            val entityList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            val retrofitModelList = listOf(
                FluxoRetrofitModel(
                    idFluxo = 1,
                    descrFluxo = "teste"
                )
            )
            whenever(
                fluxoRetrofitDatasource.recoverAll(
                    "token"
                )
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val repository = getRepository()
            val result = repository.recoverAll(
                "token"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    entityList
                )
            )
        }

}