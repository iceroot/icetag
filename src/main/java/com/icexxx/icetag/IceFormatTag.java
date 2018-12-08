package com.icexxx.icetag;

import java.io.IOException;
import java.math.RoundingMode;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class IceFormatTag extends SimpleTagSupport {
    private String precision;
    private String roundingMode;
    private String value;
    // 是否将null置位空字符串
    private Boolean nullToEmpty;
    // 是否使用自然数字格式
    private Boolean natural;

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(String roundingMode) {
        this.roundingMode = roundingMode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getNullToEmpty() {
        return nullToEmpty;
    }

    public void setNullToEmpty(Boolean nullToEmpty) {
        this.nullToEmpty = nullToEmpty;
    }

    public Boolean getNatural() {
        return natural;
    }

    public void setNatural(Boolean natural) {
        this.natural = natural;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        int precisionInt = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        if (precision != null && !"".equals(precision)) {
            try {
                precisionInt = Integer.parseInt(precision);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (this.roundingMode != null && !"".equals(this.roundingMode)) {
            if ("floor".equalsIgnoreCase(this.roundingMode)) {
                roundingMode = RoundingMode.FLOOR;
            } else if ("ceil".equalsIgnoreCase(this.roundingMode)) {
                roundingMode = RoundingMode.CEILING;
            }
        }
        String value = "";
        if (this.nullToEmpty == null) {
            this.nullToEmpty = true;
        }
        if (this.natural == null) {
            this.natural = false;
        }
        if (this.value == null) {
            if (this.nullToEmpty) {
                value = "";
            } else {
                value = null;
            }
        } else if ("".equals(this.value)) {
            value = "";
        } else {
            value = IceFormat.format(this.value, precisionInt, roundingMode);
            if (this.natural) {
                value = IceFormat.natural(value);
            }
        }
        try {
            out.write(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
