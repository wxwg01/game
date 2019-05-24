package com.game.base.vo;

public class GameCodes {

    private static final int SUCCESS_CODE = 200;

    /** success code*/
    public static GameCode SUCCESS = new GameCode(SUCCESS_CODE, "success");

    /** request error */
    public static GameCode REQUEST_ERROR = new GameCode(400, "request error.");

    /** request auth error */
    public static GameCode REQUEST_AUTH_ERROR = new GameCode(401, "request auth error.");

    /** request forbidden */
    public static GameCode REQUEST_FORBIDDEN = new GameCode(403, "request forbidden.");

    /** request service not found */
    public static GameCode REQUEST_SERVICE_NOT_FOUND = new GameCode(404, "service not found.");

    /** request high frequent */
    public static GameCode REQUEST_HIGH_FREQUENT = new GameCode(429, "too many requests.");

    //    /** request version is too low */
    //    public static GameCode REQUEST_VERSION_LOW = new GameCode(450, "request protocol version is too low.");

    /** request param error */
    public static GameCode REQUEST_PARAM_ERROR = new GameCode(460, "request parameter error.");

    /** server error */
    public static GameCode SERVER_ERROR = new GameCode(500, "server error.");

    /** server not available */
    public static GameCode SERVER_NOT_AVAILABLE = new GameCode(503, "server not available.");

    /**
     * check the code means success
     * @see GameCodes#SUCCESS
     */
    public static boolean isSuccess(int code) {
        return SUCCESS_CODE == code;
    }
}
