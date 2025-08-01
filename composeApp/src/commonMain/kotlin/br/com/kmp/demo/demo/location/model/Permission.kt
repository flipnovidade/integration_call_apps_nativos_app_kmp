package br.com.kmp.demo.demo.location.model

enum class Permission {
    /**
     * Indicates that the system setting bluetooth service is on.
     */
    BLUETOOTH_SERVICE_ON,

    /**
     * App bluetooth permission.
     */
    BLUETOOTH,

    /**
     * Indicates that the system setting location service is on.
     */
    LOCATION_SERVICE_ON,

    /**
     * App location fine permission.
     */
    LOCATION_FOREGROUND,

    /**
     * App location background permission.
     */
    LOCATION_BACKGROUND,
}