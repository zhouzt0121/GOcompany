package com.company.go.data.model

enum class SensorType(
    val displayName: String,
    val description: String,
    val icon: String
) {
    ACCELEROMETER("加速计", "Measures linear acceleration", "accelerometer"),
    STEP_COUNTER("计步器", "Counts steps taken", "steps"),
    STEP_DETECTOR("计步检测", "Detects individual steps", "step_detect"),
    GYROSCOPE("陀螺仪", "Measures rotation", "gyroscope"),
    MAGNETOMETER("磁场传感器", "Measures magnetic field", "compass"),
    LINEAR_ACCELEROMETER("线性加速计", "Linear acceleration only", "linear_accel"),
    LIGHT_SENSOR("光线传感器", "Measures ambient light", "light"),
    CELL_INFO("基站信息", "Cell tower information", "signal")
}
