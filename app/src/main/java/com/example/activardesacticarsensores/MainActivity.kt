package com.example.activardesacticarsensores

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var isSensorEnabled = false
    private var textButton : Button? = null
    private var textView : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Busca el botón en la interfaz de usuario y configura su acción
        val toggleButton = findViewById<Button>(R.id.activardesactivar)
        toggleButton.setOnClickListener {
            if (isSensorEnabled) {
                disableSensor()
            } else {
                enableSensor()
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        // Manejar cambios en el sensor
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        textView = findViewById(R.id.textView)
        textView!!.text = "X: " + x + "\nY: " + y + "\nZ: " + z
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Manejar cambios en la precisión del sensor si es necesario

    }

    private fun enableSensor() {
        var toggleButton = findViewById<Button>(R.id.activardesactivar)

        if (sensorManager != null) {
            sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            isSensorEnabled = true

            toggleButton.setText("Desactivar")
        }
    }

    private fun disableSensor() {
        var toggleButton = findViewById<Button>(R.id.activardesactivar)

        if (sensorManager != null) {
            sensorManager!!.unregisterListener(this)
            isSensorEnabled = false
            toggleButton.setText("Activar")
        }
    }

    override fun onPause() {
        super.onPause()
        // Deshabilitar el sensor cuando la actividad esté en pausa
        if (isSensorEnabled) {
            disableSensor()
        }
    }

    override fun onResume() {
        super.onResume()
        // Habilitar el sensor cuando la actividad se reanude
        if (!isSensorEnabled) {
            enableSensor()
        }
    }
}
