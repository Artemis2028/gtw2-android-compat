package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Alliance-marker placement: the pole of inaccessibility of a region's largest
 * landmass (the deepest interior point of its biggest outline ring). Label-rect
 * origins in {@code world.svg} are callout boxes and often sit in the ocean.
 */
public final class RegionMarkers {

    static final float PRECISION = 1.5f;
    private static final float WRAP = 1400f;

    private RegionMarkers() {
    }

    public static Vector3 interiorOfLargest(List<List<Vector3>> rings) {
        if (rings == null || rings.isEmpty()) {
            return null;
        }
        List<Vector3> best = null;
        double bestArea = -1.0;
        for (List<Vector3> ring : rings) {
            for (List<Vector3> part : splitWrap(ring)) {
                if (part.size() < 3) {
                    continue;
                }
                double area = Math.abs(shoelace(part));
                if (area > bestArea) {
                    bestArea = area;
                    best = part;
                }
            }
        }
        if (best == null) {
            return null;
        }
        return polylabel(best, PRECISION);
    }

    public static boolean contains(List<Vector3> ring, float x, float z) {
        List<Vector3> pts = closed(ring);
        int n = pts.size();
        boolean inside = false;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Vector3 a = pts.get(j);
            Vector3 b = pts.get(i);
            if (Math.abs(b.x - a.x) >= WRAP) {
                continue;
            }
            if ((a.z > z) != (b.z > z)
                    && x < (b.x - a.x) * (z - a.z) / (b.z - a.z) + a.x) {
                inside = !inside;
            }
        }
        return inside;
    }

    static List<List<Vector3>> splitWrap(List<Vector3> ring) {
        List<List<Vector3>> parts = new ArrayList<List<Vector3>>();
        if (ring == null || ring.isEmpty()) {
            return parts;
        }
        List<Vector3> cur = new ArrayList<Vector3>();
        Vector3 prev = null;
        for (Vector3 p : ring) {
            if (prev != null && Math.abs(p.x - prev.x) >= WRAP) {
                if (cur.size() >= 3) {
                    parts.add(cur);
                }
                cur = new ArrayList<Vector3>();
            }
            cur.add(p);
            prev = p;
        }
        if (cur.size() >= 3) {
            parts.add(cur);
        }
        if (parts.isEmpty() && ring.size() >= 3) {
            parts.add(new ArrayList<Vector3>(ring));
        }
        return parts;
    }

    static List<Vector3> closed(List<Vector3> ring) {
        int n = ring.size();
        if (n >= 2) {
            Vector3 a = ring.get(0);
            Vector3 b = ring.get(n - 1);
            if (a.x == b.x && a.z == b.z) {
                return ring.subList(0, n - 1);
            }
        }
        return ring;
    }

    static double shoelace(List<Vector3> ring) {
        List<Vector3> pts = closed(ring);
        double acc = 0.0;
        int n = pts.size();
        for (int i = 0; i < n; i++) {
            Vector3 a = pts.get(i);
            Vector3 b = pts.get((i + 1) % n);
            acc += (double) a.x * b.z - (double) b.x * a.z;
        }
        return acc * 0.5;
    }

    static Vector3 centroid(List<Vector3> ring) {
        List<Vector3> pts = closed(ring);
        double cx = 0.0;
        double cz = 0.0;
        double area2 = 0.0;
        int n = pts.size();
        for (int i = 0; i < n; i++) {
            Vector3 a = pts.get(i);
            Vector3 b = pts.get((i + 1) % n);
            double cross = (double) a.x * b.z - (double) b.x * a.z;
            cx += (a.x + b.x) * cross;
            cz += (a.z + b.z) * cross;
            area2 += cross;
        }
        if (Math.abs(area2) < 1e-6) {
            float sx = 0f;
            float sz = 0f;
            for (Vector3 p : pts) {
                sx += p.x;
                sz += p.z;
            }
            return new Vector3(sx / n, 0f, sz / n);
        }
        return new Vector3((float) (cx / (3.0 * area2)), 0f, (float) (cz / (3.0 * area2)));
    }

    static Vector3 polylabel(List<Vector3> ring, float precision) {
        List<Vector3> pts = closed(ring);
        float minX = Float.MAX_VALUE;
        float minZ = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE;
        float maxZ = -Float.MAX_VALUE;
        for (Vector3 p : pts) {
            minX = Math.min(minX, p.x);
            minZ = Math.min(minZ, p.z);
            maxX = Math.max(maxX, p.x);
            maxZ = Math.max(maxZ, p.z);
        }
        float width = maxX - minX;
        float height = maxZ - minZ;
        if (width <= 0f || height <= 0f) {
            return new Vector3((minX + maxX) * 0.5f, 0f, (minZ + maxZ) * 0.5f);
        }
        float cellSize = Math.min(width, height);
        float h = cellSize / 2f;

        PriorityQueue<Cell> queue = new PriorityQueue<Cell>();
        for (float x = minX; x < maxX; x += cellSize) {
            for (float z = minZ; z < maxZ; z += cellSize) {
                queue.add(new Cell(x + h, z + h, h, pts));
            }
        }

        Cell best = new Cell((minX + maxX) * 0.5f, (minZ + maxZ) * 0.5f, 0, pts);
        Vector3 c = centroid(pts);
        Cell centroidCell = new Cell(c.x, c.z, 0, pts);
        if (centroidCell.d > best.d) {
            best = centroidCell;
        }

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            if (cell.d > best.d) {
                best = cell;
            }
            if (cell.max - best.d <= precision) {
                continue;
            }
            float nh = (float) (cell.h / 2.0);
            queue.add(new Cell(cell.x - nh, cell.z - nh, nh, pts));
            queue.add(new Cell(cell.x + nh, cell.z - nh, nh, pts));
            queue.add(new Cell(cell.x - nh, cell.z + nh, nh, pts));
            queue.add(new Cell(cell.x + nh, cell.z + nh, nh, pts));
        }
        return new Vector3((float) best.x, 0f, (float) best.z);
    }

    private static final class Cell implements Comparable<Cell> {
        final double x;
        final double z;
        final double h;
        final double d;
        final double max;

        Cell(double x, double z, double h, List<Vector3> ring) {
            this.x = x;
            this.z = z;
            this.h = h;
            this.d = pointToPolygonDist(x, z, ring);
            this.max = this.d + this.h * Math.sqrt(2.0);
        }

        @Override
        public int compareTo(Cell other) {
            if (other.max != this.max) {
                return other.max > this.max ? 1 : -1;
            }
            return Double.compare(other.d, this.d);
        }
    }

    static double pointToPolygonDist(double x, double z, List<Vector3> ring) {
        boolean inside = contains(ring, (float) x, (float) z);
        double minSq = Double.POSITIVE_INFINITY;
        List<Vector3> pts = closed(ring);
        int n = pts.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Vector3 a = pts.get(j);
            Vector3 b = pts.get(i);
            if (Math.abs(b.x - a.x) >= WRAP) {
                continue;
            }
            minSq = Math.min(minSq, distToSegSq(x, z, a.x, a.z, b.x, b.z));
        }
        double dist = Double.isInfinite(minSq) ? 0.0 : Math.sqrt(minSq);
        return inside ? dist : -dist;
    }

    private static double distToSegSq(double px, double pz,
                                      double ax, double az, double bx, double bz) {
        double dx = bx - ax;
        double dz = bz - az;
        double len2 = dx * dx + dz * dz;
        double t = 0.0;
        if (len2 > 0.0) {
            t = ((px - ax) * dx + (pz - az) * dz) / len2;
            if (t < 0.0) {
                t = 0.0;
            } else if (t > 1.0) {
                t = 1.0;
            }
        }
        double ex = px - (ax + t * dx);
        double ez = pz - (az + t * dz);
        return ex * ex + ez * ez;
    }
}
