package controller.util

class Tools {
    static Long extractIdFromPath(String pathInfo) {
        try {
            return Long.parseLong(pathInfo.substring(1))
        } catch (NumberFormatException e) {
            return null
        }
    }
}
