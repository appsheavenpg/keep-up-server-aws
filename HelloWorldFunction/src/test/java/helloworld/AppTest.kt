package helloworld

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AppTest {
    @Test
    fun `successful response`() {
        val app = App()
        val result: APIGatewayProxyResponseEvent = app.handleRequest(null, null)
        
        assertEquals(200, result.statusCode)
        assertEquals("application/json", result.headers["Content-Type"])
        
        val content = result.body
        assertNotNull(content)
        assertTrue(content.contains("\"message\""))
        assertTrue(content.contains("\"hello world\""))
        assertTrue(content.contains("\"location\""))
    }
}
