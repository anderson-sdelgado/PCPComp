package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.ChaveRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ChaveRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.ChaveRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.RLocalFluxoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IChaveRepositoryTest {

    private val chaveRoomDatasource = mock<ChaveRoomDatasource>()
    private val chaveRetrofitDatasource = mock<ChaveRetrofitDatasource>()
    private fun getRepository() = IChaveRepository(
        chaveRetrofitDatasource = chaveRetrofitDatasource,
        chaveRoomDatasource = chaveRoomDatasource
    )

    @Test
    fun `AddAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ChaveRoomModel(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            val entityList = listOf(
                Chave(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            whenever(
                chaveRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "IChaveRepository.addAll",
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
                "Failure Repository -> IChaveRepository.addAll"
            )
        }

    @Test
    fun `AddAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ChaveRoomModel(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            val entityList = listOf(
                Chave(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            whenever(
                chaveRoomDatasource.addAll(roomModelList)
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
                chaveRoomDatasource.deleteAll()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "IChaveRepository.deleteAll",
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
                "Failure Repository -> IChaveRepository.deleteAll"
            )
        }

    @Test
    fun `DeleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                chaveRoomDatasource.deleteAll()
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
                chaveRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "IChaveRepository.recoverAll",
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
                "Failure Repository -> IChaveRepository.recoverAll"
            )
        }

    @Test
    fun `RecoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ChaveRetrofitModel(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            val entityList = listOf(
                Chave(
                    idChave = 1,
                    descrChave = "01 - TI",
                    idLocalTrab = 1
                )
            )
            whenever(
                chaveRetrofitDatasource.recoverAll("token")
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
    fun `Check return failure if have error in ChaveRoomDatasource getDescr`() =
        runTest {
            whenever(
                chaveRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "IChaveRepository.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.get(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> IChaveRepository.get"
            )
        }

    @Test
    fun `listAll - Check return failure if have error in ChaveRoomDatasource listAll`() =
        runTest {
            whenever(
                chaveRoomDatasource.listAll()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "IChaveRepository.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> IChaveRepository.get"
            )
        }

    @Test
    fun `listAll - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                chaveRoomDatasource.listAll()
            ).thenReturn(
                Result.success(
                    listOf(
                        ChaveRoomModel(
                            idChave = 1,
                            descrChave = "01 - TI",
                            idLocalTrab = 1
                        )
                    )
                )
            )
            val repository = getRepository()
            val result = repository.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idChave,
                1
            )
            assertEquals(
                entity.descrChave,
                "01 - TI"
            )
        }
}