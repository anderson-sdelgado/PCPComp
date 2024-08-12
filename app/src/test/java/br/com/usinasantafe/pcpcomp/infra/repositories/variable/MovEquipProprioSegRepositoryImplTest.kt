package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class MovEquipProprioSegRepositoryImplTest {

    @Test
    fun `Check failure Datasource in MovEquipProprioSegRepository clear`() = runTest {
        val movEquipProprioSegSharedPreferencesDatasource =
            mock<MovEquipProprioSegSharedPreferencesDatasource>()
        whenever(movEquipProprioSegSharedPreferencesDatasource.clear()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioSegSharedPreferencesDatasource.clear",
                    cause = Exception()
                )
            )
        )
        val repository = MovEquipProprioSegRepositoryImpl(
            movEquipProprioSegSharedPreferencesDatasource
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
        val movEquipProprioSegSharedPreferencesDatasource =
            mock<MovEquipProprioSegSharedPreferencesDatasource>()
        whenever(movEquipProprioSegSharedPreferencesDatasource.clear()).thenReturn(
            Result.success(true)
        )
        val repository = MovEquipProprioSegRepositoryImpl(
            movEquipProprioSegSharedPreferencesDatasource
        )
        val result = repository.clear()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}