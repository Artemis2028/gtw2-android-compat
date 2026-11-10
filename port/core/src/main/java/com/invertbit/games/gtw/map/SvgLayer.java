package com.invertbit.games.gtw.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contents of the first {@code <g>} in the SVG: named outlines, label rects
 * (rect + onmouseover) and unit-grid rects (rect without onmouseover), keyed by
 * the {@code onclick} name — the same three maps the original parser filled.
 */
public final class SvgLayer {
    public final Map<String, List<SvgRect>> grids = new HashMap<String, List<SvgRect>>();
    public final Map<String, SvgRect> labels = new HashMap<String, SvgRect>();
    private List<SvgPath> paths;

    public List<SvgPath> paths() {
        if (paths == null) {
            paths = new ArrayList<SvgPath>();
        }
        return paths;
    }
}
