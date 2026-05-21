package com.iqbalfauzi.kitchenstockmanager.domain

class MockPantryRepository {
    fun getDashboardItems(): List<PantryItem> {
        return listOf(
            PantryItem(
                id = "1",
                name = "Olive Oil",
                quantity = "15% left",
                category = CategoryType.OTHER,
                status = StockStatus.LowStock(15),
                progress = 0.15f
            ),
            PantryItem(
                id = "2",
                name = "Organic Eggs",
                quantity = "2 remaining",
                category = CategoryType.DAIRY,
                status = StockStatus.LowStock(20),
                progress = 0.2f
            ),
            PantryItem(
                id = "3",
                name = "Whole Wheat Flour",
                quantity = "Low",
                category = CategoryType.GRAINS,
                status = StockStatus.LowStock(30),
                progress = 0.3f
            )
        )
    }

    fun getInventoryItems(): List<PantryItem> {
        return listOf(
            PantryItem(
                id = "4",
                name = "Hass Avocados",
                quantity = "4 units",
                category = CategoryType.PRODUCE,
                status = StockStatus.Fresh
            ),
            PantryItem(
                id = "5",
                name = "Organic Whole Milk",
                quantity = "200 ml",
                category = CategoryType.DAIRY,
                status = StockStatus.ExpiringSoon
            ),
            PantryItem(
                id = "6",
                name = "Chicken Breast",
                quantity = "500 g",
                category = CategoryType.MEAT,
                status = StockStatus.UseToday
            ),
            PantryItem(
                id = "7",
                name = "Fusilli Pasta",
                quantity = "1.2 kg",
                category = CategoryType.PANTRY,
                status = StockStatus.Fresh
            )
        )
    }

    fun getSuggestedShoppingItems(): List<PantryItem> {
        return listOf(
            PantryItem(
                id = "8",
                name = "Organic Whole Milk",
                quantity = "Need: 1 Gallon",
                category = CategoryType.DAIRY,
                status = StockStatus.Expired, // "Out"
                price = "~$4.99"
            ),
            PantryItem(
                id = "9",
                name = "Free-Range Eggs",
                quantity = "Need: 1 Dozen",
                category = CategoryType.DAIRY,
                status = StockStatus.LowStock(20),
                price = "~$6.50"
            )
        )
    }

    fun getCustomShoppingItems(): List<PantryItem> {
        return listOf(
            PantryItem(
                id = "10",
                name = "Sourdough Bread",
                quantity = "Bakery section",
                category = CategoryType.OTHER,
                status = StockStatus.Fresh
            ),
            PantryItem(
                id = "11",
                name = "Fresh Basil",
                quantity = "Produce - 1 bunch",
                category = CategoryType.PRODUCE,
                status = StockStatus.Fresh
            )
        )
    }
}
