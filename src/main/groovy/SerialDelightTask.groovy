import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SerialDelightTask extends DefaultTask {
    String packageName = ""

    @TaskAction
    def serialDelight() {
        if(packageName.length() < 1){
            throw new GroovyRuntimeException("packageName variable must be initialized")
        }
        String buildFolderPath = "build/sqldelight/" + packageName.replace(".", "/")
        File buildFolder = project.file(buildFolderPath)

        buildFolder.listFiles().each { file ->
            if(!file.absolutePath.endsWith("Queries.kt") && !file.absolutePath.endsWith("Wrapper.kt")){
                String serializableContent = file.getText('UTF-8')
                        .replaceAll(/package (.*?)\n/, 'package $1\n\nimport kotlinx.serialization.Serializable\nimport kotlinx.serialization.Optional')
                        .replaceFirst('data class Impl', '@Serializable data class Impl')
                        .replaceAll(/override val (.*?):(.*?)\?/, '@Optional override val $1:$2? = null')

                file.newWriter().withWriter { w ->
                    w << serializableContent
                }
            }
        }
    }
}

