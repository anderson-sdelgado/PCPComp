package br.com.usinasantafe.pcpcomp.utils

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertEquals
import org.junit.Test


class GsonTest {

    @Test
    fun `Test save data ColabRoomModel`() {
        val gson = Gson()
        val itemType = object : TypeToken<List<Colab>>() {}.type
        val colabList = gson.fromJson<List<Colab>>(returnDataServerColab, itemType)
        assertEquals(colabList.size, 2447)
    }

}