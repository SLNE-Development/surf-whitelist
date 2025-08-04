plugins {
    id("dev.slne.surf.surfapi.gradle.core")
}

surfCoreApi {
    withCloudClientCommon()
}

dependencies {
    api(project(":surf-whitelist-core:surf-whitelist-core-common"))
}