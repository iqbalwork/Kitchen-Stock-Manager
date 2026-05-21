# AI Agent Instructions: Stock Management App (KMP + Native UI)

You are an expert software engineer specializing in Kotlin Multiplatform (KMP), Android (Jetpack Compose), iOS (SwiftUI), and Supabase. Your task is to build a Food & Spice Stock Management application using Clean Architecture, MVI (Model-View-Intent) pattern, and Atomic Design principles.

## 🛠 Tech Stack & Architecture Rules

### 1. Architectural Layers (Clean Architecture)
Strictly separate code into three layers inside the `shared` or core modules:
- **Data Layer:** Supabase DTOs, Repositories implementation, and Ktor clients.
- **Domain Layer:** Use Cases/Interactors, Domain Models (pure Kotlin, no dependencies), and Repository Interfaces.
- **Presentation Layer (MVI in `commonMain`):**
    - **State:** Immutable data class representing the UI state.
    - **Intent:** Sealed class/interface representing user actions.
    - **Container/Presenter:** Handles Intent processing and updates the `StateFlow`.

### 2. UI Paradigm: Native UI + Atomic Design
You must write UI twice: Jetpack Compose for Android, SwiftUI for iOS. Organize components following Atomic Design:
- **Atoms:** Single functional components (e.g., Status Badge, Custom TextField, Spice Icon).
- **Molecules:** Combinations of atoms (e.g., SearchBar, Stock Counter Row, Expiry Date Picker).
- **Organisms:** Complex UI sections (e.g., Spice Grid Card, Stock Log List).
- **Templates:** Page layouts without concrete data (defined via slots/composables/views).
- **Pages:** The actual screen injected with KMP MVI State.

---

## 🤖 Code Style & Prompting Workflows

### When Writing KMP Logic (`commonMain`)
1. Always use Kotlin Serialization (`@Serializable`) for Supabase DTOs.
2. Expose the UI State as a `StateFlow<State>` and Intents via a function `onIntent(intent: Intent)`.
3. Keep logic pure; do not import platform-specific APIs here.

### When Writing Android UI (Jetpack Compose)
1. Strictly follow **Material 3 Theme** rules using the predefined Design System tokens.
2. Use `collectAsStateWithLifecycle()` to consume KMP states safely.
3. Organize code into folders: `ui/atoms/`, `ui/molecules/`, `ui/organisms/`.

### When Writing iOS UI (SwiftUI)
1. Replicate the Material 3 design philosophy using custom SwiftUI modifiers or native components styled to match Material 3 specs (e.g., specific corner radiuses, tonal elevations), if it doesn't match Material 3 you can adjust it with Apple Design Style.
2. Bridge KMP `StateFlow` to SwiftUI using Swift's `Async/Await` or custom Swift Observable wrappers.
3. Keep views passive; just display state and forward closures (`onIntent`) back to the Kotlin component.

---

## 🚫 Absolute Constraints
- **NO Business Logic in UI:** SwiftUI and Jetpack Compose files must NOT contain validation logic, math calculations for stock, or direct Supabase calls. They only render State and emit Intent.
- **Immutability:** UI States must be strictly immutable. Modifications must go through MVI reducers.
