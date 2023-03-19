dependencies {
    implementation(project(":domain"))
    implementation(project(":boundary"))
    implementation(project(":external"))
    implementation(project(":crash-strategies"))
    implementation(project(mapOf("path" to ":crash-strategies:after-crash-strategy")))
    implementation(project(mapOf("path" to ":crash-strategies:after-three-greens")))
}