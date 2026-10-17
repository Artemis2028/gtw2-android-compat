# CI workflow (pending activation)

`build.yml` here is the GitHub Actions workflow for this repo. It could not be pushed into `.github/workflows/` automatically because the token used lacked GitHub's `workflow` scope.

To activate it, either:
- On github.com: **Add file → Create new file**, name it `.github/workflows/build.yml`, paste the contents of `ci/build.yml`, commit; or
- Locally: `git mv ci/build.yml .github/workflows/build.yml && git commit -m "Enable CI" && git push` with credentials that have the `workflow` scope (`gh auth refresh -s workflow`).
