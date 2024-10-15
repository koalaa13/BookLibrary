package util;

public class HttpUtil {
    public static String buildDownloadLink(String fileUUID, boolean moderator) {
        String suffix = moderator ?
                "moderator" :
                "reader";
        return fileUUID == null ?
                null :
                "http://" + HttpConstants.APP_DOMAIN + "/files/" + suffix + "/download/" + fileUUID;
    }
}
