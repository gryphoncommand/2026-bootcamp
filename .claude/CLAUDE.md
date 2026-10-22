# 2026 Programming Bootcamp - Claude Code Instructions

Robot code repository for Team 3966 Gryphon Command's programming bootcamp: five sessions plus two capstones. Students open this repository in GitHub Codespaces, work on `yourname/...` branches, and push them. `main` is protected; a mentor merges. The lessons, agendas, and conventions live in the companion site repository `cmlccie/bootstrap` (published at <https://cmlccie.github.io/bootstrap/>). Read its `.claude/CLAUDE.md` for the course goals and outline before designing exercises.

Claude assists mentors in preparing, revising, and running the bootcamp. The operating notes below are the source of truth for how the branches work.

## Facts about this project

- WPILib **Romi Command Bot** template, Java 17, WPILib 2026.x, desktop support enabled. The mentor generates the project with the WPILib VS Code extension. Do not hand-write project scaffolding; extend the generated project.
- Target robot is one **Romi**. Romi code runs on a laptop in simulation mode and talks to the Romi at `10.0.0.2` over its Wi-Fi. Codespaces can build and run unit tests only.
- Romi onboard I/O (`OnBoardIO`): green, yellow, and red LEDs; buttons A, B, C. Yellow is output-only. Green and red share pins with buttons B and C and are configured as input or output. Two wheel encoders (1440 counts per revolution) and a gyro are on the control board.
- Romi external I/O: five EXT pins. The default mapping is EXT0 = DIO 8, EXT1 = Analog 0, EXT2 = Analog 1, EXT3 = PWM 2, EXT4 = PWM 3. The Romi Web UI reassigns them and shows the resulting WPILib channels. The Pololu arm kit (Capstone 2) needs three PWM channels and the 5 V power jumper.
- CI runs `./gradlew build` on every non-main push and on pull requests. Branch protection requires it.
- `./gradlew simulateJava` runs headless (no Sim GUI, no Romi websocket) when `CODESPACES=true` or `-Pheadless` is passed; see the simulation block in `build.gradle`. In headless mode the robot stays in Disabled mode and prints to the terminal.

## How `main` moves: the operating model

Students follow the same routine every session: refresh `main`, branch `yourname/session-N`, code, test, commit, push. Their branches are never merged. Instead, **`main` advances between sessions** so every student starts Session N from a robot that already has Sessions 2 through N-1 done the reference way.

| Before | `main` must contain | Mentor merges |
| ------ | ------------------- | ------------- |
| Session 1 and 2 | The scaffold | nothing (`main` is at `bootcamp-start`) |
| Session 3 | Session 2 done | `solution/session-2` |
| Session 4 | Sessions 2-3 done | `solution/session-3` |
| Session 5 | Sessions 2-4 done | `solution/session-4` |
| Capstone 1 | Sessions 2-5 done | `solution/session-5` |
| Capstone 2 | Capstone 1 done | `solution/capstone-1` |

- Merge with a pull request from the solution branch into `main` (CI must be green). Do it after the session ends and before the next one starts.
- **Never merge a `*-challenge` branch into `main`.** Challenge solutions stay unpublished so the challenge stays a challenge.
- The tag **`bootcamp-start`** marks the commit `main` starts from. Move it only when the scaffold itself changes (`git tag -f bootcamp-start <commit> && git push -f origin bootcamp-start`).
- **Resetting `main` for a new run** (the bootcamp runs more than once a season): temporarily allow force pushes in the branch protection rule, run `git push --force origin bootcamp-start:main`, then restore the rule. Old student branches can stay or be deleted; they do not affect `main`.
- Site pages that say "that is your Session 3 code" rely on this model. If `main` is not advanced, those steps fail. Keep the table above and the site in agreement.

## Branches

| Branch | Based on | Holds |
| ------ | -------- | ----- |
| `main` | `bootcamp-start`, advanced by merges | The scaffold: stubs with `TODO Session N` comments, tests marked `@Disabled("Session N: ...")` |
| `solution/session-2` | `bootcamp-start` | Logging and the heartbeat |
| `solution/session-3` | `solution/session-2` | `RslLogic.shouldBeOn`, `RslLogicTest` enabled, button A trigger |
| `solution/session-4` | `solution/session-3` | `DriveInput`, `ArcadeDriveCommand`, controller bindings |
| `solution/session-5` | `solution/session-4` | Gyro, odometry, `Field2d`, telemetry, encoder tests |
| `solution/capstone-1` | `solution/session-5` | `DriveDistance`, `TurnDegrees`, the auto chooser |
| `solution/capstone-2` | `solution/capstone-1` | The arm subsystem, commands, bindings, and an auto that uses it |
| `solution/session-N-challenge` | `solution/session-N` | That session's challenge reference solution (never merged) |
| `solution/capstone-1-challenge` | `solution/capstone-1` | Shape routines (never merged) |

- The exercise chain is linear. When an exercise changes, rebuild every branch after it: check out the changed branch, re-apply the later commits (`git rebase --onto`), force-push, and confirm CI is green on each.
- Site code blocks are copied from these branches, never typed fresh. After changing a solution branch, update the matching site page.
- Exercise tests ship on `main` marked `@Disabled("Session N: ...")`. Students delete that line as step one of the exercise. Never remove `@Disabled` on `main` before the corresponding solution is merged.

## Exercise and challenge design rules

- Every exercise is real robot code that can deploy to the Romi, even when the session only runs unit tests.
- Teach Java through robot behavior. Sessions 2-3 use the LEDs and buttons so nothing needs to move. Sessions 4-5 add the drivetrain, simulation, and the Romi. The capstones add autonomous routines and the arm.
- Keep code as simple as possible and only as sophisticated as necessary. Minimal error handling. Comments explain concepts, not obvious lines.
- Every exercise has a unit test the student can run with `./gradlew test` and see pass. Prefer testing plain logic in small classes (`RslLogic`, `DriveInput`, `MorseLogic`) over testing hardware wrappers.
- Each session ends with an optional **challenge** that reuses the session's skills on a problem the site does not walk through. The site gives the goal, a done-when list, pointers, and collapsible hints. Hints are reminders and links, never code. The reference solution lives on the `*-challenge` branch and uses only syntax taught by that session (or the capstone's reference page), so a student could have written it.
- `OnBoardIO` has no `close()`, and DIO channels cannot be allocated twice in one JVM, so never construct `SignalLight` or `RobotContainer` in a test. HAL tests (`HAL.initialize(500, 0)` in `@BeforeEach`, `close()` in `@AfterEach`) are fine for `RomiDrivetrain`, which implements `AutoCloseable`.
- Compare doubles with a tolerance: `assertEquals(expected, actual, DELTA)`. `-0.0` is not equal to `0.0` without one.
- Pick test inputs that are exact in binary (multiples of 0.25) when the code uses `%`.
- Constants go in `Constants.java`. Hardware access goes in subsystems. Behavior goes in commands.
- `DifferentialDrive` has motor safety on. Any command that drives must call `arcadeDrive` every loop or the motors stop with a watchdog message.

## Conventions

- Branches: `yourname/what-it-does`. Commit messages: one short present-tense line saying what the change does.
- Run `./gradlew build` before pushing. Keep it green.
- Never include AI model identifiers in commits, pull requests, or code.
