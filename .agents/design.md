# Design System Specification: Food & Spice Stock Management

This document defines the Design Tokens, UI Patterns, and Atomic Design structure based on Material 3 guidelines, adjusted for a unified experience across Android (Jetpack Compose) and iOS (SwiftUI).

## 🎨 Design Tokens (Material 3 Based)

### 1. Color Palette (Theme: Culinary/Fresh Kitchen)
A fresh, organic palette representing fresh ingredients, spices, and clean storage.

| Token Name | Light Value | Dark Value | Description |
| :--- | :--- | :--- | :--- |
| `primary` (Herb Green) | `#2E7D32` | `#81C784` | Main buttons, active states, branding |
| `secondary` (Spice Ochre) | `#E65100` | `#FFB74D` | Warning, low stock indicators, highlights |
| `surface` | `#FFFFFF` | `#121212` | Backgrounds of cards and containers |
| `background` | `#F5F5F5` | `#1A1A1A` | Main application background |
| `error` | `#C62828` | `#E57373` | Expired stock, critical error actions |

### 2. Typography
- **Display / Headers:** Sans-Serif Bold (Android: Roboto/Inter, iOS: SF Pro Display Bold).
- **Body / Labels:** Sans-Serif Regular/Medium for crisp, legible stock counts and expiration metrics.

### 3. Shape & Elevation (Material 3 Expressive)
- **Atoms (Buttons/Chips):** Rounded corners with `8dp` (Medium) or `16dp` (Large) radius.
- **Cards (Organisms):** `12dp` corner radius with subtle tonal elevation (no heavy drop shadows, use surface tint color shifting).

---

## 🧩 Atomic Design Structure

### ⚛️ Atoms (Unbxd Functional Components)
- `StockBadge`: Small indicator badge showing "Aman" (Green), "Menipis" (Orange), "Habis" (Red).
- `QuantityField`: A clean text input that accepts numbers only.
- `AppButton`: Container button following Material 3 Filled/Tonal/Outlined styles.

### 🧪 Molecules (Composite Components)
- `StockCounter`: A combined component containing a minus button, current number label, and plus button.
- `SearchBar`: Text input with a trailing clear icon and leading search icon for filtering food/spice.
- `ExpiryLabel`: Combined calendar icon and date text that turns red if the item is close to or past expiry.

### 🧬 Organisms (Complex Layout Blocks)
- `ItemStockCard`: A Grid/List card item containing the product image placeholder, `StockBadge`, title, `StockCounter`, and `ExpiryLabel`.
- `StockHistoryList`: A chronological list showing logs of stock entry and exit (e.g., "+500g Gula", "-200g Garam").

### 🖼 Templates & Pages (Screen Layouts)
- **Dashboard / Home Screen:**
    - *Template:* Search bar slot at top, followed by horizontally scrollable categories, and a staggered grid slot for stock items.
    - *MVI State Mapping:* Renders `HomeState.ListBumbu`. Emits `HomeIntent.SearchQueryChanged` and `HomeIntent.UpdateStock`.
- **Add / Edit Item Screen:**
    - *Template:* Form scroll view with distinct sections for Name, Category, Quantity, Unit, and Expiration Date, with a sticky action button at the bottom.
