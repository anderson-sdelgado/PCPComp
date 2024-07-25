package br.com.usinasantafe.pcpcomp

import android.content.Context
import androidx.room.*
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.utils.BASE_DB
import br.com.usinasantafe.pcpcomp.utils.VERSION_DB

fun provideRoomTest(appContext: Context): AppDatabaseRoom {
    return Room.inMemoryDatabaseBuilder(
        appContext, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
}
