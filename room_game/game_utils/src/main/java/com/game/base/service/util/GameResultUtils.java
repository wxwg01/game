package com.game.base.service.util;

import com.game.base.service.vo.GameCode;
import com.game.base.service.vo.GameCodes;
import com.game.base.service.vo.GameResult;

public class GameResultUtils {
    public GameResultUtils() {
    }

    public static <T> GameResult<T> buildResult(GameCode code) {
        GameResult<T> result = new GameResult(code);
        return result;
    }

    public static <T> GameResult<T> buildResult(GameCode code, T data) {
        GameResult<T> result = new GameResult(code, data);
        return result;
    }

    public static <T> GameResult<T> buildResult(GameCode code, String message, T data) {
        GameResult<T> result = new GameResult(code.getCode(), message, data);
        return result;
    }

    public static <T> GameResult<T> buildSuccessResult() {
        return buildResult(GameCodes.SUCCESS);
    }

    public static <T> GameResult<T> buildSuccessResult(T data) {
        return buildResult(GameCodes.SUCCESS, data);
    }

    public static <T> GameResult<T> buildSuccessResult(String message, T data) {
        return buildResult(GameCodes.SUCCESS, message, data);
    }

    public static <T> GameResult<T> illegalArguments() {
        return buildResult(GameCodes.REQUEST_PARAM_ERROR, null);
    }

    public static <T> GameResult<T> illegalArguments(String message) {
        return buildResult(GameCodes.REQUEST_PARAM_ERROR, message, null);
    }

    public static <T> GameResult<T> systemError(String message) {
        return buildResult(GameCodes.SERVER_ERROR, message, null);
    }

    public static <T> GameResult<T> systemError(T data, String message) {
        return buildResult(GameCodes.SERVER_ERROR, message, data);
    }

    public static <T> GameResult<T> forbiddenError() {
        return buildResult(GameCodes.REQUEST_FORBIDDEN, "无权限操作", null);
    }

    public static <T> GameResult<T> requestError(String message) {
        return buildResult(GameCodes.REQUEST_ERROR, message, null);
    }
}
