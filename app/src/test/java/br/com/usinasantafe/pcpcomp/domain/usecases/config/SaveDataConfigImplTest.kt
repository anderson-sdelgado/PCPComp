package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertFailsWith

class SaveDataConfigImplTest {

    @Test
    fun `Chech return true if save is correct`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.save(config)).thenReturn(Result.success(true))
        val usecase = SaveDataConfigImpl(configRepository)
        val result = usecase(
            number = "16997417840",
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Chech return Failure Usecase if have error in Usecase`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val usecase = SaveDataConfigImpl(configRepository)
        val exception = assertFailsWith<Exception> {
            usecase(
                number = "16997417840A",
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        }
        assertEquals("Failure Usecase", exception.message)
        assertEquals(NumberFormatException("For input string: \"16997417840A\"").toString(), exception.cause!!.toString())
    }
}