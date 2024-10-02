package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercPassagRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class MovEquipVisitTercPassagRepositoryImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagSharedPreferencesDatasource add - FlowApp ADD`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagSharedPreferencesDatasource.add",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.add(
                idVisitTerc = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagSharedPreferencesDatasource.add"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository add - FlowApp CHANGE`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagRoomDatasource.add(10, 1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRoomDatasource.add",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.add(
                idVisitTerc = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRoomDatasource.add"
            )
        }

    @Test
    fun `Check return true if Add execute successfully - FlowApp ADD`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.add(
                idVisitTerc = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if Add execute successfully - FlowApp CHANGE`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagRoomDatasource.add(10, 1)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.add(
                idVisitTerc = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository clear`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagSharedPreferencesDatasource.clear",
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.clear()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagSharedPreferencesDatasource.clear"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercPassagRepositoryImpl Clear execute successfully`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.clear()
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.clear()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagSharedPreferencesDatasource delete - FlowApp ADD`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.delete(10)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagSharedPreferencesDatasource.delete",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.delete(
                idVisitTerc = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagSharedPreferencesDatasource.delete"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository delete - FlowApp CHANGE`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagRoomDatasource.delete(10, 1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRoomDatasource.delete",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.delete(
                idVisitTerc = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRoomDatasource.delete"
            )
        }

    @Test
    fun `Check return true if Delete execute successfully - FlowApp ADD`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.delete(10)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.delete(
                idVisitTerc = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if Delete execute successfully - FlowApp CHANGE`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagRoomDatasource.delete(10, 1)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.delete(
                idVisitTerc = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagSharedPreferencesDatasource list - FlowApp ADD`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercPassagSharedPreferencesDatasource.list",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.list(
                FlowApp.ADD,
                0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercPassagSharedPreferencesDatasource.list"
            )
        }

    @Test
    fun `Check failure Datasource in MovEquipVisitTercPassagRoomDatasource list - FlowApp CHANGE`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagRoomDatasource.list(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercPassagRoomDatasource.list",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.list(
                FlowApp.CHANGE,
                1
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercPassagRoomDatasource.list"
            )
        }

    @Test
    fun `Check return list if have success in MovEquipVisitTercPassagRepository list - FlowApp ADD`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(
                    listOf(10)
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.list(
                FlowApp.ADD,
                0
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!.size, 1)
            assertEquals(result.getOrNull()!![0].idVisitTerc, 10)
        }

    @Test
    fun `Check return list if have success in MovEquipVisitTercPassagRepository list - FlowApp CHANGE`() =
        runTest {
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagRoomDatasource.list(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassagRoomModel(
                            idMovEquipVisitTercPassag = 1,
                            idMovEquipVisitTerc = 1,
                            idVisitTerc = 10
                        )
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.list(
                FlowApp.CHANGE,
                1
            )
            assertEquals(result.isSuccess, true)
            assertEquals(result.getOrNull()!!.size, 1)
            assertEquals(result.getOrNull()!![0].idVisitTerc, 10)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository list`() = runTest {
        val movEquipVisitTercPassagSharedPreferencesDatasource =
            mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
        val movEquipVisitTercPassagRoomDatasource =
            mock<MovEquipVisitTercPassagRoomDatasource>()
        whenever(
            movEquipVisitTercPassagSharedPreferencesDatasource.list()
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercPassagSharedPreferencesDatasource.list",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipVisitTercPassagRepositoryImpl(
            movEquipVisitTercPassagSharedPreferencesDatasource,
            movEquipVisitTercPassagRoomDatasource
        )
        val result = repository.save(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercPassagSharedPreferencesDatasource.list"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRoomDatasource AddAll`() =
        runTest {
            val list = listOf(10, 20, 30)
            val modelList = list.map {
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = it
                )
            }
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                movEquipVisitTercPassagRoomDatasource.addAll(modelList)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipVisitTercPassagRoomDatasource.addAll",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.save(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercPassagRoomDatasource.addAll"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercPassagRepositoryImpl Save execute successfully`() =
        runTest {
            val list = listOf(10, 20, 30)
            val modelList = list.map {
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = it
                )
            }
            val movEquipVisitTercPassagSharedPreferencesDatasource =
                mock<MovEquipVisitTercPassagSharedPreferencesDatasource>()
            val movEquipVisitTercPassagRoomDatasource =
                mock<MovEquipVisitTercPassagRoomDatasource>()
            whenever(
                movEquipVisitTercPassagSharedPreferencesDatasource.list()
            ).thenReturn(
                Result.success(list)
            )
            whenever(
                movEquipVisitTercPassagRoomDatasource.addAll(modelList)
            ).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipVisitTercPassagRepositoryImpl(
                movEquipVisitTercPassagSharedPreferencesDatasource,
                movEquipVisitTercPassagRoomDatasource
            )
            val result = repository.save(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}