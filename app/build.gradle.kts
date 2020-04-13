import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.google.protobuf")
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        multiDexEnabled = AndroidConfig.MULTI_DEX_ENABLED
        vectorDrawables.useSupportLibrary = AndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        testInstrumentationRunnerArgument("runnerBuilder", AndroidConfig.TEST_RUNNER_BUILDER)
        setProperty("archivesBaseName", AndroidConfig.APK_NAME)

        androidExtensions {
            isExperimental = true
        }

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("String", "ENDPOINT", Config.SERVER_ENDPOINT)
        }
        getByName("debug") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("String", "ENDPOINT", Config.SERVER_ENDPOINT)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/LICENSE*")
    }

    lintOptions {
        disable("GoogleAppIndexingWarning", "HardcodedText", "InvalidPackage")
        textReport = true
        textOutput("stdout")
    }

    kotlinOptions {
        val options = this as? KotlinJvmOptions
        options?.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.11.0"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.28.1"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("java") {
                    option("lite")
                }
            }
            it.plugins {
                id("grpc") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //AndroidX
    implementation(Libs.androidx_app_compat)
    implementation(Libs.androidx_constraintlayout)
    implementation(Libs.androidx_core)
    implementation(Libs.androidx_material)
    implementation(Libs.androidx_recyclerview)
    implementation(Libs.androidx_cardview)
    implementation(Libs.androidx_fragment)
    // GRPC
    implementation(Libs.grpc_okhttp)
    implementation(Libs.grpc_protobuf_lite)
    implementation(Libs.grpc_stub)
    implementation(Libs.javax_annotation)
    // DI
    implementation(Libs.toothpick_ktp)
    implementation(Libs.toothpick_smoothie_lifecycle)
    implementation(Libs.toothpick_smoothie_viewmodel)
    kapt(Libs.toothpick_kapt)

    // Cicerone
    implementation(Libs.cicerone)

    // Lifecycle
    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_livedata)
    implementation(Libs.lifecycle_viewmodel)

    // Coil
    implementation(Libs.coil)

    // Adapter Delegates
    implementation(Libs.adapter_delegates)
    implementation(Libs.adapter_delegates_dsl)

    //Networking
    implementation(Libs.okhttp_logging_interceptor)
    implementation(Libs.retrofit)

    // Coroutines
    implementation(Libs.coroutines_android)
    implementation(Libs.coroutines)

    //Timber
    implementation(Libs.timber)

    // Unit test
    testImplementation(Libs.kotlinx_coroutines_test)
    testImplementation(Libs.junit4)
    testImplementation(Libs.junit_ext)
    testImplementation(Libs.mockito_core)
    testImplementation(Libs.mockito_kotlin)
    testProtobuf(files("src/main/proto"))

    // Leakcanary
    debugImplementation(Libs.leakcanary)
}

tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinCompile::class).all {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi"
        )
    }
}