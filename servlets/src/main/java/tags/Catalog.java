package tags;

import lombok.Setter;
import lombok.SneakyThrows;
import model.Gun;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Collection;

public class Catalog extends TagSupport {

    @Setter
    private Collection<Gun> guns;

    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        pageContext.getOut().print(getGunList(guns));

        return SKIP_BODY;
    }

    public static String getGunList(Collection<Gun> guns) throws IOException {
        StringBuffer out = new StringBuffer();
        for (Gun gun: guns)
            out.append("<tr><td><a href=\"/buy/?id=")
                    .append(gun.getId())
                    .append("\">")
                    .append(gun.getName())
                    .append("</a></td><td>")
                    .append(gun.getCaliber())
                    .append("</td></tr>");

        return out.toString();
    }
}
