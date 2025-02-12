package com.example.testappqr.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.testappqr.SharedModel
import com.example.testappqr.models.ApiSSOResponse
import com.example.testappqr.models.ModuleLazyDTO
import com.example.testappqr.network.RetrofitApi
import com.example.testappqr.ui.common.components.BasicButton
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModulesComponent(
    sharedModel: SharedModel,
    navController: NavController
    ) {
    val coroutine = rememberCoroutineScope()
    var modulesList = remember { mutableStateListOf<ModuleLazyDTO>() }
    var moduleName by remember { mutableStateOf("") }
    var speciality by remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        getModules(sharedModel).let {
            modulesList.clear()
            modulesList.addAll(it)
        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), horizontalArrangement = Arrangement.End) {
        BasicButton(onClick = {
            showModal = true
        }, text = "Add Module")
    }

    LazyColumn {
        items(modulesList.toList()) { module ->
            Card(modifier = Modifier
                .fillParentMaxWidth()
                .padding(8.dp).clickable {
                    sharedModel.moduleId=module.moduleId
                    navController.navigate("module") }) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        module.moduleName,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(module.speciality, style = TextStyle(fontSize = 18.sp))
                }
            }
        }
    }



    if (showModal) {
        BasicAlertDialog(onDismissRequest = { showModal = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Enter a new Module")
                    TextField(label = { Text("Speciality") },
                        value = speciality,
                        onValueChange = { speciality = it })

                    TextField(
                        label = { Text("module name") },
                        value = moduleName,
                        onValueChange = { moduleName = it })
                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                        BasicButton(onClick = { showModal = false }, text = "Cancel", textSize = 16.sp)
                        BasicButton(onClick = {
                            coroutine.launch {
                                modulesList.clear()
                                modulesList.addAll(
                                    createModule(
                                        sharedModel,
                                        speciality,
                                        moduleName
                                    )
                                )
                                moduleName = ""
                                speciality = ""
                            }
                            showModal = false
                        }, text = "Add", textSize = 16.sp)
                    }
                }
            }
        }
    }
}


suspend fun getModules(sharedModel: SharedModel): List<ModuleLazyDTO> {
    val response =
        RetrofitApi.api.getModules(sharedModel.apiSSOResponse?.authenticationSuccess?.attributes?.uid.toString())
    return response
}

suspend fun createModule(
    sharedModel: SharedModel,
    speciality: String,
    moduleName: String
): List<ModuleLazyDTO> {
    val response = RetrofitApi.api.createModule(
        ModuleLazyDTO(
            UUID.randomUUID(),
            moduleName,
            speciality,
            sharedModel.apiSSOResponse?.authenticationSuccess?.attributes?.uid.toString()
        )
    );   return response

}
