package com.arsoft.themovieapppaging.util;


/**
 * Utils: A utility class to store global constants and common helper methods
 * used throughout the application.
 *
 * <p>Why use this class?</p>
 * <ul>
 * <li><b>Centralization:</b> Provides a single, easy-to-find location for values
 * like API keys and base URLs, ensuring consistency across the app.</li>
 * <li><b>Easier Maintenance:</b> If a constant (e.g., API_KEY) changes,
 * you only need to update it in one place.</li>
 * <li><b>Improved Readability:</b> Using `Utils.API_KEY` is clearer than
 * hardcoding the value multiple times.</li>
 * <li><b>Code Reusability:</b> Can host static helper functions that perform
 * common tasks, avoiding code duplication.</li>
 * </ul>
 *
 * <p>NOTE: While useful for organization, hardcoding sensitive information like
 * API keys directly in source code is generally not recommended for production
 * applications due to security concerns. Consider using Gradle build config fields
 * or other secure methods for sensitive data.</p>
 */

public class Utils {
    public static String API_KEY =  "56d5ae75033f3d22fd7a2e86a9150589";
    public static String BASE_URL = "https://api.themoviedb.org/3/";
}
