package br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab

import org.koin.test.KoinTest

class NomeColabScreenTest: KoinTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    val getNomeColab: GetNomeColab by inject()
//    val setMatricColab: SetMatricColab by inject()
//
//    @Before
//    fun before() {
//        val server = MockWebServer()
//        server.start()
//        loadKoinModules(generateTestAppComponent(server.url("").toString()))
//    }
//
//    @Test
//    fun check_return_failure_if_not_have_data_in_colab_table() = runTest {
//        setContent(matricColab = "19759")
//        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
//        composeTestRule.waitUntilTimeout(2_000)
//        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
//        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Usecase -> RecoverNomeColab -> java.lang.NullPointerException")
//    }
//
//    @Test
//    fun check_return_nomeColab_if_have_vigia_in_table() = runTest {
//        val colabRoomDatasource: ColabRoomDatasource by inject()
//        colabRoomDatasource.addAll(
//            listOf(
//                ColabRoomModel(
//                    matricColab = 19759,
//                    nomeColab = "ANDERSON DA SILVA DELGADO"
//                )
//            )
//        )
//        setContent(matricColab = "19759")
//        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
//        composeTestRule.waitUntilTimeout(2_000)
//        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
//    }
//
//    private fun setContent(
//        flowApp: FlowApp = FlowApp.ADD,
//        typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
//        pos: Int = 0,
//        matricColab: String
//    ) {
//        composeTestRule.setContent {
////            NomeColabScreen(
////                viewModel = NomeColabViewModel(
////                    SavedStateHandle(
////                        mapOf(
////                            Args.FLOW_APP_ARGS to flowApp.ordinal,
////                            Args.TYPE_OCUPANTE_ARGS to typeOcupante.ordinal,
////                            Args.ID_ARGS to pos,
////                            Args.MATRIC_COLAB_ARGS to matricColab
////                        )
////                    ),
////                    getNomeColab,
////                    setMatricColab
////                ),
////                onNavMatricColab = {},
////                onNavPassagColabList = {}
////            )
//        }
//    }

}