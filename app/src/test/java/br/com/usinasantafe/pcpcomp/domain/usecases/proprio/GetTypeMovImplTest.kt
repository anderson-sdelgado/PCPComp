package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetTypeMovImplTest {

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioRepository getTipo`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(movEquipProprioRepository.getTipoMov()).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.getTipoMov",
                        cause = Exception()
                    )
                )
            )
            val result = IGetTypeMov(movEquipProprioRepository)
            val resultGet = result()
            assertEquals(resultGet.isFailure, true)
            assertEquals(
                resultGet.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.getTipoMov")
    }

    @Test
    fun `Chech return typeMov if MovEquipProprioRepository getTipo execute success`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(movEquipProprioRepository.getTipoMov()).thenReturn(
                Result.success(TypeMov.INPUT)
            )
            val result = IGetTypeMov(movEquipProprioRepository)
            val resultGet = result()
            assertTrue(resultGet.isSuccess)
            assertEquals(
                resultGet.getOrNull()!!,
                TypeMov.INPUT
            )
        }
}