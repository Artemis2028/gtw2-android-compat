package com.invertbit.games.gtw.map;

import com.badlogic.gdx.math.Vector3;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

/**
 * The game's own SVG handler, renamed. It understands {@code path} (M/m/L/l plus a
 * skipped Z/z closepath), {@code rect} (grid vs label) and the first {@code <g>}.
 * Transforms on elements are ignored, matching the original 1.5 parser.
 */
public final class SvgParser {

    private SvgParser() {}

    public static SvgDocument parse(InputStream in) throws Exception {
        Handler handler = new Handler();
        SAXParserFactory.newInstance().newSAXParser().parse(in, handler);
        return handler.document;
    }

    static final class Handler extends DefaultHandler {
        SvgDocument document;
        String currentTag;

        @Override
        public void characters(char[] ch, int start, int length) {
            if (!"title".equals(currentTag) || document == null || document.layer == null) {
                return;
            }
            List<SvgPath> paths = document.layer.paths();
            if (paths.isEmpty()) {
                return;
            }
            SvgPath last = paths.get(paths.size() - 1);
            String text = new String(ch, start, length);
            if (text.trim().length() <= 0) {
                return;
            }
            last.name = text;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            currentTag = qName;
            if ("svg".equals(qName)) {
                document = new SvgDocument();
                if (attributes == null) {
                    return;
                }
                String w = attributes.getValue("width");
                if (w != null) {
                    document.width = Float.parseFloat(w);
                }
                // Original jadx of this branch inverted the null check (returned when height
                // was present). Width is handled correctly; height is parsed the same way.
                String h = attributes.getValue("height");
                if (h != null) {
                    document.height = Float.parseFloat(h);
                }
                return;
            }
            if ("g".equals(qName)) {
                if (document != null && document.layer == null) {
                    document.layer = new SvgLayer();
                }
                return;
            }
            if (document == null || document.layer == null || attributes == null) {
                return;
            }
            if ("path".equals(qName)) {
                parsePath(attributes);
            } else if ("rect".equals(qName)) {
                parseRect(attributes, qName);
            }
        }

        private void parsePath(Attributes attributes) {
            String d = attributes.getValue("d");
            if (d == null) {
                return;
            }
            SvgPath path = new SvgPath();
            path.name = attributes.getValue("onclick");
            Vector3 cursor = new Vector3();
            String command = null;
            String[] tokens = d.split(" ");
            for (int i = 0; i < tokens.length; i++) {
                String token = tokens[i];
                if ("z".equalsIgnoreCase(token)) {
                    continue;
                }
                boolean isCommand = "m".equalsIgnoreCase(token) || "l".equalsIgnoreCase(token);
                if (isCommand) {
                    command = token;
                    continue;
                }
                String[] xy = token.split(",");
                Vector3 point;
                if ("m".equals(command) || "l".equals(command)) {
                    point = new Vector3(cursor.x + Float.parseFloat(xy[0]), 0.0f,
                            Float.parseFloat(xy[1]) + cursor.z);
                } else if ("M".equals(command) || "L".equals(command)) {
                    point = new Vector3(Float.parseFloat(xy[0]), 0.0f, Float.parseFloat(xy[1]));
                } else {
                    throw new IllegalArgumentException("Wrong directive " + command);
                }
                cursor.x = point.x;
                cursor.z = point.z;
                path.points().add(point);
            }
            document.layer.paths().add(path);
        }

        private void parseRect(Attributes attributes, String qName) {
            String onclick = attributes.getValue("onclick");
            if (onclick == null) {
                throw new IllegalArgumentException(qName);
            }
            float w = Float.parseFloat(attributes.getValue("width"));
            float h = Float.parseFloat(attributes.getValue("height"));
            Vector3 origin = new Vector3(
                    Float.parseFloat(attributes.getValue("x")),
                    0.0f,
                    Float.parseFloat(attributes.getValue("y")));
            String hover = attributes.getValue("onmouseover");
            SvgRect rect = new SvgRect(origin, w, h);
            if (hover == null || hover.isEmpty()) {
                if (document.layer.grids.containsKey(onclick)) {
                    document.layer.grids.get(onclick).add(rect);
                } else {
                    List<SvgRect> list = new ArrayList<SvgRect>();
                    list.add(rect);
                    document.layer.grids.put(onclick, list);
                }
            } else if (!document.layer.labels.containsKey(onclick)) {
                document.layer.labels.put(onclick, rect);
            }
        }
    }
}
