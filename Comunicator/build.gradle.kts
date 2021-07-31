defaultTasks(
    "clean",
    "setupVersion",
    "setupOS5",
    "buildOS5",
    "zip",
    "setupOS6",
    "buildOS6",
    "zip6",
    "Release"
)

var buildNumber = 1
var localVersion = "1.0.${buildNumber}"
var projectName = "Comunicate"

var bb_buildfile = listOf<String>(
    "**/*.cod",
    "**/*.debug",
    "**/*.jad",
    "**/*.jar",
    "**/*.export.xml",
    "**/*.csl",
    "**/*.cso",
    "**/*.mak",
    "**/*.files"
)

var folder = project.projectDir
var api5_path = "C:\\Program Files (x86)\\Research In Motion\\BlackBerry JDE 5.0.0"
var api7_path = "C:\\Program Files (x86)\\Research In Motion\\BlackBerry JDE 7.1.0"
var jdk_path = "C:\\Program Files (x86)\\Java\\jdk1.5.0_22\\bin"
var warnkeyRelease = "warnkey=0x52424200;0x52525400;0x5242534b;0x42424944;0x52435200;0x4e464352;0x52455345"
var warnkey = "warnkey=0x52424200;0x52525400;0x52435200"
var packID = "blackberry.sig"
var passwordPath = rootProject.file(System.getProperty("user.home") + "/.gradle/.keystore").readText(charset("utf-8"))
var password = rootProject.file("${passwordPath}\\${packID}").readText(charset("utf-8"))
var appversion = File("${projectName}.jdp").readText(charset("utf-8")).split("Version=")[1].trim()


task("setupVersion") {
    doLast {
        //setup files
        File("${projectName}.jdp").writeText(
            File("${projectName}.jdp").readText(charset("utf-8"))
                .replace("Version=${appversion}", "Version=${localVersion}"), charset("utf-8")
        )
        File("${projectName}.rapc").writeText(
            File("${projectName}.rapc").readText(charset("utf-8"))
                .replace("MIDlet-Version: ${appversion}", "MIDlet-Version: ${localVersion}"), charset("utf-8")
        )
    }
}

task("setupOS5") {
    doLast {
        //setup files
        var filesStr = "import=${api5_path}\\lib\\net_rim_api.jar\n"
        project.fileTree("Mail/src").filter { it.isFile() }.files.forEach {
            filesStr += it.path + "\n"
        }
        project.fileTree("Mail/res").filter { it.isFile() }.files.forEach {
            filesStr += it.path + "\n"
        }
        File("Mail.files").writeText(filesStr, charset("utf-8"))
    }
}
task("generateFiles") {
    doLast { GenerateFiles() }
}

