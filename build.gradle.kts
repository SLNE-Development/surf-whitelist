plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin") version "1.21.11+"
}

group = "dev.slne.surf.freebuild.whitelist"
version = findProperty("version") as String

surfPaperPluginApi {
    mainClass("dev.slne.surf.freebuild.whitelist.PaperMain")
    foliaSupported(true)
    generateLibraryLoader(false)
    withSurfDatabaseR2dbc("1.1.0-SNAPSHOT", "dev.slne.surf.freebuild.whitelist.libs")
    withCorePaper()

    authors.add("red")
}