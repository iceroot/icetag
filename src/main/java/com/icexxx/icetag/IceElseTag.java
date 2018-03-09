package com.icexxx.icetag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class IceElseTag extends BodyTagSupport {
    private static final long serialVersionUID = -4977855866404167123L;

    @Override
    public int doStartTag() throws JspException {
        Boolean value = IceIfTagValue.get(this.pageContext.getPage() + "");
        boolean useIf = true;
        if (value == null) {
            value = IceTestTagValue.get(this.pageContext.getPage() + "");
        }
        IceIfTagValue.put(this.pageContext.getPage() + "", null);
        IceTestTagValue.put(this.pageContext.getPage() + "", null);
        if (value != null) {
            if (!value) {
                return BodyTagSupport.EVAL_BODY_INCLUDE;
            } else {
                return BodyTagSupport.SKIP_BODY;
            }
        } else {
            throw new RuntimeException("使用else标签必须先使用if标签");
        }
    }
}
