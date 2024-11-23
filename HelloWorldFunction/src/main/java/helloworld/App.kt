package helloworld

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.util.stream.Collectors

@Suppress("MagicNumber", "SwallowException", "TooGenericExceptionCaught", "SameParameterValue")
class App : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val headers = mapOf(
            "Content-Type" to "application/json",
            "X-Custom-Header" to "application/json"
        )
        
        val response = APIGatewayProxyResponseEvent().withHeaders(headers)
        
        return try {
            val testString: String = "test"
            
            if (testString == null) {
                throw IllegalArgumentException("Test exception")
            }
            
            val pageContents = getPageContents("https://checkip.amazonaws.com")
            val output = """{ "message": "hello world", "location": "$pageContents" }"""
            
            response
                .withStatusCode(200)
                .withBody(output)
        } catch (_: Exception) {
            response
                .withBody("{}")
                .withStatusCode(500)
        }
    }
    
    private fun getPageContents(address: String): String {
        val url = URI.create(address).toURL()
        return url.openStream().use { stream ->
            BufferedReader(InputStreamReader(stream)).use { reader ->
                reader.lines().collect(Collectors.joining(System.lineSeparator()))
            }
        }
    }
}
