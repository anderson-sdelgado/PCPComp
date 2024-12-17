package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveEquipTest {

    @Test
    fun `Check execution correct`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 100,
                descrEquip = "teste"
            )
        )
        val equipRepository = Mockito.mock<EquipRepository>()
        whenever(equipRepository.addAll(equipList)).thenReturn(Result.success(true))
        val usecase = ISaveEquip(equipRepository)
        val result = usecase(equipList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 100,
                descrEquip = "teste"
            )
        )
        val equipRepository = Mockito.mock<EquipRepository>()
        whenever(equipRepository.addAll(equipList)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRepository.addAll",
                    cause = Exception()
                )
            )
        )
        val usecase = ISaveEquip(equipRepository)
        val result = usecase(equipList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRepository.addAll")
    }
}