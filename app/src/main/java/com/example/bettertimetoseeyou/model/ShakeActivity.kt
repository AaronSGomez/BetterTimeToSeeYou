package com.example.bettertimetoseeyou.model

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.math.sqrt

class ShakeActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tvShakeValue: TextView
    private lateinit var tvShakeLevel: TextView
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? =null

    private var lastAcceleration = 0f
    private var currentAcceleration = 0f
    private var shakeIntensity= 0f


    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            val x= event.values[0]
            val y= event.values[1]
            val z= event.values[2]

            val acceleration = sqrt(x*x + y*y + z*z)
            lastAcceleration = currentAcceleration
            currentAcceleration = acceleration
        }
        val delta = currentAcceleration - lastAcceleration
        shakeIntensity =abs(delta)
        tvShakeValue.text = String.format("%.2f", shakeIntensity)

        val levelText = when{
            shakeIntensity < 1f -> "Nivel : quieto"
            shakeIntensity < 3f -> "Nivel : suave"
            shakeIntensity < 6f -> "Nivel : medio"
            else -> "Nivel : depravado"
        }
        tvShakeLevel.text = levelText


    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }

}