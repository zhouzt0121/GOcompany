package com.company.go.sensor

import com.company.go.data.model.SensorData

/**
 * Simulates cell tower information
 * Provides signal strength, cell tower ID, LAC, etc.
 */
class CellInfoEmulator {
    
    fun generateCellInfoData(
        signalStrength: Int = -70,  // dBm, -140 to 0
        cellId: Int = 12345,
        lac: Int = 54321,
        mcc: String = "460",  // China
        mnc: String = "01",   // China Mobile
        timestamp: Long = System.currentTimeMillis()
    ): SensorData {
        val normalizedSignal = (signalStrength + 140) / 140.0f  // Normalize to 0-1
        
        return SensorData(
            sensorType = "CELL_INFO",
            timestamp = timestamp,
            values = listOf(
                signalStrength.toFloat(),
                cellId.toFloat(),
                lac.toFloat()
            ),
            accuracy = 1,
            metadata = mapOf(
                "signalStrength" to signalStrength.toString(),
                "cellId" to cellId.toString(),
                "lac" to lac.toString(),
                "mcc" to mcc,
                "mnc" to mnc,
                "bars" to "${(normalizedSignal * 4 + 1).toInt()}/4"
            )
        )
    }
    
    fun generateVariableSignal(
        baseStrength: Int = -70,
        variation: Int = 10,  // ±dBm
        timestamp: Long = System.currentTimeMillis()
    ): SensorData {
        val randomVariation = (Math.random() - 0.5) * 2 * variation
        val actualStrength = (baseStrength + randomVariation).toInt().coerceIn(-140, 0)
        
        return generateCellInfoData(
            signalStrength = actualStrength,
            timestamp = timestamp
        )
    }
}
