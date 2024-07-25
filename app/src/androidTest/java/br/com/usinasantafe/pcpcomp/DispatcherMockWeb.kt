package br.com.usinasantafe.pcpcomp

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

val dispatcherMockWebServer: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody("""{"idBD":1}""")
        }
        return MockResponse().setResponseCode(404)
    }
}