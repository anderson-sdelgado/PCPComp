package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class IGetMovChaveEquipOpenListTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val colabRepository = mock<ColabRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IGetMovChaveEquipOpenList(
        movChaveEquipRepository = movChaveEquipRepository,
        colabRepository = colabRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository listOpen`() =
        runTest {
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.listOpen",
                        cause = Exception()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.listOpen"
            )
        }

    @Test
    fun `Check return failure if have error in ColabRepository getNome`() =
        runTest {
            val entity =
                MovChaveEquip(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date(),
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip= 1,
                    idLocalMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    matricVigiaMovChaveEquip = 19035,
                    observMovChaveEquip = "teste",
                )
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(entity)
                )
            )
            whenever(
                colabRepository.getNome(19759)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ColabRepository.getNome",
                        cause = Exception()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ColabRepository.getNome"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getDescr`() =
        runTest {
            val entity =
                MovChaveEquip(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date(),
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip= 1,
                    idLocalMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    matricVigiaMovChaveEquip = 19035,
                    observMovChaveEquip = "teste",
                )
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(entity)
                )
            )
            whenever(
                colabRepository.getNome(19759)
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            whenever(
                equipRepository.getDescr(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "EquipRepository.getDescr",
                        cause = Exception()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> EquipRepository.getDescr"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val entity =
                MovChaveEquip(
                    idMovChaveEquip = 1,
                    dthrMovChaveEquip = Date(),
                    tipoMovChaveEquip = TypeMovKey.RECEIPT,
                    idEquipMovChaveEquip= 1,
                    idLocalMovChaveEquip = 1,
                    matricColabMovChaveEquip = 19759,
                    matricVigiaMovChaveEquip = 19035,
                    observMovChaveEquip = "teste",
                )
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(entity)
                )
            )
            whenever(
                colabRepository.getNome(19759)
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            whenever(
                equipRepository.getDescr(1)
            ).thenReturn(
                Result.success("TRATOR")
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                1
            )
            val entityResult = entityList[0]
            assertEquals(
                entityResult.equip,
                "TRATOR"
            )
            assertEquals(
                entityResult.colab,
                "19759 - ANDERSON DA SILVA DELGADO"
            )
            assertEquals(
                entityResult.tipoMov,
                "DEVOLUÇÃO"
            )
        }
}