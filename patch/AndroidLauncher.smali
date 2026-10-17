.class public Lcom/invertbit/games/gtw/android/AndroidLauncher;
.super Lcom/badlogic/gdx/backends/android/a;
.source "CompatibilityLauncher"

.method public constructor <init>()V
    .locals 0
    invoke-direct {p0}, Lcom/badlogic/gdx/backends/android/a;-><init>()V
    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 4
    invoke-super {p0, p1}, Lcom/badlogic/gdx/backends/android/a;->onCreate(Landroid/os/Bundle;)V
    const/4 v0, 0x1
    invoke-virtual {p0, v0}, Landroid/app/Activity;->requestWindowFeature(I)Z
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;
    move-result-object v0
    const/16 v1, 0x400
    invoke-virtual {v0, v1, v1}, Landroid/view/Window;->setFlags(II)V
    const/16 v1, 0x800
    invoke-virtual {v0, v1}, Landroid/view/Window;->clearFlags(I)V
    new-instance v0, Lcom/badlogic/gdx/backends/android/e;
    invoke-direct {v0}, Lcom/badlogic/gdx/backends/android/e;-><init>()V
    const/4 v1, 0x0
    iput-boolean v1, v0, Lcom/badlogic/gdx/backends/android/e;->h:Z
    iput-boolean v1, v0, Lcom/badlogic/gdx/backends/android/e;->i:Z
    new-instance v1, Lcom/invertbit/games/gtw/android/a;
    invoke-direct {v1, p0}, Lcom/invertbit/games/gtw/android/a;-><init>(Lcom/invertbit/games/gtw/android/AndroidLauncher;)V
    new-instance v2, Lcom/invertbit/games/gtw/a;
    invoke-direct {v2, v1}, Lcom/invertbit/games/gtw/a;-><init>(Lcom/invertbit/games/a/a;)V
    invoke-virtual {p0, v2, v0}, Lcom/badlogic/gdx/backends/android/a;->a(Lcom/badlogic/gdx/b;Lcom/badlogic/gdx/backends/android/e;)Landroid/view/View;
    move-result-object v3
    invoke-virtual {p0, v3}, Landroid/app/Activity;->setContentView(Landroid/view/View;)V
    return-void
.end method
