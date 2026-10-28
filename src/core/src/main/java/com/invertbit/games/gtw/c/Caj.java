package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Caj extends com.invertbit.games.gtw.c.Ca implements com.invertbit.games.gtw.c.c.Ca {
    com.badlogic.gdx.graphics.a.Cf j;
    com.invertbit.games.gtw.d.Ca k;
    com.invertbit.games.gtw.b.d.Cl l;
    com.invertbit.games.gtw.b.d.Ca m;
    com.invertbit.games.gtw.b.d.Ck n;
    com.badlogic.gdx.graphics.g2d.Ch o;
    com.badlogic.gdx.Cj p;
    com.badlogic.gdx.d.Ca q;
    long r;
    com.invertbit.games.gtw.a.Cg s;
    com.badlogic.gdx.f.a.Ci t;
    /** HUD stage with the Pause button (drawn while running); {@link #t} holds Resume / Quit (drawn while paused). */
    com.badlogic.gdx.f.a.Ci u;
    private com.invertbit.games.gtw.c.BattlePause v;

    public Caj(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        this.k = com.invertbit.games.gtw.d.Ca.a();
        this.o = new com.badlogic.gdx.graphics.g2d.Ch();
        com.badlogic.gdx.math.Matrix4 matrix4 = new com.badlogic.gdx.math.Matrix4();
        new com.badlogic.gdx.math.Cl(1.0f, 0.0f, 0.0f);
        matrix4.a();
        this.o.b(matrix4);
        this.j = new com.badlogic.gdx.graphics.a.Cf((byte) 0);
        this.n = new com.invertbit.games.gtw.b.d.Ck();
        this.t = new com.badlogic.gdx.f.a.Ci();
        this.t.b = new com.badlogic.gdx.utils.c.Cb(c, d);
        this.u = new com.badlogic.gdx.f.a.Ci();
        this.u.b = new com.badlogic.gdx.utils.c.Cb(c, d);
    }

    /** The one pause state behind the overlay, the frozen sim, Back and the Pause button. */
    public final com.invertbit.games.gtw.c.BattlePause pause() {
        if (this.v == null) {
            this.v = new com.invertbit.games.gtw.c.BattlePause();
        }
        return this.v;
    }

    /** The one battle stage drawn this frame: Resume / Quit while paused, the Pause button otherwise. */
    final com.badlogic.gdx.f.a.Ci drawnStage() {
        return pause().overlayVisible() ? this.t : this.u;
    }

    /** Battle input, front first: the visible stage's buttons, then map gestures, then Back. */
    private com.badlogic.gdx.Cj input() {
        com.badlogic.gdx.Cj cj = new com.badlogic.gdx.Cj();
        cj.a(new com.invertbit.games.gtw.c.PauseGatedInput(this.u, this, false));
        cj.a(new com.invertbit.games.gtw.c.PauseGatedInput(this.t, this, true));
        this.q = new com.badlogic.gdx.d.Ca(new com.invertbit.games.gtw.c.c.Cb(this));
        cj.a(this.q);
        cj.a(new com.invertbit.games.e.a.Ca(this));
        return cj;
    }

    private void a(com.invertbit.games.gtw.a.Cg cg) {
        this.l = new com.invertbit.games.gtw.b.d.Cl(cg.a);
        this.m = new com.invertbit.games.gtw.b.d.Ca();
        for (com.invertbit.games.gtw.a.Ci ci : cg.b) {
            for (com.invertbit.games.gtw.b.Ca ca : ci.d) {
                if (ca instanceof com.invertbit.games.gtw.b.Cf) {
                    ((com.invertbit.games.gtw.b.Cf) ca).c = ci.e;
                }
            }
            if (ci instanceof com.invertbit.games.gtw.a.Ca) {
                this.m.a((com.invertbit.games.gtw.a.Ca) ci);
            } else if (ci instanceof com.invertbit.games.gtw.a.Cf) {
                this.l.a(ci);
            }
            this.l.a(ci.d);
        }
        this.l.a(this.n);
        this.l.a(this.m);
    }

    /** Builds the Pause button (stage u, shown while running) and the Resume / Quit overlay (stage t, shown while paused). */
    private void e() {
        this.u.d();
        com.badlogic.gdx.f.a.a.Ce hud = new com.badlogic.gdx.f.a.a.Ce();
        hud.J = true;
        com.invertbit.games.gtw.c.a.Cc pauseButton = new com.invertbit.games.gtw.c.a.Cc(160, 100, "Pause",
                com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s30.h);
        pauseButton.a(new com.badlogic.gdx.f.a.Cg() {
            @Override public final boolean a() {
                com.invertbit.games.gtw.c.Caj.this.pauseBattle();
                return true;
            }
        });
        hud.c(pauseButton).m().l().i().e(20.0f).g(20.0f);
        this.u.a(hud);

        this.t.d();
        com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
        ce.J = true;
        com.invertbit.games.gtw.c.a.Cc resume = new com.invertbit.games.gtw.c.a.Cc("Resume", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s30.h, new com.badlogic.gdx.graphics.Cb(0.0f, 0.13f, 0.15f, 1.0f));
        resume.a(new com.invertbit.games.gtw.c.Cak(this, resume));
        ce.c(resume).m().h().k();
        ce.i();
        com.invertbit.games.gtw.c.a.Cc quit = new com.invertbit.games.gtw.c.a.Cc("Quit to menu", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s30.h, new com.badlogic.gdx.graphics.Cb(0.0f, 0.13f, 0.15f, 1.0f));
        quit.a(new com.invertbit.games.gtw.c.Cal(this, resume));
        ce.c(quit).m().h().i();
        this.t.a(ce);
    }

    /** Freeze the battle clock, AI and missiles (Back key, Pause button, or app background). */
    public final void pauseBattle() {
        pause().pause();
        this.i = true;
    }

    /** Resume button: unfreeze with no catch-up for the paused time. */
    public final void resumeBattle() {
        pause().resume(java.lang.System.currentTimeMillis());
        this.i = false;
    }

    /** Quit to menu (button, or Back while paused). The next battle starts unpaused anyway. */
    public final void quitToMenu() {
        pause().quit();
        this.i = false;
        this.e.a(com.invertbit.games.e.Ca.menu, new java.lang.Object[0]);
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        int i;
        super.a(objArr);
        this.s = (com.invertbit.games.gtw.a.Cg) objArr[0];
        for (com.invertbit.games.gtw.a.Ci ci : this.s.b) {
            ci.k = ci.i.e();
        }
        com.invertbit.games.gtw.a.Cg cg = this.s;
        com.invertbit.games.gtw.a.Ce ce = new com.invertbit.games.gtw.a.Ce();
        java.util.Iterator<com.invertbit.games.gtw.a.Ci> it = cg.b.iterator();
        while (it.hasNext()) {
            ce.a(it.next());
        }
        com.invertbit.games.gtw.b.d.Ck ck = this.n;
        ck.b = ce;
        ck.a.d();
        ck.a.a(new com.invertbit.games.gtw.c.a.Cf(ce));
        ce.d = 5.0f;
        if (cg.d == null) {
            cg.d = new java.util.ArrayList();
        }
        java.util.Iterator<java.lang.String> it2 = cg.d.iterator();
        while (it2.hasNext()) {
            ce.a(it2.next());
        }
        a(cg);
        a(false);
        switch (com.invertbit.games.gtw.c.Cam.a[this.s.a().e.ordinal()]) {
            case 1:
                i = -1;
                break;
            case 2:
            case 3:
                i = 1800;
                break;
            default:
                i = 0;
                break;
        }
        this.f.a.a(1440.0f, 10.0f, 960.0f);
        ((com.badlogic.gdx.graphics.Cl) this.f).n = 1.0f;
        com.badlogic.gdx.math.Cl cl = this.f.a;
        cl.a = i + cl.a;
        this.f.a();
        this.r = java.lang.System.currentTimeMillis();
        e();
        // New battle: never inherit a pause from the last match or from the app's background trip.
        pause().start(this.r);
        this.i = false;
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        this.f.a();
        pause().step(java.lang.System.currentTimeMillis(), this.sim);
        this.i = pause().isPaused();
        a(true);
        f();
        a(false);
        f();
        // One state: the overlay shows exactly while the sim is frozen, the Pause button otherwise.
        com.badlogic.gdx.f.a.Ci stage = drawnStage();
        stage.c();
        stage.a();
        if (((com.badlogic.gdx.graphics.Cl) this.f).n <= 0.5f) {
            this.o.a();
            this.l.a(this.o, this.f);
            this.o.c();
        }
        java.util.Iterator<com.invertbit.games.gtw.b.d.Cb> it = this.l.a.values().iterator();
        while (it.hasNext()) {
            it.next().f();
        }
    }

    /** The real battle behind {@link BattlePause#step}: map objects, missiles, DEFCON clock and AI. */
    private final com.invertbit.games.gtw.c.BattlePause.Sim sim = new com.invertbit.games.gtw.c.BattlePause.Sim() {
        @Override public boolean over() {
            return com.invertbit.games.gtw.c.Caj.this.l.a();
        }

        @Override public void gameOver() {
            com.invertbit.games.gtw.c.Caj.this.a(4);
            com.invertbit.games.gtw.c.Caj.this.e.a(com.invertbit.games.e.Ca.gameOver, com.invertbit.games.gtw.c.Caj.this.s);
        }

        @Override public void advance(long ms) {
            com.invertbit.games.gtw.c.Caj.this.l.a(ms);
        }

        @Override public boolean countdownRunning() {
            return com.invertbit.games.gtw.c.Caj.this.n.b.a();
        }

        @Override public void ai() {
            com.invertbit.games.gtw.c.Caj.this.m.a(com.invertbit.games.gtw.c.Caj.this.l);
        }
    };

    private void f() {
        this.j.a(this.f);
        this.l.a(this.j, this.k);
        this.j.c();
    }

    private void a(boolean z) {
        this.l.a(z);
    }

    @Override // com.invertbit.games.gtw.c.Ca, com.badlogic.gdx.Co
    public final void a() {
        this.p = input();
        com.badlogic.gdx.Cf.d.a(this.p);
        com.badlogic.gdx.Cf.d.a(true);
    }

    @Override // com.badlogic.gdx.Co
    public final void a(int i, int i2) {
        this.t.b.a(i, i2);
    }

    @Override // com.badlogic.gdx.Co
    public final void b() {
        if (this.j != null) {
            this.j.b();
        }
        if (this.k != null) {
            this.k.b();
        }
        if (this.l != null) {
            this.l.b();
        }
        if (this.o != null) {
            this.o.b();
        }
    }

    @Override // com.invertbit.games.gtw.c.c.Ca
    public final void a(float f, float f2) {
        if (!pause().isPaused()) {
            float f3 = ((com.badlogic.gdx.graphics.Cl) this.f).n;
            float f4 = ((f * f3) + this.f.a.a) - ((this.f.j * f3) / 2.0f);
            float f5 = ((f2 * f3) + this.f.a.c) - ((f3 * this.f.k) / 2.0f);
            if (f4 > 2880.0f) {
                f4 -= 2880.0f;
            }
            this.l.a(new com.badlogic.gdx.math.Cl(f4, 0.0f, f5));
        }
    }

    @Override // com.invertbit.games.gtw.c.c.Ca
    public final void b(float f, float f2) {
        if (!pause().isPaused()) {
            float f3 = this.f.a.a;
            float f4 = this.f.a.c + ((((com.badlogic.gdx.graphics.Cl) this.f).n * this.f.k) / 2.0f);
            float f5 = this.f.a.c - ((((com.badlogic.gdx.graphics.Cl) this.f).n * this.f.k) / 2.0f);
            if (1440.0f > f3) {
                this.f.a.a = 4320.0f;
            } else if (f3 > 4320.0f) {
                this.f.a.a = 1440.0f;
            }
            if (1920.0f < f4 + f2 || 0.0f > f5 + f2) {
                f2 = 0.0f;
            }
            this.f.a(-f, f2);
        }
    }

    @Override // com.invertbit.games.gtw.c.c.Ca
    public final boolean c(float f, float f2) {
        if (pause().isPaused()) {
            return false;
        }
        float f3 = ((com.badlogic.gdx.graphics.Cl) this.f).n - (((int) (f2 - f)) / 50000.0f);
        ((com.badlogic.gdx.graphics.Cl) this.f).n = f3 <= 1.0f ? f3 < 0.05f ? 0.05f : f3 : 1.0f;
        float f4 = this.f.a.c + ((((com.badlogic.gdx.graphics.Cl) this.f).n * this.f.k) / 2.0f);
        float f5 = this.f.a.c - ((((com.badlogic.gdx.graphics.Cl) this.f).n * this.f.k) / 2.0f);
        if (1920.0f < f4) {
            this.f.a(0.0f, 1920.0f - f4);
        } else if (0.0f > f5) {
            this.f.a(0.0f, -f5);
        }
        return true;
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void d() {
        // Back: same state as the Pause button. Pauses a running battle, quits from a paused one.
        if (pause().back()) {
            quitToMenu();
        } else {
            this.i = true;
        }
    }
}
