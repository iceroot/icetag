package com.icexxx.icetag;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class IceForTag extends SimpleTagSupport {
    private Object list;
    private String var;
    private String index;
    private String count;

    @Override
    public void doTag() throws JspException, IOException {
        if (list == null) {
            return;
        }
        if (list instanceof String) {
            if (!"".equals(list)) {
                String str = (String) list;
                if (str.contains(",")) {
                    list = str.split(",");
                } else {
                    list = getJspContext().getAttribute(str);
                    if (list == null) {
                        list = new String[] { str };
                    }
                }
            }
        }
        Collection coll = null;
        if (list instanceof Collection) {
            coll = (Collection) list;
        } else if (list instanceof Map) {
            coll = ((Map) list).entrySet();
        } else if (list.getClass().isArray()) {
            int len = Array.getLength(list);
            coll = new ArrayList();
            for (int i = 0; i < len; i++) {
                coll.add(Array.get(list, i));
            }
        }
        Iterator iterator = coll.iterator();
        int indexNum = 0;
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            getJspContext().setAttribute(var, obj);
            if (index != null && !"".equals(index)) {
                getJspContext().setAttribute(index, indexNum);
            }
            if (count != null && !"".equals(count)) {
                getJspContext().setAttribute(count, indexNum + 1);
            }
            getJspBody().invoke(null);
            indexNum++;
        }
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
