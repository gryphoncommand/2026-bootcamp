# 2026 Programming Bootcamp

Robot code for Team 3966 Gryphon Command's 2026 programming bootcamp. This is the repository you open in a GitHub Codespace, write code in, and push your branches to. The lessons live on the bootcamp site: <https://cmlccie.github.io/bootstrap/>.

The robot is a **Romi**. Code you write here runs in unit tests in your Codespace, in simulation on a programming laptop, and finally on the Romi itself.

## The routine

Every session starts and ends the same way. It becomes muscle memory.

1. **Open your Codespace** from [github.com/codespaces](https://github.com/codespaces) (or create one from the green **Code** button the first time).
2. **Refresh `main`.** Switch to `main` and pull, so you start from the latest code. `main` moves: after every session a mentor merges that session's finished exercise into it, so the next session starts from a working robot for everyone.
3. **Create a branch** named `yourname/session-N`, for example `yourname/session-2`. Never work on `main`. It is protected.
4. **Write your code.**
5. **Test your code.** In the terminal:

    ```bash
    ./gradlew test
    ```

6. **Commit** from the Source Control view with a one-line message that says what the change does.
7. **Push your branch** with **Publish Branch** (first time) or **Sync Changes**.
8. **Stop your Codespace** when you are done for the day. It uses your personal free quota.

A mentor merges finished work. Pull requests and code review come after the bootcamp.

Your own branch keeps your work forever. You do not need it next session: `main` already has the exercise, done the reference way.

## Layout

```text
.devcontainer/      Recipe for the Codespace (Java 17, Gradle, VS Code extensions)
.github/            CI workflow
src/main/java/      Robot program (frc.robot)
src/test/java/      Unit tests
team/               One file per programmer (Session 1)
vendordeps/         WPILib vendor libraries
build.gradle        Gradle build, WPILib GradleRIO
logs/               Robot log files (.wpilog) written when the program runs; not committed
```

## Useful commands

```bash
./gradlew test           # run the unit tests
./gradlew build          # compile everything and run all tests (what CI runs)
./gradlew simulateJava   # run the robot program on this computer
```

`simulateJava` behaves differently depending on where you run it:

- **In a Codespace** it runs headless. No window opens. The robot program starts, stays in Disabled mode, and prints its log messages to the terminal. Press Ctrl+C to stop it.
- **On a programming laptop** it opens the WPILib Simulation GUI, which lets you pick a mode, plug in a controller, and (when connected to the Romi's Wi-Fi) drive the real robot. Add `-Pheadless` to get the Codespace behavior on a laptop.

## Tests that are waiting for you

Some test classes on `main` are marked `@Disabled("Session N: ...")`. They describe code you have not written yet. When a session tells you to, delete that line, watch the tests fail, and write the code that makes them pass. Until then, `./gradlew test` skips them, so `main` stays green.

## Solutions

Completed exercises live on branches named `solution/session-N`. Use them after a session to compare with your own work. The challenges at the end of each session have no published solution. Solve them yourself.

## Mentor setup notes

- **Project generation.** Use the WPILib VS Code extension: *Create a new project* → Template → Java → Romi → **Romi Command Bot**, team number 3966, package `frc.robot`, **Enable Desktop Support** checked.
- **Branch protection on `main`.** Require the `build` status check and block force pushes.
- **`main` moves during the bootcamp.** The tag `bootcamp-start` marks the commit `main` starts from. Before Session N (for N from 3 on), merge `solution/session-(N-1)` into `main` with a pull request so students who refresh `main` get the previous exercise done. Before Capstone 1, merge `solution/session-5`; before Capstone 2, merge `solution/capstone-1`. Never merge a `*-challenge` branch.
- **Resetting for the next bootcamp.** Temporarily allow force pushes in the branch protection rule, then `git push --force origin bootcamp-start:main`, then turn the protection back on. Students' branches from the previous run can be deleted or left; they do not affect `main`.
- **Solution branches.** `solution/session-2` through `solution/session-5`, `solution/capstone-1`, and `solution/capstone-2` chain in that order, each based on the one before. `solution/session-N-challenge` and `solution/capstone-1-challenge` branch off their session's solution and hold the challenge reference solutions. They are CI-verified on every push. When an exercise changes, rebuild the chain from that session on.
- **Codespaces.** Students use their personal free quota. The dev container requests a 2-core machine and runs `./gradlew build` after creation so WPILib dependencies are cached.
- **Romi.** Runs from a programming laptop with WPILib installed, connected to the Romi's Wi-Fi. Codespaces cannot reach it.
