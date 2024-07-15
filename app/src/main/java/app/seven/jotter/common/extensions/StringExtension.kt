package app.seven.jotter.common.extensions


fun String.titleCase() = lowercase()
    .replaceFirstChar { it.uppercase() }
