package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class StartMovEquipVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository start NULL`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.start",
                        cause = Exception()
                    )
                )
            )
            val usecase = StartMovEquipVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.start"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository clear NULL`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.clear()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.clear",
                        cause = Exception()
                    )
                )
            )
            val usecase = StartMovEquipVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.clear"
            )
        }

    @Test
    fun `Check return true if StartMovEquipVisitTercImpl execute successfully NULL`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.clear()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = StartMovEquipVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository start NOT NULL`() =
        runTest {
            val passagList = listOf(
                MovEquipVisitTercPassag(
                    idVisitTercMovEquipVisitTercPassag = 10,
                    idMovEquipVisitTerc = 1,
                    idMovEquipVisitTercPassag = 1
                )
            )
            val mov = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                idVisitTercMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 1,
                idLocalMovEquipVisitTerc = 1,
                movEquipVisitTercPassagList = passagList
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start(mov)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.start",
                        cause = Exception()
                    )
                )
            )
            val usecase = StartMovEquipVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase(mov)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.start"
            )
        }

    @Test
    fun `Check return failure if have error in movEquipVisitTercPassagRepository add NOT NULL`() =
        runTest {
            val passagList = listOf(
                MovEquipVisitTercPassag(
                    idVisitTercMovEquipVisitTercPassag = 10,
                    idMovEquipVisitTerc = 1,
                    idMovEquipVisitTercPassag = 1
                )
            )
            val mov = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                idVisitTercMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 1,
                idLocalMovEquipVisitTerc = 1,
                movEquipVisitTercPassagList = passagList
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start(mov)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.add",
                        cause = Exception()
                    )
                )
            )
            val usecase = StartMovEquipVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase(mov)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.add"
            )
        }

    @Test
    fun `Check return true if StartMovEquipVisitTercImpl execute successfully NOT NULL`() =
        runTest {
            val passagList = listOf(
                MovEquipVisitTercPassag(
                    idVisitTercMovEquipVisitTercPassag = 10,
                    idMovEquipVisitTerc = 1,
                    idMovEquipVisitTercPassag = 1
                )
            )
            val mov = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                idVisitTercMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 1,
                idLocalMovEquipVisitTerc = 1,
                movEquipVisitTercPassagList = passagList
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            whenever(
                movEquipVisitTercRepository.start(mov)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = StartMovEquipVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository
            )
            val result = usecase(mov)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}