package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.EquipRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IEquipRepositoryTest {

    private val equipRoomDatasource = mock<EquipRoomDatasource>()
    private val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
    private fun getRepository() = IEquipRepository(
        equipRoomDatasource,
        equipRetrofitDatasource
    )

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        whenever(
            equipRoomDatasource.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val repository = getRepository()
        val result = repository.deleteAll()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        whenever(equipRoomDatasource.deleteAll()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.deleteAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.deleteAll()
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> EquipRoomDatasource.deleteAll"
        )
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        whenever(
            equipRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRetrofitDatasource.recoverAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.recoverAll(token)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> EquipRetrofitDatasource.recoverAll"
        )
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val entityList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        val retrofitModelList = listOf(
            EquipRetrofitModel(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        whenever(
            equipRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.success(retrofitModelList)
        )
        val repository = getRepository()
        val result = repository.recoverAll(token)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(entityList))
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        val equipRoomModelList = listOf(
            EquipRoomModel(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        whenever(
            equipRoomDatasource.addAll(equipRoomModelList)
        ).thenReturn(
            Result.success(
                true
            )
        )
        val repository = getRepository()
        val result = repository.addAll(equipList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect addAll`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        val equipRoomModelList = listOf(
            EquipRoomModel(
                idEquip = 10,
                nroEquip = 19759,
                descrEquip = "teste"
            )
        )
        whenever(
            equipRoomDatasource.addAll(equipRoomModelList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.addAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.addAll(equipList)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> EquipRoomDatasource.addAll"
        )
    }

    @Test
    fun `Check return false if not exist Equip`() = runTest {
        whenever(
            equipRoomDatasource.checkNro(100)
        ).thenReturn(
            Result.success(false)
        )
        val repository = getRepository()
        val result = repository.checkNro(100)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun `Check return true if exist Equip`() = runTest {
        whenever(
            equipRoomDatasource.checkNro(100)
        ).thenReturn(
            Result.success(true)
        )
        val repository = getRepository()
        val result = repository.checkNro(100)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun `Check return failure if have error in checkNro Datasource`() = runTest {
        whenever(
            equipRoomDatasource.checkNro(100)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.checkNro",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.checkNro(100)
        assertEquals(result.isFailure, true)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> EquipRoomDatasource.checkNro"
        )
    }

    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getNro`() = runTest {
        whenever(
            equipRoomDatasource.getNro(1)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.getNro",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.getNro(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> EquipRoomDatasource.getNro"
        )
    }

    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getNro return 0`() = runTest {
        whenever(
            equipRoomDatasource.getNro(1)
        ).thenReturn(
            Result.success(0)
        )
        val repository = getRepository()
        val result = repository.getNro(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> EquipRepositoryImpl.getNro"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.Exception: Nro is 0")
    }

    @Test
    fun `Check return nroEquip if EquipRoomDatasource getNro execute successfully`() = runTest {
        whenever(
            equipRoomDatasource.getNro(1)
        ).thenReturn(
            Result.success(100)
        )
        val repository = getRepository()
        val result = repository.getNro(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, 100)
    }


    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getId`() = runTest {
        whenever(
            equipRoomDatasource.getId(1)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasource.getId",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.getId(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> EquipRoomDatasource.getId"
        )
    }

    @Test
    fun `Check return failure if have failure in EquipRoomDatasource getId return 0`() = runTest {
        whenever(
            equipRoomDatasource.getId(100)
        ).thenReturn(
            Result.success(0)
        )
        val repository = getRepository()
        val result = repository.getId(100)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> EquipRepositoryImpl.getId"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.Exception: Id is 0")
    }

    @Test
    fun `Check return idEquip if EquipRoomDatasource getId execute successfully`() = runTest {
        whenever(
            equipRoomDatasource.getId(100)
        ).thenReturn(
            Result.success(10)
        )
        val repository = getRepository()
        val result = repository.getId(100)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, 10)
    }
}