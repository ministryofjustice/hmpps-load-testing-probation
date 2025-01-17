import java.nio.file.Path
import java.nio.file.Paths
import java.util.Objects.requireNonNull

object IDEPathHelper {

    private val projectRootDir = Paths.get(requireNonNull(javaClass.getResource("gatling.conf"), "Couldn't locate gatling.conf").toURI()).parent.parent.parent.parent
    private val gradleBuildDirectory = projectRootDir.resolve("build")
    private val gradleSrcDirectory = projectRootDir.resolve("src").resolve("gatling")

    val gradleResourcesDirectory: Path = gradleSrcDirectory.resolve("resources")
    val gradleBinariesDirectory: Path = gradleBuildDirectory.resolve("classes").resolve("kotlin").resolve("gatling")
    val resultsDirectory: Path = gradleBuildDirectory.resolve("reports").resolve("gatling")
}
