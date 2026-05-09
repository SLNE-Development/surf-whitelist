import dev.slne.surf.api.gradle.util.registerRequired
import dev.slne.surf.api.gradle.util.registerSoft

plugins {
    id("dev.slne.surf.api.gradle.paper-plugin") version "+"
}

group = "dev.slne.surf.freebuild.whitelist"
version = findProperty("version") as String

dependencies {
    compileOnly("net.luckperms:api:5.4")
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.freebuild.whitelist.PaperMain")
    foliaSupported(true)
    generateLibraryLoader(false)
    withSurfDatabaseR2dbc("1.1.0-SNAPSHOT", "dev.slne.surf.freebuild.whitelist.libs")
    withCorePaper()

    serverDependencies {
        registerRequired("LuckPerms")
    }

    authors.add("red")
}