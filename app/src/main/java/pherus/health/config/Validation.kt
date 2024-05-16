package pherus.health.config

object Validation {
    /**
     * Checks if the provided password meets the criteria for a strong password.
     * Criteria:
     * 1. Minimum length of 8 characters.
     * 2. Contains at least one uppercase letter.
     * 3. Contains at least one lowercase letter.
     * 4. Contains at least one digit.
     * 5. Contains at least one special character (non-alphanumeric).
     *
     * @param password The password string to be checked for strength.
     * @return True if the password meets the criteria for a strong password, false otherwise.
     */
    fun isStrongPassword(password: String): Boolean {
        // Minimum length requirement
        val minLength = 8
        // Check if the password contains at least one uppercase letter
        val hasUpperCase = "[A-Z]".toRegex().containsMatchIn(password)
        // Check if the password contains at least one lowercase letter
        val hasLowerCase = "[a-z]".toRegex().containsMatchIn(password)
        // Check if the password contains at least one digit
        val hasDigit = "\\d".toRegex().containsMatchIn(password)
        // Check if the password contains at least one special character (non-alphanumeric)
        val hasSpecialChar = "[^A-Za-z0-9]".toRegex().containsMatchIn(password)
        // Return true if the password meets all criteria, false otherwise
        return password.length >= minLength && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar
    }

    /**
     * Compares two passwords to verify if they match.
     *
     * @param password The original password.
     * @param confirmPassword The password to be compared with the original password.
     * @return True if the passwords match, false otherwise.
     */
    fun verifyPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    /**
     * Verifies if the provided email address is valid and belongs to specific domains.
     *
     * @param email The email address to be verified.
     * @return True if the email address is valid and belongs to specific domains, false otherwise.
     */
    fun isValidEmail(email: String): Boolean {
        // Define the list of allowed email domains
        val allowedDomains = listOf("gmail.com", "icloud.com", "pherus.org")
        // Check if the email address is valid and belongs to one of the allowed domains
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                allowedDomains.any { domain -> email.endsWith("@$domain") }
    }
}