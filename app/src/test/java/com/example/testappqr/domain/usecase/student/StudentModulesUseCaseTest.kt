package com.example.testappqr.domain.usecase.student

import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.domain.repository.StudentRepository
import com.example.testappqr.domain.usecase.util.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for [StudentModulesUseCase]. Same idea as the professor one, but here
 * the repository returns an ApiResult<...> instead of a raw list — so this also
 * shows how to test code that flows the ApiResult wrapper through unchanged.
 */
class StudentModulesUseCaseTest {

    private val studentRepository: StudentRepository = mockk()
    private val useCase = StudentModulesUseCase(studentRepository)

    @Test
    fun `invoke returns the Success result from the repository`() = runTest {
        // Arrange
        val modules = listOf(mockk<ModuleLazyDTO>())
        coEvery { studentRepository.studentModules("student-1") } returns ApiResult.Success(modules)

        // Act
        val result = useCase("student-1")

        // Assert — the wrapper and its payload are passed straight through.
        assertTrue(result is ApiResult.Success)
        assertEquals(modules, (result as ApiResult.Success).data)
    }
}
