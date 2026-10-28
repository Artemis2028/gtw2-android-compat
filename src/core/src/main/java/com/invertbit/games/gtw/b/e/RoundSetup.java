package com.invertbit.games.gtw.b.e;

/**
 * Deploys a new round when the player presses Start (was inline in the c/Cl click handler):
 * AI alliances and arsenal, then every side's cities, silos, bombers and submarines on the map.
 * Split out so JVM tests can run the real launch path for every side pairing without GL.
 */
public final class RoundSetup {
    private RoundSetup() {
    }

    public static void deploy(com.invertbit.games.gtw.a.Cg cgB, final com.invertbit.games.gtw.b.d.Cg battleMap) {
        deploy(cgB, new com.invertbit.games.gtw.b.e.Ce.Grid() {
            @Override
            public java.util.List<com.badlogic.gdx.math.Cl> cells(com.invertbit.games.gtw.b.Cf.Ib region) {
                return battleMap.a(region);
            }
        });
    }

    public static void deploy(com.invertbit.games.gtw.a.Cg cgB, com.invertbit.games.gtw.b.e.Ce.Grid grid) {
        cgB.d = new java.util.ArrayList();
        if (com.invertbit.games.gtw.b.e.Ca.a == null) {
            com.invertbit.games.gtw.b.e.Ca.a = new com.invertbit.games.gtw.b.e.Ca();
        }
        com.invertbit.games.gtw.b.e.Ca ca3 = com.invertbit.games.gtw.b.e.Ca.a;
        ca3.a(cgB.c, cgB.b());
        ca3.a(cgB.b());
        if (cgB.a().i.a()) {
            ca3.a(cgB.a());
            cgB.d.add("Your arsenal has been automatically deployed.");
        }
        com.invertbit.games.gtw.b.Cf sea = com.invertbit.games.gtw.b.a.Ca.a().b().get("submarine");
        if (sea == null || sea.i == null) {
            throw new java.lang.IllegalStateException("world.svg did not load (no submarine grid); see the SVG parse error earlier in the log");
        }
        java.util.List<com.badlogic.gdx.math.Cl> list = sea.i;
        java.util.Collections.shuffle(list);
        java.util.Iterator<com.badlogic.gdx.math.Cl> it = list.iterator();
        for (com.invertbit.games.gtw.a.Ci ci : cgB.b) {
            boolean zEquals = cgB.a().equals(ci);
            com.invertbit.games.gtw.b.e.Ce.a(ci, grid, zEquals);
            com.invertbit.games.gtw.a.Cj cj = ci.i.i;
            com.invertbit.games.gtw.b.e.Ce.a(5, cj.a / 5, ci, it, zEquals);
            com.invertbit.games.gtw.b.e.Ce.a(10, cj.b / 10, ci, it, zEquals);
            com.invertbit.games.gtw.b.e.Ce.a(15, cj.c / 15, ci, it, zEquals);
        }
        cgB.d();
    }
}
