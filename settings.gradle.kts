rootProject.name = "blaze-bot"

include("external")
include("application")
include("domain")
include("usecase")
include("web")
include("boundary")
include("crash-strategies")
include("crash-strategies:after-crash-strategy")
findProject(":crash-strategies:after-crash-strategy")?.name = "after-crash-strategy"
include("crash-strategies:after-three-greens")
findProject(":crash-strategies:after-three-greens")?.name = "after-three-greens"
