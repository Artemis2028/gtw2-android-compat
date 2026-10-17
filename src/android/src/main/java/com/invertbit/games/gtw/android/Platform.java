package com.invertbit.games.gtw.android;

/** Platform callbacks (originally com.invertbit.games.gtw.android.a). The ad callback is a no-op. */
final class Platform extends com.invertbit.games.a.Ca {
    private final android.app.Activity activity;

    Platform(android.app.Activity activity) {
        this.activity = activity;
    }

    private void open(String url) {
        activity.startActivity(new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse(url)));
    }

    @Override public final void a() { open("http://invertbit.blogspot.com/"); }
    @Override public final void b() { open("https://play.google.com/store/apps/developer?id=InvertBit"); }
    @Override public final void c() { open("https://play.google.com/store/apps/details?id=com.invertbit.games.gtw.android"); }
    @Override public final void a(int adId) { /* ads removed */ }
}
