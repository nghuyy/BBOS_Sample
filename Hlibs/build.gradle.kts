var buildNumber = 1
var localVersion = "1.0.${buildNumber}"
var projectName = "Hlibs"

var bb_buildfile = listOf<String>(
    "**/*.cod",
    "**/*.debug",
    "**/*.jad",
    "**/*.jar",
    "**/*.export.xml",
    "**/*.csl",
    "**/*.cso",
    "**/*.mak",
    "**/*.files",
    "**/*.err"

)

var folder = project.projectDir
var api5_path = "C:\\Program Files (x86)\\Research In Motion\\BlackBerry JDE 5.0.0"
var api7_path = "C:\\Program Files (x86)\\Research In Motion\\BlackBerry JDE 7.1.0"
var jdk_path = "C:\\Program Files (x86)\\Java\\jdk1.5.0_22\\bin"
var warnkeyRelease = "warnkey=0x52424200;0x52525400;0x5242534b;0x42424944;0x52435200;0x4e464352;0x52455345"
var warnkey = "warnkey=0x52424200;0x52525400;0x52435200"



task("Clean") {
    doLast {
        Clean()
    }
}
task("GenerateFiles") {
    doLast {
        GenerateFiles()
    }
}

task("Build"){
    doLast {
        Clean()
        GenerateFiles()
        Build()
    }
}

fun Build(){
    exec {
        commandLine = listOf(
            "${api7_path}\\bin\\rapc.exe",
            "-quiet",
            "codename=build\\${projectName}",
            "${projectName}.rapc",
            warnkeyRelease,
            "@${projectName}.files"
        )
    }
}
fun GenerateFiles() {
    //setup files
    var filesStr = "import=${api7_path}\\lib\\net_rim_api.jar\n"
    var shortPathStr = ""
    project.fileTree("src").filter { it.isFile() }.files.forEach {
        filesStr += it.path + "\n"
        shortPathStr += it.path.replace(project.projectDir.toString() + "\\", "") + "\n"
    }
    project.fileTree("res").filter { it.isFile() }.files.forEach {
        filesStr += it.path + "\n"
        shortPathStr += it.path.replace(project.projectDir.toString() + "\\", "") + "\n"
    }
    File("${projectName}.files").writeText(filesStr, charset("utf-8"))
    var jdp_str = File("${projectName}.jdp").readText(charset("utf-8"))
    var arraystr = jdp_str.split("[Files\r\n")
    var firsttmp = arraystr[0]
    var lasttmp = arraystr[1]
    var laststr = lasttmp.split(Regex("\\]"),2)[1]
    File("${projectName}.jdp").writeText(firsttmp + "[Files\r\n" + shortPathStr + "]" + laststr,charset("utf-8"))

}

fun Clean() {
    delete("build")
    delete(fileTree(".").matching {
        include(bb_buildfile)
    })
    delete("dist")
}
