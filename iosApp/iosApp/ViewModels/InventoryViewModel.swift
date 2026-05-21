import Foundation
import SharedLogic
import Combine

@MainActor
class InventoryViewModel: ObservableObject {
    private let repository = MockPantryRepository()
    
    @Published var items: [PantryItem] = []
    @Published var selectedFilter: String = "All Items"
    
    let filters = ["All Items", "Produce", "Dairy", "Meat", "Pantry"]
    
    init() {
        loadItems()
    }
    
    func loadItems() {
        self.items = repository.getInventoryItems()
    }
}
