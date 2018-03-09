package com.icexxx.icetag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class IceIfTag extends BodyTagSupport {
    private static final long serialVersionUID = -3634206243040714045L;
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
            IceIfTagValue.put(this.pageContext.getPage() + "", _if);
            IceTestTagValue.put(this.pageContext.getPage() + "", null);
            if (_if) {
                return BodyTagSupport.EVAL_BODY_INCLUDE;
            } else {
                return BodyTagSupport.SKIP_BODY;
            }
        } else if (test != null) {
            IceTestTagValue.put(this.pageContext.getPage() + "", test);
            IceIfTagValue.put(this.pageContext.getPage() + "", null);
            if (test) {
                return BodyTagSupport.EVAL_BODY_INCLUDE;
            } else {
                return BodyTagSupport.SKIP_BODY;
            }
        } else {
            throw new RuntimeException("if标签必须有if属性或test属性");
        }
    }
}
