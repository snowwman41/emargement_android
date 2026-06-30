package com.example.testappqr.domain.usecase.professor

import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests for [GetUserModulesUseCase]. The use case just delegates to
 * ProfessorRepository.modules(userId). We don't want a real backend here, so we
 * give it a FAKE repository built with MockK and tell that fake what to return.
 *
 * This is "mocking": replacing a real dependency with a controllable stand-in
 * so we can test one unit (the use case) in isolation.
 */
class GetUserModulesUseCaseTest {

    // A fake ProfessorRepository — every method does nothing until we script it.
    private val professorRepository: ProfessorRepository = mockk()

    // The real use case under test, wired to the fake repository.
    private val useCase = GetUserModulesUseCase(professorRepository)

    @Test
    fun `invoke returns the modules from the repository`() = runTest {
        // Arrange — a fake list, and script the fake repo to return it.
        // mockk<ModuleLazyDTO>() makes a placeholder object so we don't need to
        // know the DTO's real fields just to test the wiring.
        val expectedModules = listOf(mockk<ModuleLazyDTO>(), mockk<ModuleLazyDTO>())
        coEvery { professorRepository.modules("teacher-1") } returns expectedModules

        // Act
        val result = useCase("teacher-1")

        // Assert — the use case hands back exactly what the repo gave it.
        assertEquals(expectedModules, result)
    }

    @Test
    fun `invoke forwards the userId to the repository`() = runTest {
        // Arrange — accept any id, return an empty list.
        coEvery { professorRepository.modules(any()) } returns emptyList()

        // Act
        useCase("teacher-42")

        // Assert — prove the repo was called with the exact id we passed in.
        coVerify { professorRepository.modules("teacher-42") }
    }
}
