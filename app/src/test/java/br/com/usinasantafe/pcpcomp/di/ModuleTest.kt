package br.com.usinasantafe.pcpcomp.di

import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class ModuleTest : KoinTest {

    @Test
    fun checkAllModules() {
        appModule.verify()
    }

}