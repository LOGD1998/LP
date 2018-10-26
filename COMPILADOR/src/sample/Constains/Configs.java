package sample.Constains;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configs {
    public static final String Username="";
    public static final String[] KEYWORDS = new String[] {
            "crear","nuevo","insertar","color","tiempo","loop","cambio","seleccionar","pista","cortar","boton","presionado","declarar","comp","audio","direccion"
            ,"launch","ruta","añadir","desplazar","en","clic","volumen","cambiar","secuencia","escena","izquierda","derecha","arriba","abajo"
    };

    public static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    public static final String PAREN_PATTERN = "\\(|\\)";
    public static final String BRACE_PATTERN = "\\{|\\}";
    public static final String BRACKET_PATTERN = "\\[|\\]";
    public static final String SEMICOLON_PATTERN = "\\;";
    public static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    public static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    public static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public static final String sampleCode = String.join("\n", new String[] {
            "declarar color x = 131313",
            "declarar comp fasdasda = 22",
            "declarar nuevo boton b1",
            "seleccionar ruta audio variable = G:\\COMPILADOR\\src\\himnodeoctavia.mp3",
            "insertar audio boton nomboton = audiotal",
            "crear loop boton wea = comp 30",
            "crear desplazar direccion derecha en clic boton fasda"
    });

    public static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public static String [] EXPRESIONES ={
            "declarar[\\s]color[\\s]\\w+[\\s][=][\\s][A-F0-9]{6}",
            "insertar[\\s]color[\\s][\\w]+[\\s]=[\\s][\\w]+",
            "declarar[\\s]comp[\\s]\\w+[\\s][=][\\s][\\d]+",
            "declarar[\\s]nuevo[\\s]boton[\\s]\\w+",
            "seleccionar[\\s]ruta[\\s]audio[\\s]\\w+[\\s]=[\\s][\\w]\\:[\\a-z0-9]+.mp3",
            "insertar[\\s]audio[\\s]boton[\\s]\\w+[\\s]=[\\s]\\w+",
            "crear[\\s]loop[\\s]boton[\\s]\\w+[\\s]=[\\s]comp[\\s][0-9]+",
            "crear[\\s]desplazar[\\s]direccion[\\s]\\w+[\\s]en[\\s]clic[\\s]boton[\\s]\\w+"
    };
}

//([\s]+)?[)]([\s]+)?}