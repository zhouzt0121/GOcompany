package com.company.go.sensor

import com.company.go.data.model.SensorData

class CellInfoEmulator {
    
    fun generateCellInfoData(signalStrength: Int = -70, cellId: Int = 12345, lac: Int = 54321,
        mcc: String = "460", mnc: String = "01", timestamp: Long = System.currentTimeMillis()): SensorData {
        val normalizedSignal = (signalStrength + 140) / 140.0f
        return SensorData(sensorType = "CELL_INFO", timestamp = timestamp,
            values = listOf(signalStrength.toFloat(), cellId.toFloat(), lac.toFloat()), accuracy = 1,
            metadata = mapOf("signalStrength" to signalStrength.toString(), "cellId" to cellId.toString(),
                "lac" to lac.toString(), "mcc" to mcc, "mnc" to mnc, "bars" to "${(normalizedSignal * 4 + 1).toInt()}/4"))
    }
    
    fun generateVariableSignal(baseStrength: Int = -70, variation: Int = 10, timestamp: Long = System.currentTimeMillis()): SensorData {
        val randomVariation = (Math.random() - 0.5) * 2 * variation
        val actualStrength = (baseStrength + randomVariation).toInt().coerceIn(-140, 0)
        return generateCellInfoData(signalStrength = actualStrength, timestamp = timestamp)
    }
}