fun GenerateFiles() {
    //setup files
    var filesStr = "import=..\\Hlibs\\Hlibs.jar;${api7_path}\\lib\\net_rim_api.jar\n"
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
    var laststr = lasttmp.split(Regex("\\]"), 2)[1]
    File("${projectName}.jdp").writeText(firsttmp + "[Files\r\n" + shortPathStr + "]" + laststr, charset("utf-8"))

}
task("build") {
    doLast {
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
}


tasks.create("signSource6") {
    doLast {
        exec {
            commandLine(
                "${jdk_path}\\javaw.exe",
                "-jar",
                "${api7_path}\\bin\\SignatureTool.jar",
                "-a",
                "-p",
                password,
                "-r",
                "${folder}/build/OS6"
            )
            workingDir(api7_path)
        }
        delete("${folder}/build/OS6/cache")
    }
}
tasks.create<Copy>("copy6") {
    dependsOn(tasks.getByName("signSource6"))
    from("build/OS6")
    into("build/OS6/cache")
    include("*.cod", "*.jad")
}

task("Merge6") {
    dependsOn(tasks.getByName("copy6"))
    doLast {
        Thread.sleep(2000)
        exec {
            commandLine(
                "${api7_path}\\bin\\UpdateJad.exe",
                "-n",
                "${folder}\\build\\OS6\\cache\\Email.jad",
                "${folder}\\build\\OS6\\cache\\Mail.jad",
                "${folder}\\build\\OS6\\cache\\MailOS5.jad",
                "${folder}\\build\\OS6\\cache\\MailOS46.jad",
                "${folder}\\build\\OS6\\cache\\MailOS47.jad",
                "${folder}\\build\\OS6\\cache\\MailOS6.jad"
            )
            workingDir("${folder}/build/OS6/cache")
        }
        delete("${folder}\\build\\OS6\\cache\\Mail.jad")
        delete("${folder}\\build\\OS6\\cache\\MailOS5.jad")
        delete("${folder}\\build\\OS6\\cache\\MailOS46.jad")
        delete("${folder}\\build\\OS6\\cache\\MailOS47.jad")
        delete("${folder}\\build\\OS6\\cache\\MailOS6.jad")
    }
}

tasks.register<Zip>("zip6") {
    dependsOn(tasks.getByName("Merge6"))
    var DependsOn = "MailOS6" //jdp_text.split("[DependsOn\r\n")[1].trim().split("\r\n")[0]
    archiveFileName.set("${DependsOn}-${appversion}.zip")
    destinationDirectory.set(layout.projectDirectory.dir("build"))
    from("${folder}/build/OS6/cache")
}


tasks.create("signSource") {
    doLast {
        exec {
            commandLine(
                "${jdk_path}\\javaw.exe",
                "-jar",
                "${api5_path}\\bin\\SignatureTool.jar",
                "-a",
                "-p",
                password,
                "-r",
                "${folder}/build/OS5"
            )
            workingDir(api5_path)
        }
        delete("${folder}/build/OS5/cache")
    }
}

tasks.create<Copy>("copy") {
    dependsOn(tasks.getByName("signSource"))
    from("build/OS5")
    into("build/OS5/cache")
    include("*.cod", "*.jad")
}

task("Merge") {
    dependsOn(tasks.getByName("copy"))
    doLast {
        Thread.sleep(2000)
        exec {
            commandLine(
                "${api5_path}\\bin\\UpdateJad.exe",
                "-n",
                "${folder}\\build\\OS5\\cache\\Email.jad",
                "${folder}\\build\\OS5\\cache\\Mail.jad",
                "${folder}\\build\\OS5\\cache\\MailOS5.jad",
                "${folder}\\build\\OS5\\cache\\MailOS46.jad",
                "${folder}\\build\\OS5\\cache\\MailOS47.jad"
            )
            workingDir("${folder}/build/OS5/cache")
        }
        delete("${folder}\\build\\OS5\\cache\\Mail.jad")
        delete("${folder}\\build\\OS5\\cache\\MailOS5.jad")
        delete("${folder}\\build\\OS5\\cache\\MailOS46.jad")
        delete("${folder}\\build\\OS5\\cache\\MailOS47.jad")
    }
}

tasks.register<Zip>("zip") {
    dependsOn(tasks.getByName("Merge"))
    var DependsOn = "MailOS5" //jdp_text.split("[DependsOn\r\n")[1].trim().split("\r\n")[0]
    archiveFileName.set("${DependsOn}-${appversion}.zip")
    destinationDirectory.set(layout.projectDirectory.dir("build"))
    from("${folder}/build/OS5/cache")
}

tasks.register("Release") {
    doLast {
        delete("dist")
        exec {
            commandLine = listOf("git", "clone", "git@github.com:nghuyy/BlackberryMail_Release.git", "dist")
        }
        copy {
            from("build/")
            into("dist/")
            include("*.zip")
        }
        if (File("build/OS6/cache").exists()) {
            copy {
                from("build/OS5/cache")
                into("dist/OS5")
                include("*.cod", "*.jad")
                exclude("Mail.cod")
            }
            copy {
                from(zipTree("build/OS5/cache/Mail.cod"))
                into("dist/OS5")
            }
        }
        if (File("build/OS6/cache").exists()) {
            copy {
                from("build/OS6/cache")
                into("dist/OS6")
                include("*.cod", "*.jad")
                exclude("Mail.cod")
            }
            copy {
                from(zipTree("build/OS6/cache/Mail.cod"))
                into("dist/OS6")
            }
        }

        exec {
            workingDir = File("./dist")
            commandLine = listOf("git", "add", ".")
        }
        exec {
            workingDir = File("./dist")
            commandLine = listOf("git", "commit", "-m", "\"Update\"")
        }
        exec {
            workingDir = File("./dist")
            commandLine = listOf("git", "push", "-f", "origin", "main")
        }
    }
}

task("clean") {
    doLast {
        delete("build")
        delete(fileTree(".").matching {
            include(bb_buildfile)
        })
        delete("dist")
    }
}

