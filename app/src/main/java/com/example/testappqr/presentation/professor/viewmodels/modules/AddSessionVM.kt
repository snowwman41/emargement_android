//package com.example.testappqr.presentation.professor.viewmodels.modules
//
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.testappqr.data.models.SessionLazyDTO
//import com.example.testappqr.domain.usecase.professor.AddSessionUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import java.time.LocalDate
//import java.util.UUID
//import javax.inject.Inject
//
//@HiltViewModel
//class AddSessionVM @Inject constructor(
//    private val savedStateHandle: SavedStateHandle,
//    private val addSessionUseCase: AddSessionUseCase
//) :
//    ViewModel() {
//    val addSessionState = savedStateHandle.getStateFlow("addSessionState", AddSessionState())
//
//    fun onChangeSessionName(value: String) {
//        updateAddSessionState { it.copy(sessionName = value) }
//        println(value)
//
//    }
//
//    fun onChangeDate(value: String) {
//        updateAddSessionState { it.copy(date = value) }
//        println(value)
//    }
//
//    fun onChangeStartTime(value: String) {
//        updateAddSessionState { it.copy(startTime = value) }
//        println(value)
//    }
//
//    fun onChangeEndTime(value: String) {
//        updateAddSessionState { it.copy(endTime = value) }
//        println(value)
//
//    }
//
//    fun addSession(moduleId : UUID, updateSessionsList : (List<SessionLazyDTO>) -> Unit) {
//        println("add SESSION MODULE ID : $moduleId")
////        val moduleId = UUID.fromString(savedStateHandle.get<String>("moduleId"))
//        viewModelScope.launch {
//            val sessionsList = addSessionUseCase(
//                moduleId = moduleId,
//                sessionName = addSessionState.value.sessionName,
//                date = addSessionState.value.date,
//                startTime = addSessionState.value.startTime,
//                endTime = addSessionState.value.endTime
//            )
//            updateAddSessionState {
//                it.copy(
//                    sessionName = "",
//                    date = LocalDate.now().toString(),
//                    startTime = "00:00",
//                    endTime = "00:00"
//                )
//            }
//            updateSessionsList(sessionsList)
//        }
//    }
//
//    private fun updateAddSessionState(update: (AddSessionState) -> AddSessionState) {
//        savedStateHandle["addSessionState"] = update(addSessionState.value)
//    }
//}
////
////@Parcelize
////data class AddSessionState(
////    val sessionName: String = "",
////    val date: String = LocalDate.now().toString(),
////    val startTime: String = "00:00",
////    val endTime: String = "00:00"
////) : Parcelable