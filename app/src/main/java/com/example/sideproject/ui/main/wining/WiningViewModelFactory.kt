package com.example.sideproject.ui.main.wining

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WiningViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WiningViewModel::class.java)) {
            return WiningViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}