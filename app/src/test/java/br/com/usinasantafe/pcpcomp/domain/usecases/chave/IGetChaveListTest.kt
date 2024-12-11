package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetChaveListTest {

    private val chaveRepository = mock<ChaveRepository>()
    private val getDescrFullChave = mock<GetDescrFullChave>()
    private val usecase = IGetChaveList(
        chaveRepository = chaveRepository,
        getDescrFullChave = getDescrFullChave
    )

    @Test
    fun `Check return failure if have error in ChaveRepository listAll`() =
        runTest {
            whenever(
                chaveRepository.listAll()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ChaveRepository.listAll",
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
                "Failure Repository -> ChaveRepository.listAll"
            )
        }

    @Test
    fun `Check return failure if have error in GetDescrFullChave`() =
        runTest {
            whenever(
                chaveRepository.listAll()
            ).thenReturn(
                Result.success(
                    listOf(
                        Chave(
                            idChave = 1,
                            descrChave = "01 - SALA TI - TI",
                            idLocalTrab = 1
                        )
                    )
                )
            )
            whenever(
                getDescrFullChave(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetDescrFullChave",
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
                "Failure Usecase -> GetDescrFullChave"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                chaveRepository.listAll()
            ).thenReturn(
                Result.success(
                    listOf(
                        Chave(
                            idChave = 1,
                            descrChave = "01 - SALA TI",
                            idLocalTrab = 1
                        )
                    )
                )
            )
            whenever(
                getDescrFullChave(1)
            ).thenReturn(
                Result.success(
                    "01 - SALA TI - TI"
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.count(),
                1
            )
            val model = list[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.descr,
                "01 - SALA TI - TI"
            )
        }

}