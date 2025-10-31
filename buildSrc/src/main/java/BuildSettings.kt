import org.gradle.api.JavaVersion

object BuildSettings {
    const val COMPILE_SDK = 36
    const val MIN_SDK = 21
    const val TARGET_SDK = 36

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val NAME_SPACE = "br.com.wms.nubanktest"

    val JAVA_VERSION = JavaVersion.VERSION_11
}