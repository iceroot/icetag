package com.icexxx.icetag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class IceElseIfTag extends BodyTagSupport {
    private static final long serialVersionUID = -8491256499642179430L;
    private Boolean _if;
    private Boolean test;

    public boolean getIf() {
        return _if;
    }

    public void setIf(boolean _if) {
        this._if = _if;
    }

    public boolean getTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public int doStartTag() throws JspException {
        if (_if != null) {
            IceTestTagValue.put(this.pageContext.getPage() + "", null);
            if (_if) {
                IceIfTagValue.put(this.pageContext.getPage() + "", _if);
                return BodyTagSupport.EVAL_BODY_INCLUDE;
            } else {
                return BodyTagSupport.SKIP_BODY;
            }
        } else if (test != null) {
            IceIfTagValue.put(this.pageContext.getPage() + "", null);
            if (test) {
                IceTestTagValue.put(this.pageContext.getPage() + "", test);
                return BodyTagSupport.EVAL_BODY_INCLUDE;
            } else {
                return BodyTagSupport.SKIP_BODY;
            }
        } else {
            throw new RuntimeException("if标签必须有if属性或test属性");
        }
    }
}
