
plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

surfPaperPluginApi {
    withCloudClientPaper()
    mainClass("dev.slne.surf.whitelist.paper.PaperMain")
    bootstrapper("dev.slne.surf.whitelist.paper.PaperBootstrap")
}

dependencies {
    api(project(":surf-whitelist-core:surf-whitelist-core-client"))
}