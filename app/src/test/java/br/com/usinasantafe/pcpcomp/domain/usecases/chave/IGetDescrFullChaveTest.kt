package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalTrabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetDescrFullChaveTest {

    private val chaveRepository = mock<ChaveRepository>()
    private val localTrabRepository = mock<LocalTrabRepository>()

    private val usecase = IGetDescrFullChave(
        chaveRepository = chaveRepository,
        localTrabRepository = localTrabRepository
    )

    @Test
    fun `Check return failure if have error in ChaveRepository get`() =
        runTest {
            whenever(
                chaveRepository.get(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ChaveRepository.get",
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
                "Failure Repository -> ChaveRepository.get"
            )
        }

    @Test
    fun `Check return failure if have error in LocalTrabRepository getDescr`() =
        runTest {
            whenever(
                chaveRepository.get(1)
            ).thenReturn(
                Result.success(
                    Chave(
                        idChave = 1,
                        descrChave = "01 - SALA TI",
                        idLocalTrab = 1
                    )
                )
            )
            whenever(
                localTrabRepository.getDescr(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "LocalTrabRepository.getDescr",
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
                "Failure Repository -> LocalTrabRepository.getDescr"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return description if IGetDescrFullChave execute successfully`() =
        runTest {
            whenever(
                chaveRepository.get(1)
            ).thenReturn(
                Result.success(
                    Chave(
                        idChave = 1,
                        descrChave = "01 - SALA TI",
                        idLocalTrab = 1
                    )
                )
            )
            whenever(
                localTrabRepository.getDescr(1)
            ).thenReturn(
                Result.success("TI")
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val descr = result.getOrNull()!!
            assertEquals(
                descr,
                "01 - SALA TI - TI"
            )
        }
}