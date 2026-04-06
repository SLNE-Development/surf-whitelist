plugins {
    id("dev.slne.surf.api.gradle.paper-plugin") version "+"
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