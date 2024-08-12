package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
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
}