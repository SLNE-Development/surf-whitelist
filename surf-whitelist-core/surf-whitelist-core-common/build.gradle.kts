plugins {
    id("dev.slne.surf.surfapi.gradle.core")
}

surfCoreApi {
    withCloudCommon()
}

dependencies {
    api(project(":surf-whitelist-api"))
}
