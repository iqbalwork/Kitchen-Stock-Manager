import Foundation
import SharedLogic
import Combine

@MainActor
class ShoppingViewModel: ObservableObject {
    private let repository = MockPantryRepository()
    
    @Published var suggestedItems: [PantryItem] = []
    @Published var customItems: [PantryItem] = []
    
    var remainingItemsCount: Int {
        suggestedItems.count + customItems.count
    }
    
    init() {
        loadItems()
    }
    
    func loadItems() {
        self.suggestedItems = repository.getSuggestedShoppingItems()
        self.customItems = repository.getCustomShoppingItems()
    }
}
