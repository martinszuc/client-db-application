package com.matos.app.ui.component.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matos.app.data.entity.Service
import com.matos.app.data.repository.ServiceRepository
import com.matos.app.ui.base.AbstractViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
) : AbstractViewModel() {
}
