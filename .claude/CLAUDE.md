# 2026 Programming Bootcamp - Claude Code Instructions

Robot code repository for Team 3966 Gryphon Command's six-session programming bootcamp. Students open this repository in GitHub Codespaces, work on `yourname/...` branches, and open pull requests to a protected `main`. The lessons, agendas, and conventions live in the companion site repository `cmlccie/bootstrap` (published at <https://cmlccie.github.io/bootstrap/>). Read its `.claude/CLAUDE.md` for the course goals and outline before designing exercises.

## Facts about this project

- WPILib **Romi Command Bot** template, Java 17, WPILib 2026.x, desktop support enabled. The mentor generates the project with the WPILib VS Code extension. Do not hand-write project scaffolding; extend the generated project.
- Target robot is one **Romi**. Romi code runs on a laptop in simulation mode and talks to the Romi at `10.0.0.2` over its Wi-Fi. Codespaces can build and run unit tests only.
- Romi onboard I/O (`OnBoardIO`): green, yellow, and red LEDs; buttons A, B, C. Yellow is output-only. Green and red share pins with buttons B and C and are configured as input or output. Two wheel encoders (1440 counts per revolution) and a gyro are on the control board.
- CI runs `./gradlew build` on every non-main push and on pull requests. Branch protection requires it.

## Exercise design rules

- Every exercise is real robot code that can deploy to the Romi, even when the session only runs unit tests.
- Teach Java through robot behavior. Sessions 2-3 use the LEDs and buttons so nothing needs to move. Sessions 4-6 add the drivetrain, simulation, and the Romi.
- Keep code as simple as possible and only as sophisticated as necessary. Minimal error handling. Comments explain concepts, not obvious lines.
- Every exercise has a unit test the student can run with `./gradlew test` and see pass. Prefer testing plain logic in small classes over testing hardware wrappers.
- Constants go in `Constants.java`. Hardware access goes in subsystems. Behavior goes in commands.

## Conventions

- Branches: `yourname/what-it-does`. Commit messages: one short present-tense line saying what the change does.
- Run `./gradlew build` before pushing. Keep it green.
- Never include AI model identifiers in commits, pull requests, or code.
