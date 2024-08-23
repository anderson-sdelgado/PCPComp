package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
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
        whenever(movEquipProprioPassagSharedPreferencesDatasource.clear()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.clear",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource
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
        whenever(movEquipProprioPassagSharedPreferencesDatasource.clear()).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource
        )
        val result = repository.clear()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check failure Datasource in MovEquipProprioPassagRepository list`() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource =
            mock<MovEquipProprioPassagSharedPreferencesDatasource>()
        whenever(movEquipProprioPassagSharedPreferencesDatasource.list()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.list",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource
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
        whenever(movEquipProprioPassagSharedPreferencesDatasource.list()).thenReturn(
            Result.success(
                listOf(19759)
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource
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
            whenever(movEquipProprioPassagSharedPreferencesDatasource.delete(19759)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioPassagSharedPreferencesDatasource.delete",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioPassagRepositoryImpl(
                movEquipProprioPassagSharedPreferencesDatasource
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
        whenever(movEquipProprioPassagSharedPreferencesDatasource.delete(19759)).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource
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
        whenever(movEquipProprioPassagSharedPreferencesDatasource.add(19759)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.add",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource
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
        whenever(movEquipProprioPassagSharedPreferencesDatasource.add(19759)).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioPassagRepositoryImpl(
            movEquipProprioPassagSharedPreferencesDatasource
        )
        val result = repository.add(
            matricColab = 19759,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

}