import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
}

// key.properties 已被 .gitignore 忽略，没有就 release 不签名（合规）
fun loadSigningProperties(): Properties {
    val props = Properties()
    val keyProps = rootProject.file("key.properties")
    if (keyProps.exists()) {
        props.load(FileInputStream(keyProps))
    }
    return props
}
val signingProps = loadSigningProperties()

android {
    namespace = "com.taoyue.taoyue_app_flutter"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    defaultConfig {
        applicationId = "com.taoyue.taoyue_app_flutter"
        minSdk = flutter.minSdkVersion
        targetSdk = 34
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    // 三套环境：包名后缀 = 环境开关
    flavorDimensions += "env"
    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
        }
        create("staging") {
            dimension = "env"
            applicationIdSuffix = ".staging"
        }
        create("prod") {
            dimension = "env"
            // 不加后缀 = 正式包
        }
    }

    signingConfigs {
        create("release") {
            val storeFileProp = signingProps.getProperty("storeFile")
            if (storeFileProp != null) {
                storeFile = file(storeFileProp)
                storePassword = signingProps.getProperty("storePassword")
                keyAlias = signingProps.getProperty("keyAlias")
                keyPassword = signingProps.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.findByName("release")
        }
    }
}

flutter {
    source = "../.."
}
