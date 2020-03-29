package io;

/**
 * Represents a string slicer.
 */
public class StringSlicer {

    /**
     * Slice start.
     * @param s String.
     * @param startIndex Start.
     * @return Sliced String.
     */
    public static String sliceStart(String s, int startIndex) {
        if (startIndex < 0) {
            startIndex = s.length() + startIndex;
        }
        return s.substring(startIndex);
    }

    /**
     * Slice end.
     * @param s String.
     * @param endIndex End.
     * @return Sliced String.
     */
    public static String sliceEnd(String s, int endIndex) {
        if (endIndex < 0) {
            endIndex = s.length() + endIndex;
        }
        return s.substring(0, endIndex);
    }

    /**
     * Slice range.
     * @param s String.
     * @param startIndex Start.
     * @param endIndex End.
     * @return Sliced String.
     */
    public static String sliceRange(String s, int startIndex, int endIndex) {
        if (startIndex < 0) {
            startIndex = s.length() + startIndex;
        }
        if (endIndex < 0) {
            endIndex = s.length() + endIndex;
        }
        return s.substring(startIndex, endIndex);
    }
}
