package com.example.testappqr.domain.usecase.util

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

/**
 * Tests for [safeApiCall] — wraps a (suspending) network call and turns the
 * outcome into an [ApiResult]: a normal return becomes Success, a thrown
 * exception becomes Error with a friendly message.
 *
 * safeApiCall is a `suspend` function, so every test runs inside `runTest { }`
 * which provides a coroutine to call it from.
 */
class SafeApiCallTest {

    @Test
    fun `returns Success when the call succeeds`() = runTest {
        // Act — the lambda stands in for a successful network call
        val result = safeApiCall { "session-data" }

        // Assert
        assertTrue(result is ApiResult.Success)
        assertEquals("session-data", (result as ApiResult.Success).data)
    }

    @Test
    fun `returns Error with network message on IOException`() = runTest {
        // Act — simulate a connectivity failure
        val result = safeApiCall<String> { throw IOException("no connection") }

        // Assert — IOException maps to the friendly network message
        assertTrue(result is ApiResult.Error)
        assertEquals("Network error occurred", (result as ApiResult.Error).message)
    }

    @Test
    fun `returns Error with the exception message on a generic error`() = runTest {
        // Act — any other exception falls through to the generic branch
        val result = safeApiCall<String> { throw IllegalStateException("something broke") }

        // Assert — the generic branch reuses the exception's own message
        assertTrue(result is ApiResult.Error)
        assertEquals("something broke", (result as ApiResult.Error).message)
    }
}
