package com.ailuoku6.golib.Util;

public class HtmlTransformer {

    public static final String START_LIBRARY_NOTIFICATION = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "\n" +
            "<head>\n" +
            "  <meta charset=\"UTF-8\">\n" +
            "  <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width,height=device-height, user-scalable=no,initial-scale=1, minimum-scale=1, maximum-scale=1,target-densitydpi=device-dpi \">\n" +
            "</head>\n" +
            "<style>\n" +
            "\t\n" +
            "</style>\n" +
            "<body>\n";

    public static final String END_LIBRARY_NOTIFICATION = "</body>\n" +
            "<script>\n" +
            "document.getElementsByTagName('body')[0].style.cssText = 'oveflow-x:hidden;padding:0 3px';\n" +
            "\tdocument.getElementsByTagName('td')[0].style.cssText = '';\n" +
            "\tdocument.getElementsByTagName('div')[0].style.cssText = 'margin: 15px 0;font-size: 26px;font-weight: 700;line-height: 1.4;color: #2f2f2f;margin-top:0;'\n" +
            "\tdocument.getElementsByTagName('div')[1].style.cssText ='padding-left:0';\n" +
            "\tdocument.getElementsByTagName('div')[2].style.cssText ='float:left;font-size: 13px;color: #b1b1b1;';\n" +
            "\tdocument.getElementsByTagName('div')[3].style.cssText ='float:left;font-size: 13px;color: #b1b1b1;';\n" +
            "\tdocument.getElementsByTagName('div')[4].style.cssText = 'padding-right:0;clear:both;margin-top:40px;font-size: 16px;font-weight: 400;line-height: 1.7;color: #2f2f2f;'\n" +
            "\tfor(var i=0;i<4;i++){\n" +
            "\t\tdocument.getElementsByTagName('tr')[document.getElementsByTagName('tr').length-1].remove();\n" +
            "\t}\n" +
            "\tvar length = document.getElementsByTagName('img').length;\n" +
            "\tvar width = window.screen.availWidth -20;\n" +
            "\tfor(var i=0;i<length;i++){\n" +
            "\t\tvar src  ='http://www.lib.wust.edu.cn' + document.getElementsByTagName('img')[i].getAttribute('src').slice(2);\n" +
            "\t\tdocument.getElementsByTagName('img')[i].setAttribute('src',src);\n" +
            "\t\tdocument.getElementsByTagName('img')[i].style.maxWidth = width + 'px';\n" +
            "\t}\n" +
            "\tvar p_length = document.getElementsByTagName('p').length;\n" +
            "\tfor(var i=0;i<p_length;i++){\n" +
            "\t\tdocument.getElementsByTagName('p')[i].style.fontSize = '16px';\n" +
            "\t\tdocument.getElementsByTagName('p')[i].style.marginTop = '0';\n" +
            "\t\tdocument.getElementsByTagName('p')[i].style.marginBottom = '10px';\n" +
            "\t}\n" +
            "\t\t\n" +
            "</script>\n" +
            "</html>";

}

