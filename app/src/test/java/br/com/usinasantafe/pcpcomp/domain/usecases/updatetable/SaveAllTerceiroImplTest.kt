package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class SaveAllTerceiroImplTest {

    @Test
    fun `Check execution correct`() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro",
            )
        )
        val terceiroRepository = Mockito.mock<TerceiroRepository>()
        whenever(terceiroRepository.addAll(terceiroList)).thenReturn(Result.success(true))
        val usecase = SaveAllTerceiroImpl(terceiroRepository)
        val result = usecase(terceiroList)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val terceiroList = listOf(
            Terceiro(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "Terceiro",
                empresaTerceiro = "Empresa Terceiro",
            )
        )
        val terceiroRepository = Mockito.mock<TerceiroRepository>()
        whenever(terceiroRepository.addAll(terceiroList)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "TerceiroRepository.addAll",
                    cause = Exception()
                )
            )
        )
        val usecase = SaveAllTerceiroImpl(terceiroRepository)
        val result = usecase(terceiroList)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> TerceiroRepository.addAll")
    }
}