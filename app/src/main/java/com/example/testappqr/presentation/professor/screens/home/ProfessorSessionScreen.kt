package com.example.testappqr.presentation.professor.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.testappqr.data.models.SignatureDTO
import com.example.testappqr.presentation.navigation.NavigationView
import com.example.testappqr.presentation.professor.viewmodels.home.ProfessorSessionVM
import com.example.testappqr.utils.formatDate
import com.example.testappqr.utils.formatTime
//
//@Composable
//fun ProfessorSessionScreen(
//    navController: NavHostController, professorSessionVM: ProfessorSessionVM = hiltViewModel()
//) {
//    val professorSessionState by professorSessionVM.professorSessionState.collectAsStateWithLifecycle()
//    LaunchedEffect(Unit) {
//        professorSessionVM.getSession()
//    }
//    NavigationView(
//        navController = navController,
//        true,
//        if (professorSessionState.session != null) professorSessionState.session!!.sessionName else "null"
//    ) {
//        if (professorSessionState.session != null) {
//            Column(
//                modifier = Modifier.padding(10.dp)
//
//            ) {
//                Text(
//                    formatDate(professorSessionState.session!!.startTime),
//                    style = TextStyle(fontSize = 18.sp)
//                )
//                Text(
//                    formatTime(professorSessionState.session!!.startTime) + " - " + formatTime(
//                        professorSessionState.session!!.endTime
//                    ), style = TextStyle(fontSize = 18.sp)
//                )
//                LazyColumn {
//                    items(professorSessionState.session!!.signatures) { signature ->
//                        SignatureView(signature, Modifier
//                            .padding(8.dp)
//                            .fillParentMaxWidth())
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun SignatureView(signature: SignatureDTO, modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier
//    ) {
//        Text(signature.studentId,Modifier.padding(10.dp))
//    }
//}
//
//



@Composable
fun ProfessorSessionScreen(
    navController: NavHostController, professorSessionVM: ProfessorSessionVM = hiltViewModel()
) {
    val professorSessionState by professorSessionVM.professorSessionState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        professorSessionVM.getSession()
    }

    NavigationView(
        navController = navController,
        showBackButton = true,
        title = professorSessionState.session?.sessionName ?: "Session"
    ) {
        professorSessionState.session?.let { session ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = session.sessionName, style = TextStyle(fontSize = 24.sp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Date: ${formatDate(session.startTime)}", style = TextStyle(fontSize = 18.sp))
                        Text(
                            text = "Hour: ${formatTime(session.startTime)} - ${formatTime(session.endTime)}",
                            style = TextStyle(fontSize = 18.sp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .align(Alignment.End)
                                .background(if (session.active) Color.Green else Color.Red, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (session.active) "✅ Open" else "❌ Closed",
                                color = Color.White,
                                style = TextStyle(fontSize = 18.sp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn {
                    items(session.signatures) { signature ->
                        SignatureView(signature)
                    }
                }
            }
        }
    }
}

@Composable
fun SignatureView(signature: SignatureDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Name", style = TextStyle(fontSize = 20.sp))
                Text(text = signature.studentId, style = TextStyle(fontSize = 16.sp, color = Color.Gray))
            }

            Box(
                modifier = Modifier
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Signed at 10:00 am",
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }
        }
    }
}




