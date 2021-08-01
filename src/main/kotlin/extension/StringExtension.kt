package extension

fun String.hasRepeatedChar(): Boolean {
    this.toCharArray().sorted().let {chars ->
        chars.forEachIndexed { index, c ->
            if (c == chars.getOrNull(index + 1))
                return true
        }
    }
    return false
}