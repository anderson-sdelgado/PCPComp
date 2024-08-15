package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetMatricColabImplTest {

    @Test
    fun `Chech return failure Usecase if matric is numeric invalid`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val usecase = SetMatricColabImpl(movEquipProprioRepository)
        val result = usecase("19759a")
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> SetMatricMotorista")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NumberFormatException: For input string: \"19759a\"")
    }

    @Test
    fun `Chech return failure Datasource if have error in setMatricMotorista`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.setMatricColab(19759)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.setMatricMotorista",
                    cause = Exception()
                )
            )
        )
        val usecase = SetMatricColabImpl(movEquipProprioRepository)
        val result = usecase("19759")
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.setMatricMotorista")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Chech return true if usecase is success`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(movEquipProprioRepository.setMatricColab(19759)).thenReturn(
            Result.success(true)
        )
        val usecase = SetMatricColabImpl(movEquipProprioRepository)
        val result = usecase("19759")
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }
}