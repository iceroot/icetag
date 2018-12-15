package com.icexxx.icetag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class IcePageTag extends SimpleTagSupport {
    private Integer count;// 总记录数
    private Integer size;// 每页的条数
    private Integer current;// 当前页,从1开始
    private String url;// 请求路径
    private String id;// dom id
    private String _class;// dom class
    private String style;// dom style
    private String target;// dom target
    private String firstName;
    private String prevName;
    private String nextName;
    private String lastName;
    private Boolean showFirstAndLast;
    private Boolean showPrevAndNext;
    private Integer[] rainbow;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassValue() {
        return _class;
    }

    public void setClass(String _class) {
        this._class = _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public void setClazz(String _class) {
        this._class = _class;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPrevName() {
        return prevName;
    }

    public void setPrevName(String prevName) {
        this.prevName = prevName;
    }

    public String getNextName() {
        return nextName;
    }

    public void setNextName(String nextName) {
        this.nextName = nextName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getShowFirstAndLast() {
        return showFirstAndLast;
    }

    public void setShowFirstAndLast(Boolean showFirstAndLast) {
        this.showFirstAndLast = showFirstAndLast;
    }

    public Boolean getShowPrevAndNext() {
        return showPrevAndNext;
    }

    public void setShowPrevAndNext(Boolean showPrevAndNext) {
        this.showPrevAndNext = showPrevAndNext;
    }

    public Integer[] getRainbow() {
        return rainbow;
    }

    public void setRainbow(Integer[] rainbow) {
        this.rainbow = rainbow;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        String value = init();
        try {
            out.write(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.doTag();
    }

    private String init() {
        if (size == null) {
            size = 10;
        }
        if (current == null || current <= 0) {
            current = 1;
        }
        if (id == null || "".equals(id)) {
            id = "ice-page-wrap";
        }
        if (showFirstAndLast == null) {
            showFirstAndLast = true;
        }
        if (showPrevAndNext == null) {
            showPrevAndNext = false;
        }
        if (firstName == null || "".equals(firstName)) {
            firstName = "首页";
        }
        if (prevName == null || "".equals(prevName)) {
            prevName = "上一页";
        }
        if (nextName == null || "".equals(nextName)) {
            nextName = "下一页";
        }
        if (lastName == null || "".equals(lastName)) {
            lastName = "尾页";
        }
        if (!url.contains("?")) {
            url += "?currentPage={{currentPage}}";
        }
        int lastPageCount = count % size;
        int pageCount = count / size;
        if (lastPageCount > 0) {
            pageCount++;
        }
        if (current > pageCount) {
            current = pageCount;
        }
        StringBuilder html = new StringBuilder();
        html.append("<div");
        if (id != null && !"".equals(id)) {
            html.append(" id=\"");
            html.append(id);
            html.append("\"");
        }
        html.append(">");
        if (showFirstAndLast) {
            html.append(pageSpan(firstName, 1, false, "ice-page ice-first"));
        }
        if (showPrevAndNext) {
            if (current != 1) {
                html.append(pageSpan(prevName, current - 1, false, "ice-page ice-prev"));
            }
        }
        for (int i = 0; i < rainbow.length; i++) {
            Integer itemPage = rainbow[i];
            String name = itemPage + "";
            boolean showHover = false;
            if (current.equals(itemPage)) {
                showHover = true;
            }
            String pageSpan = pageSpan(name, itemPage, showHover, "ice-page ice-text");
            html.append(pageSpan);
        }
        if (showPrevAndNext) {
            if (current != pageCount) {
                html.append(pageSpan(nextName, current + 1, false, "ice-page ice-next"));
            }
        }
        if (showFirstAndLast) {
            html.append(pageSpan(lastName, pageCount, false, "ice-page ice-last"));
        }
        html.append("</div>");
        return html.toString();
    }

    private String pageSpan(String name, Integer itemPage, boolean showHover, String className) {
        String innerUrl = url.replace("{{currentPage}}", itemPage + "");
        String newLine = "\r\n";
        StringBuilder html = new StringBuilder();
        html.append("<a ");
        if (_class != null && !"".equals(_class)) {
            html.append("class=\"");
            html.append(className);
            html.append(" ");
            html.append(_class);
            if (showHover) {
                html.append(" hover");
            }
            html.append("\" ");
        } else {
            html.append("class=\"");
            html.append(className);
            if (showHover) {
                html.append(" hover");
            }
            html.append("\" ");
        }
        if (style != null && !"".equals(style)) {
            html.append("style=\"");
            html.append(style);
            html.append("\" ");
        }

        if (target != null && !"".equals(target)) {
            html.append("target=\"");
            html.append(target);
            html.append("\" ");
        }
        html.append("href=\"");
        html.append(innerUrl);
        html.append("\">");
        html.append(name);
        html.append("</a>");
        html.append(newLine);
        return html.toString();
    }

}
