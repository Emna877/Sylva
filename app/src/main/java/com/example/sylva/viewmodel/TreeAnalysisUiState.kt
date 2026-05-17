package com.example.sylva.viewmodel

import com.example.sylva.ui.model.TreeProfile

sealed interface TreeAnalysisUiState {
    data object Idle : TreeAnalysisUiState
    data object Loading : TreeAnalysisUiState
    data class Success(val profile: TreeProfile) : TreeAnalysisUiState
    data class Error(val message: String) : TreeAnalysisUiState
}

