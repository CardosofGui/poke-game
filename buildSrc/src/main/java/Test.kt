object Test {
    const val junit = "4.13.2"
    const val test_junit = "1.1.3"
    const val espresso = "3.4.0"
}

object JUnit {
    const val junit = "junit:junit:${Test.junit}"
    const val test_junit = "androidx.test.ext:junit:${Test.test_junit}"
}

object Espresso {
    const val espresso = "androidx.test.espresso:espresso-core:${Test.espresso}"
}