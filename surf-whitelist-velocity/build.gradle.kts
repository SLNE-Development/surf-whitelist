plugins {
    id("dev.slne.surf.surfapi.gradle.velocity")
}

surfVelocityApi {
    withCloudClientVelocity()
}

dependencies {
    api(project(":surf-whitelist-core:surf-whitelist-core-client"))
}