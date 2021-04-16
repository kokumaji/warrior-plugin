dependencies {
    compileOnly("org.apache.logging.log4j:log4j-core:2.3")
    compileOnly("io.netty:netty-all:4.1.24.Final")
    compileOnly("com.mojang:authlib:1.5.21")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    compileJava {
        options.encoding = "UTF-8"
    }
}
