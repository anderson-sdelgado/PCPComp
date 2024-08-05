package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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

}