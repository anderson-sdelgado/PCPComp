package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetIdEquipMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetIdEquipMovChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository,
        equipRepository = equipRepository,
        startProcessSendData = startProcessSendData
    )

    @Test
    fun `Check return failure if have error in EquipRepository getId`() =
        runTest {
            whenever(
                equipRepository.getId(100)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "EquipRepository.getId",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> EquipRepository.getId"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository setIdChave`() =
        runTest {
            whenever(
                equipRepository.getId(100)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movChaveEquipRepository.setIdEquip(
                    idEquip = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.setIdEquip",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.setIdEquip"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRepository.getId(100)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movChaveEquipRepository.setIdEquip(
                    idEquip = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}