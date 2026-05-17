package com.example.sylva.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sylva.data.repository.TreeRepository
import com.example.sylva.ui.model.TreeProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TreeAnalysisViewModel(
    private val repository: TreeRepository = TreeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<TreeAnalysisUiState>(TreeAnalysisUiState.Idle)
    val uiState: StateFlow<TreeAnalysisUiState> = _uiState.asStateFlow()

    private var lastImageBytes: ByteArray? = null

    fun analyzeImage(imageBytes: ByteArray) {
        lastImageBytes = imageBytes
        _uiState.update { TreeAnalysisUiState.Loading }

        viewModelScope.launch {
            val result = repository.analyzeTree(imageBytes)
            _uiState.update {
                result.fold(
                    onSuccess = { TreeAnalysisUiState.Success(it) },
                    onFailure = {
                        TreeAnalysisUiState.Error(
                            it.message ?: it::class.java.simpleName.ifBlank { "Analysis failed" }
                        )
                    }
                )
            }
        }
    }

    fun retryLastAnalysis() {
        val bytes = lastImageBytes ?: return
        analyzeImage(bytes)
    }

    fun latestProfileOrNull(): TreeProfile? {
        return (uiState.value as? TreeAnalysisUiState.Success)?.profile
    }
}

