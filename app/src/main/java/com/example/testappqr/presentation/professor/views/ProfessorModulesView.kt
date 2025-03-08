package com.example.testappqr.presentation.professor.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.testappqr.data.models.ModuleLazyDTO
import com.example.testappqr.presentation.professor.viewmodels.ProfessorVM
import java.util.UUID


@Composable
fun ProfessorModulesView(
    navController: NavController,
    professorVM: ProfessorVM = hiltViewModel()
) {
    val professorState by professorVM.professorState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        professorVM.getModules()
    }
    LazyColumn {
        items(professorState.modulesList) { module ->
            ModuleView(module = module,
                modifier = Modifier
                    .padding(6.dp)
                    .clickable {
                        navController.navigate("professor/module/${module.moduleId}")
                    }
            )
        }
    }
}

@Composable
fun ModuleView(module: ModuleLazyDTO, modifier: Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(
                module.moduleName,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModulePreview() {
    ModuleView(
        module = ModuleLazyDTO(
            UUID.randomUUID(),
            "Module 1", UUID.randomUUID()
        ),
        modifier = Modifier.fillMaxWidth()
    )
}