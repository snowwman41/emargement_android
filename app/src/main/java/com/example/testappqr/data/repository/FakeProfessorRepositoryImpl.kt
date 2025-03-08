package com.example.testappqr.data.repository

import com.example.testappqr.data.models.ModuleDTO
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.data.models.SSODTO
import com.example.testappqr.data.models.SessionDTO
import com.example.testappqr.data.models.TeacherLazyDTO
import com.example.testappqr.domain.repository.ProfessorRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FakeProfessorRepositoryImpl @Inject constructor() : ProfessorRepository {

//    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override suspend fun modules(): List<ModuleLazyDTO> {
        return listOf(
            ModuleLazyDTO(
                moduleId = UUID.fromString("b3559075-e922-4a35-b57c-261c94955c86"),
                moduleName = "Git",
                specialityId = UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
            ),
            ModuleLazyDTO(
                moduleId = UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                moduleName = "Mobile",
                specialityId = UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
            ),
            ModuleLazyDTO(
                moduleId = UUID.fromString("8c6e3e0b-603a-443d-891d-5985a6a1dbd4"),
                moduleName = "Environement Informatique",
                specialityId = UUID.fromString("b06509ed-e7f1-46ff-980b-85ef0d5935e0")
            )
        )
    }

    override suspend fun module(moduleId: UUID): ModuleDTO {
        return ModuleDTO(
            moduleId = UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
            moduleName = "Mobile",
            specialityId = "b06509ed-e7f1-46ff-980b-85ef0d5935e0",
            teachers = listOf(
                TeacherLazyDTO("s23022841", "Second", "Prof", "test2@mail.de"),
                TeacherLazyDTO("b24028566", "Oscar", "Bauer", "test@mail.de")
            ),
            sessions = listOf(
                SessionDTO(
                    UUID.fromString("10dc002f-35fa-445d-9a1c-99a78940333e"),
                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                    "third",
                    1740222636,
                    1740225636,
                    "null",
                    false,

                ),
                SessionDTO(
                    UUID.fromString("35ec002f-35fa-445d-9a1c-99a78940111e"),
                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                    "second",
                    1740222636,
                    1740225636,
                    "null",
                    false

                ),
                SessionDTO(
                    UUID.fromString("35ec002e-20fa-445d-9a1c-99a78940722e"),
                    UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
                    "first",
                    1740222636,
                    1740225636,
                    "null",
                    false
                )
            )
        )
    }

    override suspend fun openSession(sessionId: UUID): SessionDTO {
//        apiService.openSession(sessionId)
        return SessionDTO(
            UUID.fromString("10dc002f-35fa-445d-9a1c-99a78940333e"),
            UUID.fromString("66af01d4-17eb-402c-9efd-06da619e6d2f"),
            "third",
            1740222636,
            1740225636,
            "null",
            false
        )
    }

    override suspend fun closeSession(sessionId: UUID) {
        //        apiService.closeSession(sessionId)
    }

    override suspend fun addSession(session: SessionDTO): List<SessionDTO> {
        return emptyList()
    }

    override suspend fun verifyToken(): Boolean {
        TODO("Not yet implemented")
    }





    override suspend fun casValidate(url: String): SSODTO {
        TODO("Not yet implemented")
    }

    override suspend fun createModule(module: ModuleLazyDTO): List<ModuleLazyDTO> {
        TODO("Not yet implemented")
    }



    override suspend fun getSessions(): List<SessionDTO> {
        TODO("Not yet implemented")
    }
}