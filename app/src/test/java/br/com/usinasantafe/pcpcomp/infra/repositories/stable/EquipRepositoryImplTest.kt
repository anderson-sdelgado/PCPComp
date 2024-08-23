package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class EquipRepositoryImplTest {

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.deleteAll()).thenReturn(Result.success(true))
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.deleteAll()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.deleteAll",
                    cause = Exception()
                )
            )
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRoomDatasource.deleteAll")
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRetrofitDatasource.recoverAll(token)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRetrofitDatasource.recoverAll",
                    cause = Exception()
                )
            )
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRetrofitDatasource.recoverAll")
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val equip = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759
            )
        )
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRetrofitDatasource.recoverAll(token)).thenReturn(Result.success(equip))
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.recoverAll(token)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(equip))
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759
            )
        )
        val equipRoomModelList = listOf(
            EquipRoomModel(
                idEquip = 10,
                nroEquip = 19759
            )
        )
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.addAll(equipRoomModelList)).thenReturn(
            Result.success(
                true
            )
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.addAll(equipList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect addAll`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759
            )
        )
        val equipRoomModelList = listOf(
            EquipRoomModel(
                idEquip = 10,
                nroEquip = 19759
            )
        )
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.addAll(equipRoomModelList)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.addAll",
                    cause = Exception()
                )
            )
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.addAll(equipList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRoomDatasource.addAll")
    }

    @Test
    fun `Check return false if not exist Equip`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.checkNro(100)).thenReturn(
            Result.success(false)
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.checkNro(100)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun `Check return true if exist Equip`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.checkNro(100)).thenReturn(
            Result.success(true)
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.checkNro(100)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Check return failure if have error in checkNro Datasource`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.checkNro(100)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.checkNro",
                    cause = Exception()
                )
            )
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.checkNro(100)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRoomDatasource.checkNro")
    }

    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getNro`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.getNro(1)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.getNro",
                    cause = Exception()
                )
            )
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.getNro(1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRoomDatasource.getNro")
    }

    @Test
    fun `Check return nroEquip if EquipRoomDatasource getNro execute successfully`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.getNro(1)).thenReturn(
            Result.success(100)
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.getNro(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, 100)
    }


    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getId`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.getId(1)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.getId",
                    cause = Exception()
                )
            )
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.getId(1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRoomDatasource.getId")
    }

    @Test
    fun `Check return idEquip if EquipRoomDatasource getId execute successfully`() = runTest {
        val equipRoomDatasource = mock<EquipRoomDatasource>()
        val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
        whenever(equipRoomDatasource.getId(100)).thenReturn(
            Result.success(10)
        )
        val repository = EquipRepositoryImpl(equipRoomDatasource, equipRetrofitDatasource)
        val result = repository.getId(100)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, 10)
    }
}