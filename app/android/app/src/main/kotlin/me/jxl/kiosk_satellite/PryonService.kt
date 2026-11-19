package me.jxl.kiosk_satellite

/**
 * Whether the ROM runs pryond, the service for Amazon's wake word engine on
 * the pryond socket (PryonCatalog).
 *
 * The Echo Dot and Echo Show images say so with `ro.kiosk_satellite.pryon`.
 * The service is the same with a screen and without one. Whether it has
 * anything to listen with is up to the files installed on the device, which
 * the service reports in its hello.
 */
object PryonService {
    const val PROPERTY = "ro.kiosk_satellite.pryon"

    /**
     * A non-default socket path for pryond, for a ROM that cannot (or
     * should not) listen at the Echo images' own `/dev/socket/pryond`.
     * Empty unless a build sets it, in which case PryonCatalog connects
     * there instead. Unlike [PROPERTY] this is a `persist.*` property, so
     * it can be changed without a factory reset while the right path is
     * still being worked out.
     */
    const val SOCKET_PROPERTY = "persist.kiosk_satellite.pryon_socket"

    fun detect(property: (String) -> String): Boolean = property(PROPERTY) == "1"

    val available: Boolean by lazy { detect(Headless::property) }

    /** Null unless a build set [SOCKET_PROPERTY] to something. */
    val socketPathOverride: String? by lazy {
        Headless.property(SOCKET_PROPERTY).ifEmpty { null }
    }
}
