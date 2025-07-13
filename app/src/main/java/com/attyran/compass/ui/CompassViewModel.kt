package com.attyran.compass.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attyran.compass.sensor.CompassData
import com.attyran.compass.sensor.CompassSensor
import com.attyran.compass.sensor.LocationData
import com.attyran.compass.sensor.LocationSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CompassViewModel @Inject constructor(
    compassSensor: CompassSensor,
    locationSensor: LocationSensor
) : ViewModel() {

    val compassData: StateFlow<CompassData> = compassSensor.getCompassReadings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CompassData(0, "N")
        )

    val locationData: StateFlow<LocationData> = locationSensor.getLocationUpdates()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LocationData()
        )
} 