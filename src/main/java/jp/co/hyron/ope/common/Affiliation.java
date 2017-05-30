package jp.co.hyron.ope.common;

import lombok.Getter;

/**
 * @author li_x
 */
@Getter
public enum Affiliation {

    JP("日本海隆"), SH("上海"), HZ("華鐘"), BJ("北京"), JS("江蘇"), KS("昆山"), WZ("外注"), NO("未選択");

    private String text;

    private Affiliation(String text) {
        this.text = text;
    }

}