package com.gupaoedu.vip.mebatis.v2.generator.internal.util;

import com.sun.istack.internal.Nullable;

import java.util.Collection;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/7 10:53
 * @description：
 * @modified By：
 * @version: $
 */
public class StringUtils {
    /**
     * Check whether the given {@code CharSequence} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code CharSequence} is not {@code null}, its length is greater than
     * 0, and it contains at least one non-whitespace character.
     * <p><pre class="code">
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null},
     * its length is greater than 0, and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(@Nullable CharSequence str) {
        return (str != null && str.length() > 0 && containsText(str));
    }

    /**
     * Check whether the given {@code String} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code String} is not {@code null}, its length is greater than 0,
     * and it contains at least one non-whitespace character.
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null}, its
     * length is greater than 0, and it does not contain whitespace only
     * @see #hasText(CharSequence)
     */
    public static boolean hasText(@Nullable String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }
    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check that the given {@code CharSequence} is neither {@code null} nor
     * of length 0.
     * <p>Note: this method returns {@code true} for a {@code CharSequence}
     * that purely consists of whitespace.
     * <p><pre class="code">
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null} and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(@Nullable CharSequence str) {
        return (str != null && str.length() > 0);
    }
    /**
     * Check that the given {@code String} is neither {@code null} nor of length 0.
     * <p>Note: this method returns {@code true} for a {@code String} that
     * purely consists of whitespace.
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null} and has length
     * @see #hasLength(CharSequence)
     * @see #hasText(String)
     */
    public static boolean hasLength(@Nullable String str) {
        return (str != null && !str.isEmpty());
    }
    /**
     * Capitalize a {@code String}, changing the first letter to
     * upper case as per {@link Character#toUpperCase(char)}.
     * No other letters are changed.
     * @param str the {@code String} to capitalize
     * @return the capitalized {@code String}
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    /**
     * Uncapitalize a {@code String}, changing the first letter to
     * lower case as per {@link Character#toLowerCase(char)}.
     * No other letters are changed.
     * @param str the {@code String} to uncapitalize
     * @return the uncapitalized {@code String}
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (!hasLength(str)) {
            return str;
        }

        char baseChar = str.charAt(0);
        char updatedChar;
        if (capitalize) {
            updatedChar = Character.toUpperCase(baseChar);
        }
        else {
            updatedChar = Character.toLowerCase(baseChar);
        }
        if (baseChar == updatedChar) {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[0] = updatedChar;
        return new String(chars, 0, chars.length);
    }

    /**
     * Copy the given {@code Collection} into a {@code String} array.
     * <p>The {@code Collection} must contain {@code String} elements only.
     * @param collection the {@code Collection} to copy
     * @return the {@code String} array
     */
    public static String[] toStringArray(Collection<String> collection) {
        return collection.toArray(new String[collection.size()]);
    }
}