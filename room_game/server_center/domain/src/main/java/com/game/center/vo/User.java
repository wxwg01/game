package com.game.center.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 *
 * @date : 2019/5/15
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7219134596815563099L;
    private String name;
    private String nikName;
    private Integer level;
    private Long exp;
    private Long gold;
    private String pic;
}
