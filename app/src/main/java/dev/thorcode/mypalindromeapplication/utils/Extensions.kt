package dev.thorcode.mypalindromeapplication.utils

fun String.isPalindrome(): Boolean {
    val chars = lowercase().split("").filter{it.isNotBlank()}
    val reversedChars = chars.reversed()
    return chars == reversedChars && isNotBlank()
}