package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetNroEquipChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IGetNroEquipChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository getIdEquip`() =
        runTest {
            whenever(
                movChaveEquipRepository.getIdEquip(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.getIdEquip",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.getIdEquip"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getNro`() =
        runTest {
            whenever(
                movChaveEquipRepository.getIdEquip(1)
            ).thenReturn(
                Result.success(100)
            )
            whenever(
                equipRepository.getNro(100)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "EquipRepository.getNro",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> EquipRepository.getNro"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRepository.getIdEquip(1)
            ).thenReturn(
                Result.success(100)
            )
            whenever(
                equipRepository.getNro(100)
            ).thenReturn(
                Result.success(300)
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "300"
            )
        }
}