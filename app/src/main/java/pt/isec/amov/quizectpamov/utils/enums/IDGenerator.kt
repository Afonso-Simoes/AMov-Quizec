object IDGenerator {
    fun generate(length: Int = 6): String {
        val chars = ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
    }
}