plugins{
    application
}

dependencies{
    implementation(project(":external"))
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":web"))
    implementation(project(":boundary"))
    implementation(project(":crash-strategies"))
    implementation(kotlin("stdlib-jdk8"))
}

application{
    mainClassName="me.pedroeugenio.blazebot.Boot"
}