package com.example.testappqr.presentation.module

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class ModuleViewModel : ViewModel() {
    // Mutable states inside the ViewModel
    private val _showModal = MutableStateFlow(false)
    val showModal: StateFlow<Boolean> = _showModal

    // Methods to update states
    fun setShowModal(visible: Boolean) {
        _showModal.value = visible
    }

}
