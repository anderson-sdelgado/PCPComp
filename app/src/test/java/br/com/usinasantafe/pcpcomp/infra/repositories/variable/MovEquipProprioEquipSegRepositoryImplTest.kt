package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class MovEquipProprioEquipSegRepositoryImplTest {

    @Test
    fun `Check failure Datasource in MovEquipProprioSegRepository clear`() = runTest {
        val movEquipProprioEquipSegSharedPreferencesDatasource =
            mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
        whenever(movEquipProprioEquipSegSharedPreferencesDatasource.clear()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioSegSharedPreferencesDatasource.clear",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioEquipSegRepositoryImpl(
            movEquipProprioEquipSegSharedPreferencesDatasource
        )
        val result = repository.clear()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioSegSharedPreferencesDatasource.clear"
        )
    }

    @Test
    fun `Check true if have success in MovEquipProprioSegRepository clear`() = runTest {
        val movEquipProprioEquipSegSharedPreferencesDatasource =
            mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
        whenever(movEquipProprioEquipSegSharedPreferencesDatasource.clear()).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioEquipSegRepositoryImpl(
            movEquipProprioEquipSegSharedPreferencesDatasource
        )
        val result = repository.clear()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check failure Datasource in MovEquipProprioEquipSegRepository list`() = runTest {
        val movEquipProprioEquipSegSharedPreferencesDatasource =
            mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
        whenever(movEquipProprioEquipSegSharedPreferencesDatasource.list()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegSharedPreferencesDatasource.list",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioEquipSegRepositoryImpl(
            movEquipProprioEquipSegSharedPreferencesDatasource
        )
        val result = repository.list()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioEquipSegSharedPreferencesDatasource.list"
        )
    }

    @Test
    fun `Check return list if have success in MovEquipProprioEquipSegRepository list`() = runTest {
        val movEquipProprioEquipSegSharedPreferencesDatasource =
            mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
        whenever(movEquipProprioEquipSegSharedPreferencesDatasource.list()).thenReturn(
            Result.success(
                listOf(10)
            )
        )
        val repository = MovEquipProprioEquipSegRepositoryImpl(
            movEquipProprioEquipSegSharedPreferencesDatasource
        )
        val result = repository.list()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!.size, 1)
        assertEquals(result.getOrNull()!![0], 10)
    }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioEquipSegRepository delete`() =
        runTest {
            val movEquipProprioEquipSegSharedPreferencesDatasource =
                mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
            whenever(movEquipProprioEquipSegSharedPreferencesDatasource.delete(10)).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "MovEquipProprioEquipSegSharedPreferencesDatasource.delete",
                        cause = Exception()
                    )
                )
            )
            val repository = MovEquipProprioEquipSegRepositoryImpl(
                movEquipProprioEquipSegSharedPreferencesDatasource
            )
            val result = repository.delete(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipProprioEquipSegSharedPreferencesDatasource.delete"
            )
        }

    @Test
    fun `Check return true if MovEquipProprioEquipSegRepository delete execute success`() =
        runTest {
            val movEquipProprioEquipSegSharedPreferencesDatasource =
                mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
            whenever(movEquipProprioEquipSegSharedPreferencesDatasource.delete(10)).thenReturn(
                Result.success(true)
            )
            val repository = MovEquipProprioEquipSegRepositoryImpl(
                movEquipProprioEquipSegSharedPreferencesDatasource
            )
            val result = repository.delete(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipProprioEquipSegRepository add`() = runTest {
        val movEquipProprioEquipSegSharedPreferencesDatasource =
            mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
        whenever(movEquipProprioEquipSegSharedPreferencesDatasource.add(10)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegSharedPreferencesDatasource.add",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioEquipSegRepositoryImpl(
            movEquipProprioEquipSegSharedPreferencesDatasource
        )
        val result = repository.add(
            idEquip = 10,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioEquipSegSharedPreferencesDatasource.add"
        )
    }

    @Test
    fun `Check return true if MovEquipProprioEquipSegRepository add execute success`() = runTest {
        val movEquipProprioEquipSegSharedPreferencesDatasource =
            mock<MovEquipProprioEquipSegSharedPreferencesDatasource>()
        whenever(movEquipProprioEquipSegSharedPreferencesDatasource.add(10)).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioEquipSegRepositoryImpl(
            movEquipProprioEquipSegSharedPreferencesDatasource
        )
        val result = repository.add(
            idEquip = 10,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

}