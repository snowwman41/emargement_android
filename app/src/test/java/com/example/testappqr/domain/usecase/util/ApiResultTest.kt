package com.example.testappqr.domain.usecase.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for [ApiResult.handle] — the small "router" that calls the right
 * callback (onSuccess / onError / onLoading) depending on the result type.
 *
 * No coroutines and no mocks here: handle() is pure logic, so these are the
 * simplest possible tests.
 */
class ApiResultTest {

    @Test
    fun `handle calls onSuccess with the data when result is Success`() {
        // Arrange — a Success holding the string "hello"
        val result: ApiResult<String> = ApiResult.Success("hello")
        var received: String? = null

        // Act — run handle, capturing whatever onSuccess receives
        result.handle(onSuccess = { data -> received = data })

        // Assert — onSuccess should have been given "hello"
        assertEquals("hello", received)
    }

    @Test
    fun `handle calls onError with the message when result is Error`() {
        // Arrange
        val result: ApiResult<String> =
            ApiResult.Error(RuntimeException("boom"), "Network error occurred")
        var receivedMessage: String? = null

        // Act
        result.handle(onError = { _, message -> receivedMessage = message })

        // Assert
        assertEquals("Network error occurred", receivedMessage)
    }

    @Test
    fun `handle calls onLoading when result is Loading`() {
        // Arrange
        val result: ApiResult<String> = ApiResult.Loading
        var loadingCalled = false

        // Act
        result.handle(onLoading = { loadingCalled = true })

        // Assert
        assertTrue(loadingCalled)
    }

    @Test
    fun `handle does not call onSuccess when result is Error`() {
        // Arrange
        val result: ApiResult<String> =
            ApiResult.Error(RuntimeException("boom"), "Network error occurred")
        var successCalled = false

        // Act
        result.handle(onSuccess = { successCalled = true })

        // Assert — the wrong callback must never fire
        assertEquals(false, successCalled)
    }
}
