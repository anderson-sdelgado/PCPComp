package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioPassagRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class MovEquipProprioPassagRepositoryImplTest {

    @Test
    fun `Check failure Datasource in MovEquipProprioPassagRepository clear`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.clear()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.clear",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.clear()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioPassagSharedPreferencesDatasource.clear"
        )
    }

    @Test
    fun `Check true if have success in MovEquipProprioPassagRepository clear`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.clear()).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.clear()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check failure Datasource in MovEquipProprioPassagRepository list`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.list()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.list",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.list()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioPassagSharedPreferencesDatasource.list"
        )
    }

    @Test
    fun `Check return list if have success in MovEquipProprioPassagRepository list`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.list()).thenReturn(
            Result.success(
                listOf(19759)
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.list()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!.size, 1)
        assertEquals(result.getOrNull()!![0], 19759)
    }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioPassagRepository delete`() =
        runTest {
            val movEquipProprioPassagSharedPreferencesDatasource =
                mock<MovEquipProprioPassagSharedPreferencesDatasource>()
            val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
            whenever(movEquipProprioPassagSharedPreferencesDatasource.delete(19759)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioPassagSharedPreferencesDatasource.delete",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioPassagRepositoryImpl(
                movEquipProprioPassagSharedPreferencesDatasource,
                movEquipProprioPassagRoomDatasource
            )
            val result = repository.delete(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioPassagSharedPreferencesDatasource.delete"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository delete execute success`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.delete(19759)).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.delete(
            matricColab = 19759,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return failure if have error in MovEquipProprioPassagRepository add`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.add(19759)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.add",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.add(
            matricColab = 19759,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioPassagSharedPreferencesDatasource.add"
        )
    }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository add execute success`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.add(19759)).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.add(
            matricColab = 19759,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return failure if have error in MovEquipProprioPassagRepository list`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.list()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.list",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.save(1)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioPassagSharedPreferencesDatasource.list"
        )
    }

    @Test
    fun `Check return failure if have error in MovEquipProprioPassagRoomDatasource addAll`() = runTest {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val movEquipProprioPassagRoomModelList = list.map {
            MovEquipProprioPassagRoomModel(
                idMovEquipProprio = 1,
                matricColab = it
            )
        }
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.list()).thenReturn(
            Result.success(list)
        )
        whenever(movEquipProprioPassagRoomDatasource.addAll(movEquipProprioPassagRoomModelList)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagRoomDatasource.addAll",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.save(1)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioPassagRoomDatasource.addAll"
        )
    }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository save execute successfully`() = runTest {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val movEquipProprioPassagRoomModelList = list.map {
            MovEquipProprioPassagRoomModel(
                idMovEquipProprio = 1,
                matricColab = it
            )
        }
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        val movEquipProprioPassagRoomDatasource = mock<MovEquipProprioPassagRoomDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.list()).thenReturn(
            Result.success(list)
        )
        whenever(movEquipProprioPassagRoomDatasource.addAll(movEquipProprioPassagRoomModelList)).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource,
            movEquipProprioPassagRoomDatasource
        )
        val result = repository.save(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}