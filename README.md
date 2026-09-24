# 2026 Programming Bootcamp

Robot code for Team 3966 Gryphon Command's 2026 programming bootcamp. This is the repository you open in a GitHub Codespace, write code in, and push your branches to. The lessons live on the bootcamp site: <https://cmlccie.github.io/bootstrap/>.

The robot is a **Romi**. Code you write here runs in unit tests in your Codespace, in simulation on a programming laptop, and finally on the Romi itself.

## How you work in this repository

1. **Open a Codespace.** Green **Code** button, **Codespaces** tab, **Create codespace on main** (or reopen the one you already have at [github.com/codespaces](https://github.com/codespaces)).
2. **Create a branch** named `yourname/what-it-does`, for example `yourname/session-2`. Never work on `main`. It is protected.
3. **Write and test.** Run the tests in the terminal:

    ```bash
    ./gradlew test
    ```

4. **Commit and push** from the Source Control view in VS Code.
5. **Open a pull request** when your work is ready for review (starting in Session 2).
6. **Stop your Codespace** when you are done for the day. It uses your personal free quota.

## Layout

```text
.devcontainer/      Recipe for the Codespace (Java 17, Gradle, VS Code extensions)
.github/            CI workflow and pull request template
src/main/java/      Robot program (frc.robot)
src/test/java/      Unit tests
team/               One file per programmer (Session 1)
vendordeps/         WPILib vendor libraries
build.gradle        Gradle build, WPILib GradleRIO
```

## Useful commands

```bash
./gradlew build          # compile and run all tests
./gradlew test           # run the unit tests
./gradlew simulateJava   # run in simulation (programming laptop only, needs a screen)
```

## Mentor setup notes

- **Project generation.** Use the WPILib VS Code extension: *Create a new project* → Template → Java → Romi → **Romi Command Bot**, team number 3966, package `frc.robot`, **Enable Desktop Support** checked. Generate into a temporary folder and copy the contents into the root of this repository so `gradlew` sits next to this README.
- **Branch protection on `main`.** Require a pull request before merging, require the `build` status check, and block force pushes.
- **Codespaces.** Students use their personal free quota. The dev container requests a 2-core machine and runs `./gradlew build` after creation so WPILib dependencies are cached.
- **Romi.** Runs from a programming laptop with WPILib installed, connected to the Romi's Wi-Fi. Codespaces cannot reach it.
